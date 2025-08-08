package com.minse0.kbc.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.minse0.kbc.notification.domain.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByReceiverIdOrderByCreatedAtDesc(Long receiverId);
    long countByReceiverIdAndReadFalse(Long receiverId);
    
    @Modifying
    @Query("update Notification n set n.read = true where n.receiverId = :userId and n.read = false")
    int markAllRead(Long userId);
}
