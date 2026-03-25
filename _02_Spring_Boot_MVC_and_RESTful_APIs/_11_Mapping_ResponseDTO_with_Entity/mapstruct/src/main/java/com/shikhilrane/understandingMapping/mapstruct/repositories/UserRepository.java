package com.shikhilrane.understandingMapping.mapstruct.repositories;

import com.shikhilrane.understandingMapping.mapstruct.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
