package com.shashikant.linkedin.connections_service.controllers;

import com.shashikant.linkedin.connections_service.entity.Person;
import com.shashikant.linkedin.connections_service.services.ConnectionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class ConnectionsController {

    private  final ConnectionsService connectionsService;

    @GetMapping("/first-degree")
    public ResponseEntity<List<Person>> getFirstConnections(){
         List<Person> firstDegreeConnection = connectionsService.getFirstDegreeConnection();
         return ResponseEntity.ok(firstDegreeConnection);
    }

}
