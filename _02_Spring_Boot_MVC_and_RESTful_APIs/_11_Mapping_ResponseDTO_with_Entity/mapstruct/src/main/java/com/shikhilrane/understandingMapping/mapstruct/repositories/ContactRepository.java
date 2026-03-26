package com.shikhilrane.understandingMapping.mapstruct.repositories;

import com.shikhilrane.understandingMapping.mapstruct.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
