package com.shashikant.linkedin.user_service.services;

import com.shashikant.linkedin.user_service.dtos.LoginRequestDto;
import com.shashikant.linkedin.user_service.dtos.SignupRequestDto;
import com.shashikant.linkedin.user_service.dtos.UserDto;
import com.shashikant.linkedin.user_service.entities.User;
import com.shashikant.linkedin.user_service.exceptions.BadCredentialException;
import com.shashikant.linkedin.user_service.exceptions.ResourceNotFoundException;
import com.shashikant.linkedin.user_service.repositories.UserRepository;
import com.shashikant.linkedin.user_service.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JWTService jwtService;

    public UserDto signUp(SignupRequestDto signupRequestDto) {

        boolean exists = userRepository.existsByEmail(signupRequestDto.getEmail());
        if(exists){
            throw new BadCredentialException("User Already Exists, Cannot signup again");
        }

        User user = modelMapper.map(signupRequestDto,User.class);
        user.setPassword(PasswordUtil.hashPassword(signupRequestDto.getPassword()));

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser,UserDto.class);

    }

    public String login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: "+loginRequestDto.getEmail()));

        boolean isPasswordMatch = PasswordUtil.checkPassword(loginRequestDto.getPassword(),user.getPassword());

        if(!isPasswordMatch){
            throw new BadCredentialException("Incorrect Password");
        }

        return  jwtService.generateAccessToken(user);

    }
}
