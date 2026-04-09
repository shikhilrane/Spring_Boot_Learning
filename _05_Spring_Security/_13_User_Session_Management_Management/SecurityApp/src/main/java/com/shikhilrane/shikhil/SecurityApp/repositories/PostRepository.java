package com.shikhilrane.shikhil.SecurityApp.repositories;

import com.shikhilrane.shikhil.SecurityApp.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
}