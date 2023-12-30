package com.example.banksecurity.Service;


import com.example.banksecurity.Api.ApiException;
import com.example.banksecurity.DTO.EmployeeDTO;
import com.example.banksecurity.Model.Employee;
import com.example.banksecurity.Model.User;
import com.example.banksecurity.Repository.AuthRepository;
import com.example.banksecurity.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AuthRepository authRepository;


    public List<Employee> getAllEmployees(){

        return employeeRepository.findAll();
    }

    public void register(EmployeeDTO employeeDTO){
        User user=new User(null,employeeDTO.getUsername(),employeeDTO.getPassword(),employeeDTO.getName(),employeeDTO.getEmail(),employeeDTO.getRole(),null,null);
        user.setRole("EMPLOYEE");
        String hash=new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        authRepository.save(user);

        Employee employee=new Employee(user.getId(), employeeDTO.getPosition(),employeeDTO.getSalary(),user);
        employeeRepository.save(employee);

    }


    public void updateEmployee(Integer id, EmployeeDTO employeeDTO, Integer auth ){
        User user =authRepository.findUserById(auth);
        Employee oldEmployee = employeeRepository.findEmployeeById(id);
        if(user==null){
            throw new ApiException("User Not Found");
        }
        if(user.getCustomer().getId()!=oldEmployee.getId()){

            throw new ApiException("You do not have the authority");
        }

        oldEmployee.setEmail(employeeDTO.getEmail());
        oldEmployee.setName(employeeDTO.getName());
        oldEmployee.setUsername(employeeDTO.getUsername());
        oldEmployee.setPassword(employeeDTO.getPassword());
        oldEmployee.setPosition(employeeDTO.getPosition());
        oldEmployee.setSalary(employeeDTO.getSalary());


        employeeRepository.save(oldEmployee);


    }

    public void deleteEmployee(Integer id, Integer auth){
        User user= authRepository.findUserById(auth);
        Employee employee=employeeRepository.findEmployeeById(id);

        if(user==null){
            throw new ApiException("User Not Found");
        }

        if(user.getEmployee().getId()!=employee.getId()){
            throw new ApiException("You do not have the authority");
        }
        employeeRepository.delete(employee);

    }
}