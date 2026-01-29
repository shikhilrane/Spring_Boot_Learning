package com.shikhilrane.shikhil.prod_ready_features.services;

import com.shikhilrane.shikhil.prod_ready_features.dto.PostDto;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<PostDto> getAllPosts();
    PostDto createNewPost(PostDto inputPost);
    Optional<PostDto> getPostById(Long id);
}
