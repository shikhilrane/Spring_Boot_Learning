package com.shikhilrane.shikhil.SecurityApp.utils;

import com.shikhilrane.shikhil.SecurityApp.dto.PostDto;
import com.shikhilrane.shikhil.SecurityApp.entities.PostEntity;
import com.shikhilrane.shikhil.SecurityApp.entities.User;
import com.shikhilrane.shikhil.SecurityApp.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostSecurity {

    private final PostService postService;

    public boolean isOwnerOfPost(Long postId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();  // Get the current logged-in user from SpringSecurityContext
        Optional<PostDto> post = postService.getPostById(postId);                   // Get the post by its id from the service
        return post.get().getAuthor().getId().equals(user.getId());                // Check if the id of the user who created the post is same as the logged-in user's id
    }
}
