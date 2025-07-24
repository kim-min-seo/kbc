package com.minse0.kbc.comment.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.minse0.kbc.comment.domain.Comment;
import com.minse0.kbc.comment.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> getComments(Long postId) {
        return commentRepository.findByPostIdOrderByCreatedAtAsc(postId);
    }
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public boolean addComment(Long postId, Long userId, String content) {
        Comment c = Comment.builder()
                .postId(postId)
                .userId(userId)
                .content(content)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        try {
            commentRepository.save(c);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean updateComment(Long commentId, Long userId, String content) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null || !comment.getUserId().equals(userId)) {
            return false;
        }
        comment.setContent(content);
        comment.setUpdatedAt(LocalDateTime.now());
        commentRepository.save(comment);
        return true;
    }

    public boolean deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null || !comment.getUserId().equals(userId)) {
            return false;
        }
        commentRepository.delete(comment);
        return true;
    }

    public void deleteCommentsByPost(Long postId) {
        commentRepository.deleteByPostId(postId);
    }
}
