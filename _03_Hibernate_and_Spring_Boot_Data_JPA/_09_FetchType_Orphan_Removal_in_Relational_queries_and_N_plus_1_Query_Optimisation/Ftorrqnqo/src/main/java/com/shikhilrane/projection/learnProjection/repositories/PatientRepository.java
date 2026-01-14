package com.shikhilrane.projection.learnProjection.repositories;

import com.shikhilrane.projection.learnProjection.dto.BloodGroupStats;
import com.shikhilrane.projection.learnProjection.dto.CPatientInfo;
import com.shikhilrane.projection.learnProjection.dto.IPatientInfo;
import com.shikhilrane.projection.learnProjection.entities.Patient;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("select p.id as id, p.name as name, p.email as email from Patient p")
    List<IPatientInfo> getAllPatientsInfo();

    @Query("select new com.shikhilrane.projection.learnProjection.dto.CPatientInfo (p.id, p.name) from Patient p")
    List<CPatientInfo> getAllPatientsInfoConcrete();

    @Query("select new com.shikhilrane.projection.learnProjection.dto.BloodGroupStats (p.bloodGroup, COUNT(p)) from Patient p group by p.bloodGroup order by COUNT(p) DESC")
    List<BloodGroupStats> getCountOfBloodGroupPeople();

    @Transactional
    @Modifying
    @Query("update Patient p set p.name = :name where p.id = :id")
    int updatePatientNameWithId(@Param("name") String name, @Param("id") Long id);
}
