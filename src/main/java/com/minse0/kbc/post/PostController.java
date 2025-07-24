// src/main/java/com/minse0/kbc/post/PostController.java
package com.minse0.kbc.post;

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

import com.minse0.kbc.post.domain.Post;
import com.minse0.kbc.post.service.PostService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("")
    public String community(Model model,
                            HttpSession session,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size) {
    	
    	  
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        
        Page<Post> postPage = postService.getPostPage(pageable);

      
        model.addAttribute("posts",       postPage.getContent());  
        model.addAttribute("page",        postPage.getNumber());    
        model.addAttribute("size",        postPage.getSize());      
        model.addAttribute("totalPages",  postPage.getTotalPages());

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

    
}
