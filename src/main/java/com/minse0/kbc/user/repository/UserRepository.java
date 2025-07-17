package com.minse0.kbc.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minse0.kbc.user.domain.User;

public interface UserRepository extends JpaRepository <User, Long> {
	
	boolean existsByLoginId(String loginId);
    Optional<User> findByLoginIdAndPassword(String loginId, String password);
}
