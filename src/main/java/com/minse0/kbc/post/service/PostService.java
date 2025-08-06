package com.minse0.kbc.post.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.minse0.kbc.common.Filemanager;
import com.minse0.kbc.post.domain.Post;
import com.minse0.kbc.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Page<Post> getPostPage(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public boolean addPost(Long userId, String team, String contents,
                           MultipartFile imageFile) {
        if (contents == null || contents.isEmpty()) {
            return false;
        }
        String imagePath = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            imagePath = Filemanager.saveFile(userId, imageFile);
        }
        LocalDateTime now = LocalDateTime.now();
        Post post = Post.builder()
                .userId(userId)
                .team(team)
                .contents(contents)
                .imagePath(imagePath)
                .createdAt(now)
                .updatedAt(now)
                .build();
        postRepository.save(post);
        return true;
    }

    public Post getPost(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public boolean updatePost(Long postId, Long userId,
                              String team, String contents,
                              MultipartFile imageFile) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null || !post.getUserId().equals(userId)) {
            return false;
        }
        post.setTeam(team);
        post.setContents(contents);
        if (imageFile != null && !imageFile.isEmpty()) {
            String path = Filemanager.saveFile(userId, imageFile);
            post.setImagePath(path);
        }
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.save(post);
        return true;
    }

    public boolean deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null || !post.getUserId().equals(userId)) {
            return false;
        }
        String path = post.getImagePath();
        if (path != null && !path.isEmpty()) {
            Filemanager.removeFile(path);
        }
        postRepository.delete(post);
        return true;
    }
    public Post save(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

}