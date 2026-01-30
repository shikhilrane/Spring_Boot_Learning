package com.shikhilrane.shikhil.prod_ready_features.controllers;

import com.shikhilrane.shikhil.prod_ready_features.dto.PostAuditDto;
import com.shikhilrane.shikhil.prod_ready_features.entities.PostEntity;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/postAudit")
@RequiredArgsConstructor
public class PostAuditController {

    private final EntityManagerFactory entityManagerFactory;
    private final ModelMapper modelMapper;

    @GetMapping("/posts/{postId}")
    public List<PostAuditDto> getPostRevision(@PathVariable Long postId){
        AuditReader reader = AuditReaderFactory.get(entityManagerFactory.createEntityManager());
        List<Number> revisions = reader.getRevisions(PostEntity.class, postId);
        return revisions.stream()
                .map(rev -> modelMapper.map(reader.find(PostEntity.class, postId, rev), PostAuditDto.class))
                .toList();
    }
}
