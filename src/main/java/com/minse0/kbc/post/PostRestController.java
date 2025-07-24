package com.minse0.kbc.post;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import com.minse0.kbc.post.service.PostService;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;

   
    @PostMapping("/create")
    public Map<String, String> createPost(
            @RequestParam String team,
            @RequestParam String contents,
            @RequestParam(required = false) MultipartFile imageFile,
            HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        boolean success = postService.addPost(userId, team, contents, imageFile);

        Map<String, String> result = new HashMap<>();
        result.put("result", success ? "success" : "fail");
        return result;
    }
    @PutMapping("//update")
    public Map<String, String> updatePost(
            @RequestParam Long postId,
            @RequestParam String team,
            @RequestParam String contents,
            @RequestParam(required = false) MultipartFile imageFile,
            HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        boolean success = postService.updatePost(postId, userId, team, contents, imageFile);

        Map<String, String> result = new HashMap<>();
        result.put("result", success ? "success" : "fail");
        return result;
    }


    @DeleteMapping("/delete")
    public Map<String, String> deletePost(
            @RequestParam Long postId,
            HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        boolean success = postService.deletePost(postId, userId);

        Map<String, String> result = new HashMap<>();
        result.put("result", success ? "success" : "fail");
        return result;
    }
}
