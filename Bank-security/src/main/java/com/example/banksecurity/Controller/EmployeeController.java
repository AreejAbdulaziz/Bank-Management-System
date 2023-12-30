package com.example.banksecurity.Controller;

import com.example.banksecurity.DTO.EmployeeDTO;
import com.example.banksecurity.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    //Admin
    @GetMapping("/get")
    public ResponseEntity getAllEmployees(){
        return ResponseEntity.status(200).body(employeeService.getAllEmployees());
    }

    //All
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid EmployeeDTO employeeDTO) {
        employeeService.register(employeeDTO);
        return ResponseEntity.status(200).body("employee Register");
    }

    //Admin //Employee
    @PutMapping("update/{id}")
    public ResponseEntity updateEmployee(@PathVariable Integer id, @RequestBody @Valid EmployeeDTO employeeDTO, @AuthenticationPrincipal Integer auth){
        employeeService.updateEmployee(id,employeeDTO,auth);
        return ResponseEntity.status(200).body("employee updated");

    }

    //Admin //Employee
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEmployee(@PathVariable Integer id, @AuthenticationPrincipal Integer auth){
        employeeService.deleteEmployee(id,auth);
        return ResponseEntity.status(200).body("employee deleted");
    }
}