package com.medical.program.dto.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {
    private String phoneNumber;
    private String password;
    private String fullName;
    private String avatar;
    private String role;
}
