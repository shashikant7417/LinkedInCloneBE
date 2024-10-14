package com.shashikant.linkedin.post_service.services;

import com.shashikant.linkedin.post_service.auth.UserContextHolder;
import com.shashikant.linkedin.post_service.clients.ConnectionsClient;
import com.shashikant.linkedin.post_service.dtos.PersonDto;
import com.shashikant.linkedin.post_service.dtos.PostCreateRequestDto;
import com.shashikant.linkedin.post_service.dtos.PostDto;
import com.shashikant.linkedin.post_service.entities.Post;
import com.shashikant.linkedin.post_service.exceptions.ResourceNotFoundException;
import com.shashikant.linkedin.post_service.repositories.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final ConnectionsClient connectionsClient;

    public PostDto createPost(PostCreateRequestDto postCreateRequestDto , Long userId) {
        Post post = modelMapper.map(postCreateRequestDto, Post.class);
        post.setUserId(userId);

        Post savedPost = postRepository.save(post);

        return modelMapper.map(savedPost,PostDto.class);


    }

    public PostDto getPostById(Long postId) {
        log.debug("Retrieving post with ID : {}" , postId);

        Long userId = UserContextHolder.getCurrentUserId();
         List<PersonDto> firstConnections = connectionsClient.getFirstConnections(userId);

         // Send Notification to all connections

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with id :" + postId));
        return modelMapper.map(post,PostDto.class);

    }


    public List<PostDto> getAllPostsOfUser(Long userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        return posts
                .stream().map((element) -> modelMapper.map(element, PostDto.class))
                .collect(Collectors.toList());

    }
}
