package com.example.banksecurity.Controller;

import com.example.banksecurity.Model.Account;
import com.example.banksecurity.Service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {


    private final AccountService accountService;

    //Admin
    @GetMapping("/get")
    public ResponseEntity getAllAccounts() {
        return ResponseEntity.status(200).body(accountService.getAllAccount());
    }


    //Customer
    @GetMapping("/getMyAccounts")
    public ResponseEntity getMyAccounts(@AuthenticationPrincipal Integer auth) {
        return ResponseEntity.status(200).body(accountService.getMyAccounts(auth));
    }

    //Customer
    @PostMapping("/add")
    public ResponseEntity addMyAccount(@Valid @RequestBody Account account,@AuthenticationPrincipal Integer auth ){
        accountService.addMyAccount(account,auth);
        return ResponseEntity.status(200).body("account added");
    }

    //Customer
    @PutMapping("update/{id}")
    public ResponseEntity updateAccount(@PathVariable Integer id, @RequestBody @Valid Account account, @AuthenticationPrincipal Integer auth){
        accountService.updateAccount(id,account,auth);
        return ResponseEntity.status(200).body("account updated");
    }

    //Admin //Customer
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAccount(@PathVariable Integer id, @AuthenticationPrincipal Integer auth){
        accountService.deleteAccount(id,auth);
        return ResponseEntity.status(200).body("Account deleted");
    }

    //Customer //Admin
    @PutMapping("/active/{id}")
    public ResponseEntity activeBankAccount(@AuthenticationPrincipal Integer auth,@PathVariable Integer id){
        accountService.activeBankAccount(auth,id);
        return ResponseEntity.status(200).body("Account Activated");
    }

    //Customer
    @PutMapping("/deposit/{id}/{amount}")
    public ResponseEntity deposit(@AuthenticationPrincipal Integer auth,@PathVariable Integer id,@PathVariable Integer amount){
        accountService.deposit(auth,id,amount);
        return ResponseEntity.status(200).body("Deposited Successfully");
    }

    //Customer
    @PutMapping("/withdraw/{id}/{amount}")
    public ResponseEntity withdraw(@AuthenticationPrincipal Integer auth,@PathVariable Integer id,@PathVariable Integer amount){
        accountService.withdraw(auth,id,amount);
        return ResponseEntity.status(200).body("Withdraw Done");
    }

    //Customer
    @PutMapping("/transfer/{account_id1}/{account_id2}/{amount}")
    public ResponseEntity TransferBetweenAccounts(@AuthenticationPrincipal Integer auth,@PathVariable Integer account_id1,@PathVariable Integer account_id2,@PathVariable Integer amount){
        accountService.TransferBetweenAccounts(auth, account_id1, account_id2, amount);
        return ResponseEntity.status(200).body("Transfer Done");
    }
}