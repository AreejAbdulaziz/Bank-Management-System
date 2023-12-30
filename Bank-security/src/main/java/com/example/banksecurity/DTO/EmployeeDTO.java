package com.example.banksecurity.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDTO {
    @NotNull(message = "username cannot be null")
    @Size(min = 4,max = 10,message = "username between 4 and 10 characters")
    @Column(columnDefinition = "varchar 10 not null")
    private String username;
    @NotNull(message = "password cannot be null")
    @Size(min = 6,message = "password must be more than 6 characters") //ماله كولوم صح
    private String password;
    @NotNull(message = "name cannot be null")
    @Size(min = 2, max = 20,message = "name must be between 2 and 20 characters")
    @Column(columnDefinition = "varchar 20 not null")
    private String name;
    @Email(message = "enter correct email")
    @Column(columnDefinition = "varchar 20 ")
    private String email;
    @Pattern(regexp = "^(CUSTOMER|EMPLOYEE|ADMIN)$")
    @Column(columnDefinition = "check (role=CUSTOMER or role=EMPLOYEE or role=ADMIN)")
    private String role;


    @NotNull(message = "position cannot be null")
    private String position;
    @NotNull(message = "salary cannot be null")
    @Pattern(regexp = "^(0|[1-9]\\d*)$",message = "only decimal numbers")
    private double salary;
}
