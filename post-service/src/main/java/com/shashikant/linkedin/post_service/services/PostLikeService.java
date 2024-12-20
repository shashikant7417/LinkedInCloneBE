package com.shashikant.linkedin.post_service.services;

import com.shashikant.linkedin.post_service.auth.UserContextHolder;
import com.shashikant.linkedin.post_service.entities.Post;
import com.shashikant.linkedin.post_service.entities.PostLike;
import com.shashikant.linkedin.post_service.event.PostLikedEvent;
import com.shashikant.linkedin.post_service.exceptions.BadRequestException;
import com.shashikant.linkedin.post_service.exceptions.ResourceNotFoundException;
import com.shashikant.linkedin.post_service.repositories.PostLikeRepository;
import com.shashikant.linkedin.post_service.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final KafkaTemplate<Long, PostLikedEvent> kafkaTemplate;


    public void likePost(Long postId){
        Long userId = UserContextHolder.getCurrentUserId();

        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post not found with id : "+postId));

        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if(alreadyLiked) throw new BadRequestException("Post already liked cannot like again");

        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikeRepository.save(postLike);
        log.info("Post with id: {} liked Successfully", postId);

        PostLikedEvent postLikedEvent = PostLikedEvent.builder()
                .postId(postId)
                .likedByUserId(userId)
                .creatorId(post.getUserId())
                .build();
        kafkaTemplate.send("post-liked-topic",postId, postLikedEvent);



    }


    public void unLikePost(Long postId) {
        Long userId = UserContextHolder.getCurrentUserId();
        boolean exists = postRepository.existsById(postId);
        if(!exists) throw new ResourceNotFoundException("Post not found with id : "+postId);

        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if(!alreadyLiked) throw new BadRequestException("Cannot unlike the post which is not liked");

        postLikeRepository.deleteByUserIdAndPostId(userId, postId);

        log.info("Post unliked Successfully with id : " +postId);


    }
}
