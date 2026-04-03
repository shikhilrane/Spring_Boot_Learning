package com.shikhilrane.shikhil.prod_ready_features.services;

import com.shikhilrane.shikhil.prod_ready_features.dto.PostAuditDto;

import java.util.List;

public interface PostAuditService {
    List<PostAuditDto> getAllRevsForId(Long postId);
}
