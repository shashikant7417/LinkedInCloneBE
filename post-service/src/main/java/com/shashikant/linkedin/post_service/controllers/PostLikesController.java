package com.shashikant.linkedin.post_service.controllers;

import com.shashikant.linkedin.post_service.services.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class PostLikesController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}")
    public ResponseEntity<Void> postLike(@PathVariable Long postId){
        postLikeService.likePost(postId);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> postUnLike(@PathVariable Long postId){
        postLikeService.unLikePost(postId);
        return ResponseEntity.noContent().build();

    }

}
