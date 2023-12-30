package com.example.banksecurity.Service;


import com.example.banksecurity.Api.ApiException;
import com.example.banksecurity.DTO.CustomerDTO;
import com.example.banksecurity.Model.Customer;
import com.example.banksecurity.Model.User;
import com.example.banksecurity.Repository.AuthRepository;
import com.example.banksecurity.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;


    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }
    public void register(CustomerDTO customerDTO){
        User user=new User(null,customerDTO.getUsername(),customerDTO.getPassword(),customerDTO.getName(),customerDTO.getEmail(),customerDTO.getRole(),null,null);
        user.setRole("CUSTOMER");
        String hash=new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        authRepository.save(user);

        Customer customer=new Customer(user.getId(), customerDTO.getPhoneNumber(),user,null);
        customerRepository.save(customer);

    }


    public void updateCustomer(Integer id, CustomerDTO customerDTO,  Integer auth ){
        User user =authRepository.findUserById(auth);
        Customer oldCustomer = customerRepository.findCustomerById(id);
        if(user==null){
            throw new ApiException("User Not Found");
        }
        if(user.getCustomer().getId()!=oldCustomer.getId()){

            throw new ApiException("You do not have the authority");
        }

        oldCustomer.setEmail(customerDTO.getEmail());
        oldCustomer.setPhoneNumber(customerDTO.getPhoneNumber());
        oldCustomer.setName(customerDTO.getName());
        oldCustomer.setUsername(customerDTO.getUsername());
        oldCustomer.setPassword(customerDTO.getPassword());

        customerRepository.save(oldCustomer);


    }

    public void deleteCustomer(Integer id, Integer auth){
        User user= authRepository.findUserById(auth);
        Customer customer=customerRepository.findCustomerById(id);

        if(user==null){
            throw new ApiException("User Not Found");
        }

        if(user.getCustomer().getId()!=customer.getId()){
            throw new ApiException("You do not have the authority");
        }
        customerRepository.delete(customer);

    }

}