package com.medical.program.repository;

import com.medical.program.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findEmployeeByPhoneNumber(String phoneNumber);
}
