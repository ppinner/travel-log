package com.travellog.post;

import com.travellog.common.exception.ForbiddenException;
import com.travellog.common.exception.ResourceNotFoundException;
import com.travellog.place.PlaceService;
import com.travellog.place.RatingSummary;
import com.travellog.post.dto.CreatePostRequest;
import com.travellog.post.dto.TourGuideDto;
import java.time.Instant;
import java.util.List;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PlaceService placeService;
    private final MongoTemplate mongoTemplate;

    public PostService(PostRepository postRepository, PlaceService placeService, MongoTemplate mongoTemplate) {
        this.postRepository = postRepository;
        this.placeService = placeService;
        this.mongoTemplate = mongoTemplate;
    }

    public List<Post> findByPlaceId(String placeId) {
        return postRepository.findByPlaceIdAndActiveTrue(placeId);
    }

    public Post findById(String id) {
        return postRepository.findById(id)
                .filter(Post::isActive)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
    }

    public Post create(CreatePostRequest request, String userId) {
        placeService.findById(request.placeId());

        Instant now = Instant.now();
        Post post = Post.builder()
                .placeId(request.placeId())
                .authorId(userId)
                .title(request.title())
                .body(request.body())
                .ratings(toRatings(request))
                .tourGuide(toTourGuide(request.tourGuide()))
                .visitDate(request.visitDate())
                .active(true)
                .createdAt(now)
                .updatedAt(now)
                .build();
        post = postRepository.save(post);
        recomputeRatingSummary(post.getPlaceId());
        return post;
    }

    public Post update(String id, CreatePostRequest request, String userId) {
        Post post = findById(id);
        assertAuthor(post, userId);

        post.setTitle(request.title());
        post.setBody(request.body());
        post.setRatings(toRatings(request));
        post.setTourGuide(toTourGuide(request.tourGuide()));
        post.setVisitDate(request.visitDate());
        post.setUpdatedAt(Instant.now());
        post = postRepository.save(post);
        recomputeRatingSummary(post.getPlaceId());
        return post;
    }

    public void delete(String id, String userId) {
        Post post = findById(id);
        assertAuthor(post, userId);
        post.setActive(false);
        post.setUpdatedAt(Instant.now());
        postRepository.save(post);
        recomputeRatingSummary(post.getPlaceId());
    }

    private void recomputeRatingSummary(String placeId) {
        Aggregation aggregation = Aggregation.newAggregation(
                match(where("placeId").is(placeId).and("active").is(true)),
                group()
                        .count().as("count")
                        .avg("ratings.cost").as("avgCost")
                        .avg("ratings.authenticity").as("avgAuthenticity")
                        .avg("ratings.enjoyment").as("avgEnjoyment")
                        .avg("ratings.fitnessRequired").as("avgFitnessRequired"));

        AggregationResults<AggregatedRatings> results =
                mongoTemplate.aggregate(aggregation, "posts", AggregatedRatings.class);
        AggregatedRatings aggregated = results.getUniqueMappedResult();

        RatingSummary summary = aggregated == null
                ? RatingSummary.empty()
                : RatingSummary.builder()
                        .count(aggregated.getCount())
                        .avgCost(aggregated.getAvgCost())
                        .avgAuthenticity(aggregated.getAvgAuthenticity())
                        .avgEnjoyment(aggregated.getAvgEnjoyment())
                        .avgFitnessRequired(aggregated.getAvgFitnessRequired())
                        .build();

        placeService.updateRatingSummary(placeId, summary);
    }

    private Ratings toRatings(CreatePostRequest request) {
        return Ratings.builder()
                .cost(request.ratings().cost())
                .authenticity(request.ratings().authenticity())
                .enjoyment(request.ratings().enjoyment())
                .fitnessRequired(request.ratings().fitnessRequired())
                .build();
    }

    private TourGuide toTourGuide(TourGuideDto dto) {
        if (dto == null) {
            return null;
        }
        return TourGuide.builder()
                .name(dto.name())
                .contactMethod(dto.contactMethod())
                .contactValue(dto.contactValue())
                .note(dto.note())
                .build();
    }

    private void assertAuthor(Post post, String userId) {
        if (!post.getAuthorId().equals(userId)) {
            throw new ForbiddenException("You do not own this post");
        }
    }
}
