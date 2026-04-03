package com.shikhilrane.shikhil.prod_ready_features.controllers;

import com.shikhilrane.shikhil.prod_ready_features.dto.PostAuditDto;
import com.shikhilrane.shikhil.prod_ready_features.entities.PostEntity;
import com.shikhilrane.shikhil.prod_ready_features.services.PostAuditService;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/postAudit")
@RequiredArgsConstructor
public class PostAuditController {

    private final PostAuditService postAuditService;

    @GetMapping("/posts/{postId}")
    public ResponseEntity<List<PostAuditDto>> getPostRevision(@PathVariable Long postId){
        List<PostAuditDto> allRevs = postAuditService.getAllRevsForId(postId);
        return ResponseEntity.ok(allRevs);
    }
}
