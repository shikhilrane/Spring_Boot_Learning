package com.shikhilrane.shikhil.prod_ready_features.services.Implementation;

import com.shikhilrane.shikhil.prod_ready_features.dto.PostAuditDto;
import com.shikhilrane.shikhil.prod_ready_features.entities.PostEntity;
import com.shikhilrane.shikhil.prod_ready_features.services.PostAuditService;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostAuditServiceImpl implements PostAuditService {

    private final EntityManagerFactory entityManagerFactory;
    private final ModelMapper modelMapper;

    @Override
    public List<PostAuditDto> getAllRevsForId(Long postId) {
        AuditReader reader = AuditReaderFactory.get(entityManagerFactory.createEntityManager());
        List<Number> revisions = reader.getRevisions(PostEntity.class, postId);
        return revisions.stream()
                .map(rev -> modelMapper.map(reader.find(PostEntity.class, postId, rev), PostAuditDto.class))
                .toList();
    }
}
