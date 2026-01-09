package com.serviceLayer.service.repositories;

import com.serviceLayer.service.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

}

// @Repository -> It is annotation use to define this interface as repository