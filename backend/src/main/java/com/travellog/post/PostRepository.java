package com.travellog.post;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {

    List<Post> findByPlaceIdAndActiveTrue(String placeId);

    List<Post> findByAuthorIdAndActiveTrue(String authorId);
}
