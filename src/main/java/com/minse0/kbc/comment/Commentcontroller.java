package com.minse0.kbc.comment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.minse0.kbc.comment.service.CommentService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class Commentcontroller {

    private final CommentService commentService;

    @PostMapping("/add")
    public String add(@RequestParam("postId") Long postId,
                      @RequestParam("content") String content,
                      HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        commentService.addComment(postId, userId, content);
        return "redirect:/community";
    }

    @PostMapping("/edit")
    public String edit(@RequestParam("commentId") Long commentId,
                       @RequestParam("postId")    Long postId,
                       @RequestParam("content")   String content,
                       HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        commentService.updateComment(commentId, userId, content);
        return "redirect:/community";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("commentId") Long commentId,
                         @RequestParam("postId")    Long postId,
                         HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        commentService.deleteComment(commentId, userId);
        return "redirect:/community";
    }
}
