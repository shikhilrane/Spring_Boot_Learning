package com.shikhilrane.shikhil.prod_ready_features.repositories;

import com.shikhilrane.shikhil.prod_ready_features.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
}