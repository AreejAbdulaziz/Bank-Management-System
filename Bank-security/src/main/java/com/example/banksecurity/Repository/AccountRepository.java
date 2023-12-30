package com.example.banksecurity.Repository;

import com.example.banksecurity.Model.Account;
import com.example.banksecurity.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
    Account findAccountById(Integer id);

    List<Account> findAllById(User user);
}
