package com.example.banksecurity.Api;

public class ApiException extends RuntimeException{
    public ApiException(String message){
        super(message);
    }
}
