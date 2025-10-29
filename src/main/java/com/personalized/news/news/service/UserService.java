package com.personalized.news.news.service;

import com.personalized.news.news.dto.userDto.UserRequest;
import com.personalized.news.news.dto.userDto.UserResponse;
import com.personalized.news.news.entity.User;
import com.personalized.news.news.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public UserResponse createUser(UserRequest userRequest){
        userRequest.setPassword(encoder.encode(userRequest.getPassword()));
        User user = User.from(userRequest);
        User userSaved = userRepository.save(user);

        return UserResponse.from(userSaved);
    }
}
