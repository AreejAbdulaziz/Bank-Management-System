package com.example.banksecurity.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.CreditCardNumber;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "account number cannot be null")
    //@Pattern(regexp = "^(\\d{4}-\\d{4}-\\d{4}-\\d{4})$",message = "enter correct account number")
    @CreditCardNumber
    @Column(columnDefinition = "int not null")
    private Integer accountNumber;
    @NotNull(message = "balance cannot be null")
    @Pattern(regexp = "^(0|[1-9]\\d*)$",message = "only decimal numbers")
    @Column(columnDefinition = "double")
    private double balance;
    @Column(columnDefinition = "Boolean")
    @AssertFalse(message = "is active should be false")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    @JsonIgnore
    private Customer customer;
}
