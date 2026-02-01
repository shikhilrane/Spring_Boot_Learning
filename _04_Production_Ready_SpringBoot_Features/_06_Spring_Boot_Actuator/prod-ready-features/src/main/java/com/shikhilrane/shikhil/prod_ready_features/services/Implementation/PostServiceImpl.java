package com.shikhilrane.shikhil.prod_ready_features.services.Implementation;

import com.shikhilrane.shikhil.prod_ready_features.dto.PostDto;
import com.shikhilrane.shikhil.prod_ready_features.entities.PostEntity;
import com.shikhilrane.shikhil.prod_ready_features.exceptions.ResourceNotFoundException;
import com.shikhilrane.shikhil.prod_ready_features.repositories.PostRepository;
import com.shikhilrane.shikhil.prod_ready_features.services.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public Optional<PostDto> getPostById(Long id) {
        Optional<PostEntity> byId = postRepository.findById(id);
        return byId
                .map(gotById -> modelMapper.map(gotById, PostDto.class));
    }


    @Override
    public List<PostDto> getAllPosts() {
        List<PostEntity> all = postRepository.findAll();
        return all
                .stream()
                .map(postEntity -> modelMapper.map(postEntity, PostDto.class))
                .toList();
    }

    @Override
    public PostDto createNewPost(PostDto inputPost) {
        PostEntity postEntity = modelMapper.map(inputPost, PostEntity.class);
        PostEntity save = postRepository.save(postEntity);
        return modelMapper.map(save, PostDto.class);
    }

    public boolean isExist(Long id){
        return postRepository.existsById(id);
    }

    @Override
    public PostDto updatePostById(Long id, PostDto inputPost) {
        PostEntity existing = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found with id : " + id)); // bring from DB
        modelMapper.map(inputPost, existing);   // Map dto with entity
        existing.setId(id);                     // Set id to change
        PostEntity saved = postRepository.save(existing);   // save in DB
        return modelMapper.map(saved, PostDto.class);   // Map entity with dto
    }

}
