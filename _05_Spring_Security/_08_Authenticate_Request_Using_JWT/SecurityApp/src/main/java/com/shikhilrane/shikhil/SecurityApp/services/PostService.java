package com.shikhilrane.shikhil.SecurityApp.services;

import com.shikhilrane.shikhil.SecurityApp.dto.PostDto;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<PostDto> getAllPosts();
    PostDto createNewPost(PostDto inputPost);
    Optional<PostDto> getPostById(Long id);
}
