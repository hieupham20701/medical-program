package com.medical.program.service.impl;

import com.medical.program.dto.request.EmployeeRequest;
import com.medical.program.model.entity.Employee;
import com.medical.program.repository.EmployeeRepository;
import com.medical.program.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final EmployeeRepository employeeRepository;
    @Override
    public Employee getEmployeeByPhone(String phone) {
        return employeeRepository.findEmployeeByPhoneNumber(phone).orElseThrow(() -> new UsernameNotFoundException("Employee is not exists!"));
    }

    @Override
    public Employee register(EmployeeRequest employeeRequest) {
        return null;
    }
}
