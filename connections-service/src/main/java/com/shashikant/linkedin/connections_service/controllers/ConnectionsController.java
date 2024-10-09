package com.shashikant.linkedin.connections_service.controllers;

import com.shashikant.linkedin.connections_service.entity.Person;
import com.shashikant.linkedin.connections_service.services.ConnectionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class ConnectionsController {

    private  final ConnectionsService connectionsService;

    @GetMapping("/{userId}/first-degree")
    public ResponseEntity<List<Person>> getFirstConnections(@PathVariable Long userId){
         List<Person> firstDegreeConnection = connectionsService.getFirstDegreeConnection(userId);
         return ResponseEntity.ok(firstDegreeConnection);
    }

}
