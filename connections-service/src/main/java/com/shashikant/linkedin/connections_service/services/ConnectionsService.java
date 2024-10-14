package com.shashikant.linkedin.connections_service.services;

import com.shashikant.linkedin.connections_service.auth.UserContextHolder;
import com.shashikant.linkedin.connections_service.entity.Person;
import com.shashikant.linkedin.connections_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionsService {

    private final PersonRepository personRepository;


    public List<Person> getFirstDegreeConnection(){

        Long userId = UserContextHolder.getCurrentUserId();

        log.info(" Getting first degree connections for user with id: {}" , userId);

        return personRepository.getFirstDegreeConnection(userId);
    }
}
