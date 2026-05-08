package com.shikhilrane.testing.TestingApplication.services.impl;

import com.shikhilrane.testing.TestingApplication.entities.Employee;
import com.shikhilrane.testing.TestingApplication.entities.SalaryAccount;
import com.shikhilrane.testing.TestingApplication.repositories.SalaryAccountRepository;
import com.shikhilrane.testing.TestingApplication.services.SalaryAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)                  // Always creates a new transaction for methods of this class.
public class SalaryAccountServiceImpl implements SalaryAccountService {

    private final SalaryAccountRepository salaryAccountRepository;      // Repository used to perform SalaryAccount DB operations.

    @Override
    public void createAccount(Employee employee) {

        // If by any mean, we want there is no need to create SalaryAccount for employee name with "Shikhil", then Employee will be created but 'SalaryAccount' won't be created
        if (employee.getName().equals("Shikhil")) {
            throw new RuntimeException("Shikhil is not allowed to create SalaryAccount");
        }

        SalaryAccount salaryAccount = SalaryAccount.builder()           // Creates SalaryAccount object using builder pattern.
                .employee(employee)                                     // Sets employee in SalaryAccount.
                .balance(BigDecimal.ZERO)                               // Sets initial balance as zero.
                .build();                                               // Builds the SalaryAccount object.

        salaryAccountRepository.save(salaryAccount);                    // Saves SalaryAccount into database.
    }
}