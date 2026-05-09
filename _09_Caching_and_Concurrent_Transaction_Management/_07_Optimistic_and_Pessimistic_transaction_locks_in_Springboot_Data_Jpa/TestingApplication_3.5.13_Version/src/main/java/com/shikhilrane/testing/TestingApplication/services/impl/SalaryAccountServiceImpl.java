package com.shikhilrane.testing.TestingApplication.services.impl;

import com.shikhilrane.testing.TestingApplication.entities.Employee;
import com.shikhilrane.testing.TestingApplication.entities.SalaryAccount;
import com.shikhilrane.testing.TestingApplication.repositories.SalaryAccountRepository;
import com.shikhilrane.testing.TestingApplication.services.SalaryAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class SalaryAccountServiceImpl implements SalaryAccountService {

    private final SalaryAccountRepository salaryAccountRepository;      // Repository used to perform SalaryAccount DB operations.

    @Override
    public void createAccount(Employee employee) {

        // If by any mean, we want there is no need to create SalaryAccount for employee name with "Shikhil", then Employee will be created but 'SalaryAccount' won't be created
        if (employee.getName().equals("Spidey")) {
            throw new RuntimeException("Spidey is not allowed to create SalaryAccount");
        }

        SalaryAccount salaryAccount = SalaryAccount.builder()           // Creates SalaryAccount object using builder pattern.
                .employee(employee)                                     // Sets employee in SalaryAccount.
                .balance(BigDecimal.ZERO)                               // Sets initial balance as zero.
                .build();                                               // Builds the SalaryAccount object.

        salaryAccountRepository.save(salaryAccount);                    // Saves SalaryAccount into database.
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public SalaryAccount incrementBalance(Long id) {
        SalaryAccount salaryAccount = salaryAccountRepository.findById(id)                      // Fetches SalaryAccount from database using id.
                .orElseThrow(() -> new RuntimeException("Account with given id not found"));    // Throws exception if account not found.
        BigDecimal previousBalance = salaryAccount.getBalance();                                // Stores current balance of SalaryAccount.
        BigDecimal newBalance = previousBalance.add(BigDecimal.valueOf(1L));                    // Increments balance by 1.
        salaryAccount.setBalance(newBalance);                                                   // Sets updated balance into SalaryAccount object.
        return salaryAccountRepository.save(salaryAccount);                                     // Saves updated SalaryAccount into database.
    }
}