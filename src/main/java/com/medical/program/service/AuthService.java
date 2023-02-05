package com.medical.program.service;

import com.medical.program.dto.request.EmployeeRequest;
import com.medical.program.model.entity.Employee;

public interface AuthService {

    Employee getEmployeeByPhone(String phone);
    Employee register(EmployeeRequest employeeRequest);
}
