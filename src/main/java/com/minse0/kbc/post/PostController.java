package com.minse0.kbc.post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.minse0.kbc.comment.domain.Comment;
import com.minse0.kbc.comment.service.CommentService;
import com.minse0.kbc.post.domain.Post;
import com.minse0.kbc.post.service.PostService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("")
    public String community(Model model,
                            HttpSession session,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(required = false) Long editId) {

        Long currentUserId = (Long) session.getAttribute("userId");
        model.addAttribute("currentUserId", currentUserId);

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Post> postPage = postService.getPostPage(pageable);
        List<Post> posts = postPage.getContent();

        model.addAttribute("posts",      posts);
        model.addAttribute("page",       postPage.getNumber());
        model.addAttribute("size",       postPage.getSize());
        model.addAttribute("totalPages", postPage.getTotalPages());

      
        Map<Long, List<Comment>> commentMap = new HashMap<>();
        for (Post p : posts) {
        	 List<Comment> list = commentService.getComments(p.getId());
           
        }
        model.addAttribute("commentMap", commentMap);

        if (editId != null) {
            Post toEdit = postService.getPost(editId);
            if (toEdit != null && toEdit.getUserId().equals(currentUserId)) {
                model.addAttribute("editPost", toEdit);
            }
        }

        return "post/community";
    }

    @PostMapping("")
    public String submitCommunity(@RequestParam String team,
                                  @RequestParam String contents,
                                  @RequestParam(required = false) MultipartFile imageFile,
                                  HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        postService.addPost(userId, team, contents, imageFile);
        return "redirect:/community";
    }

    @PostMapping("/edit")
    public String editSubmit(@RequestParam Long id,
                             @RequestParam String team,
                             @RequestParam String contents,
                             @RequestParam(required = false) MultipartFile imageFile,
                             HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        postService.updatePost(id, userId, team, contents, imageFile);
        return "redirect:/community";
    }

    @PostMapping("/delete")
    public String deletePost(@RequestParam Long id,
                             HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        postService.deletePost(id, userId);
        return "redirect:/community";
    }
}
