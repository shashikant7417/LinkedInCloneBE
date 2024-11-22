package com.shashikant.linkedin.notification_service.comsumer;

import com.shashikant.linkedin.notification_service.clients.ConnectionsClient;
import com.shashikant.linkedin.notification_service.dto.PersonDto;
import com.shashikant.linkedin.notification_service.entity.Notification;
import com.shashikant.linkedin.notification_service.repository.NotificationRepository;
import com.shashikant.linkedin.notification_service.service.SendNotification;
import com.shashikant.linkedin.post_service.event.PostCreatedEvent;
import com.shashikant.linkedin.post_service.event.PostLikedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceConsumer {

    private final ConnectionsClient connectionsClient;
    private final SendNotification sendNotification;

    @KafkaListener(topics = "post-created-topic")
    public void handelPostCreated(PostCreatedEvent postCreatedEvent){
        log.info("Sending Notification : handelPostCreated", postCreatedEvent);
         List<PersonDto> connections = connectionsClient.getFirstConnections(postCreatedEvent.getCreatorId());

         for(PersonDto connection: connections){

             sendNotification.send(connection.getUserId(),"Your connection" +postCreatedEvent.getCreatorId()+
                     "has created a post, Check it out");
         }

    }

    @KafkaListener(topics = "post-liked-topic")
    public void handelPostLiked(PostLikedEvent postLikedEvent){
        log.info("Sending Notification : handelPostLiked", postLikedEvent);
        String message = String.format("Your post, %d has been liked by %d", postLikedEvent.getPostId(),postLikedEvent.getLikedByUserId());

        sendNotification.send(postLikedEvent.getCreatorId(), message);


    }
}
