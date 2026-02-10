package com.shikhilrane.shikhil.SecurityApp.services.Implementation;

import com.shikhilrane.shikhil.SecurityApp.dto.PostDto;
import com.shikhilrane.shikhil.SecurityApp.entities.PostEntity;
import com.shikhilrane.shikhil.SecurityApp.repositories.PostRepository;
import com.shikhilrane.shikhil.SecurityApp.services.PostService;
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

}
