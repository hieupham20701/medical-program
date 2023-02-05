package com.medical.program.model.entity;

import com.medical.program.model.enums.EmployeeRole;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "employees")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String phoneNumber;
    private String password;
    private String fullName;
    private String avatar;
    @Enumerated(EnumType.STRING)
    private EmployeeRole role;
}
