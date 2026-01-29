package com.shikhilrane.shikhil.prod_ready_features.controllers;

import com.shikhilrane.shikhil.prod_ready_features.dto.PostDto;
import com.shikhilrane.shikhil.prod_ready_features.exceptions.ResourceNotFoundException;
import com.shikhilrane.shikhil.prod_ready_features.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/{getPostById}")
    public ResponseEntity<PostDto> getbyId(@PathVariable(name = "getPostById") Long id){
        Optional<PostDto> postById = postService.getPostById(id);
        return postById
                .map(postById1 -> ResponseEntity.ok(postById1))
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id : " + id));
    }

    @GetMapping("/getAllPosts")
    public ResponseEntity<List<PostDto>> getAll(){
        List<PostDto> allPosts = postService.getAllPosts();
        return ResponseEntity.ok(allPosts);
    }

    @PostMapping("/createPost")
    public ResponseEntity<PostDto> createNewPost(@RequestBody PostDto inputPost){
        PostDto newPost = postService.createNewPost(inputPost);
        return ResponseEntity.ok(newPost);
    }
}
