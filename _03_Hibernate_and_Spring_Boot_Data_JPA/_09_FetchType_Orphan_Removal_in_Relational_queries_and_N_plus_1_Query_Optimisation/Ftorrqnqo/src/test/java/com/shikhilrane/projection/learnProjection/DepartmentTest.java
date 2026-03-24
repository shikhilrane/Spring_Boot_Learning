package com.shikhilrane.projection.learnProjection;

import com.shikhilrane.projection.learnProjection.entities.Department;
import com.shikhilrane.projection.learnProjection.services.DepartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DepartmentTest {

    @Autowired
    private DepartmentService departmentService;

    @Test
    public void creatingDept(){
        Department department = Department.builder()
                .name("Paediatrics")
                .build();

        Department department1 = departmentService.createDepartment(department, 4L);
        System.out.println(department1);
    }

    @Test
    public void deleteDept(){
        departmentService.deleteDeptartment(7L);
    }

    @Test
    public void listOfDept(){

        Long doctorId = 4L;
        Long[] deptids = {1L,2L,3L};
        for (Long dpt : deptids){
            System.out.println(departmentService.assignDepartmentsToDoctors(dpt,doctorId));
        }
    }
}
