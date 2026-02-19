package com.demo.employee.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    private String department;

    @NotNull(message = "Salary is required")
    private Double salary;
}

