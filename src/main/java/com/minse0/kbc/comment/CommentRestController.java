package com.minse0.kbc.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.minse0.kbc.comment.domain.Comment;
import com.minse0.kbc.comment.service.CommentService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentRestController {
    private final CommentService commentService;

    @GetMapping("/post")
    public Map<String, Object> listByPost(@RequestParam Long postId, HttpSession session) {
        Long currentUserId = (Long) session.getAttribute("userId");
        List<Comment> comments = commentService.getComments(postId);
        return Map.of(
            "postId", postId,
            "currentUserId", currentUserId,
            "comments", comments
        );
    }

    @PostMapping("/create")
    public Map<String, String> createComment(@RequestParam Long postId,
                                             @RequestParam String content,
                                             HttpSession session) {
        Map<String, String> result = new HashMap<>();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            result.put("result", "fail");
            result.put("message", "로그인 후 사용 가능합니다.");
            return result;
        }
        boolean success = commentService.addComment(postId, userId, content);
        result.put("result", success ? "success" : "fail");
        return result;
    }

    @PutMapping("/edit")
    public Map<String, String> editComment(@RequestParam Long commentId,
                                           @RequestParam Long postId,
                                           @RequestParam String content,
                                           HttpSession session) {
        Map<String, String> result = new HashMap<>();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            result.put("result", "fail");
            result.put("message", "로그인 후 사용 가능합니다.");
            return result;
        }
        Comment comment = commentService.getComment(commentId);
        if (comment == null) {
            result.put("result", "fail");
            result.put("message", "댓글을 찾을 수 없습니다.");
            return result;
        }
        if (!comment.getUserId().equals(userId)) {
            result.put("result", "fail");
            result.put("message", "수정 권한이 없습니다.");
            return result;
        }
        boolean success = commentService.updateComment(commentId, userId, content);
        result.put("result", success ? "success" : "fail");
        return result;
    }

    @DeleteMapping("/delete")
    public Map<String, String> deleteComment(@RequestParam Long commentId,
                                             @RequestParam Long postId,
                                             HttpSession session) {
        Map<String, String> result = new HashMap<>();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            result.put("result", "fail");
            result.put("message", "로그인 후 사용 가능합니다.");
            return result;
        }
        Comment comment = commentService.getComment(commentId);
        if (comment == null) {
            result.put("result", "fail");
            result.put("message", "댓글을 찾을 수 없습니다.");
            return result;
        }
        if (!comment.getUserId().equals(userId)) {
            result.put("result", "fail");
            result.put("message", "삭제 권한이 없습니다.");
            return result;
        }
        boolean success = commentService.deleteComment(commentId, userId);
        result.put("result", success ? "success" : "fail");
        return result;
    }
}
