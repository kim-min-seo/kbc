package com.minse0.kbc.user.service;

import org.springframework.stereotype.Service;

import com.minse0.kbc.common.SHA256HashingEncoder;
import com.minse0.kbc.user.domain.User;
import com.minse0.kbc.user.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

   
    public User addUser(String loginId, String password, String name, String nickname, String preferteam) {
        String hashedPassword = SHA256HashingEncoder.encode(password);

        User user = User.builder()
                .loginId(loginId)
                .password(hashedPassword)
                .name(name)
                .nickname(nickname)
                .preferTeam(preferteam) 
                .build();

        return userRepository.save(user);  
    }

   
    public boolean isDuplicatedId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    
    public User getUser(String loginId, String password) {
        String hashedPassword = SHA256HashingEncoder.encode(password);
        return userRepository.findByLoginIdAndPassword(loginId, hashedPassword).orElse(null);
    }
}
