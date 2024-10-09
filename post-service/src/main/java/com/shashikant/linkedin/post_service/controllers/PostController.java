package com.shashikant.linkedin.post_service.controllers;

import com.shashikant.linkedin.post_service.dtos.PostCreateRequestDto;
import com.shashikant.linkedin.post_service.dtos.PostDto;
import com.shashikant.linkedin.post_service.entities.Post;
import com.shashikant.linkedin.post_service.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path ="/core")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateRequestDto postCreateRequestDto, HttpServletRequest httpServletRequest){

        PostDto createdPost = postService.createPost(postCreateRequestDto, 1L);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);

    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId){
        PostDto postdto = postService.getPostById(postId);

        return ResponseEntity.ok(postdto);

    }

    @GetMapping("/user/{userId}/allPosts")
    public ResponseEntity<List<PostDto>> getAllPostOfUser(@PathVariable Long userId){
       List<PostDto> posts = postService.getAllPostsOfUser(userId);

       return ResponseEntity.ok(posts);

    }
}
