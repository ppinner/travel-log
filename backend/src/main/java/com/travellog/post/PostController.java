package com.travellog.post;

import com.travellog.post.dto.CreatePostRequest;
import com.travellog.post.dto.PostResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/api/places/{placeId}/posts")
    public List<PostResponse> listByPlace(@PathVariable String placeId) {
        return postService.findByPlaceId(placeId).stream().map(PostResponse::from).toList();
    }

    @GetMapping("/api/posts/{id}")
    public PostResponse get(@PathVariable String id) {
        return PostResponse.from(postService.findById(id));
    }

    @PostMapping("/api/posts")
    public ResponseEntity<PostResponse> create(
            @Valid @RequestBody CreatePostRequest request, Authentication authentication) {
        Post post = postService.create(request, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(PostResponse.from(post));
    }

    @PutMapping("/api/posts/{id}")
    public PostResponse update(
            @PathVariable String id, @Valid @RequestBody CreatePostRequest request, Authentication authentication) {
        return PostResponse.from(postService.update(id, request, authentication.getName()));
    }

    @DeleteMapping("/api/posts/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id, Authentication authentication) {
        postService.delete(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
