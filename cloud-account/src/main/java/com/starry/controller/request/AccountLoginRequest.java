package com.starry.controller.request;

import lombok.Data;

@Data
public class AccountLoginRequest {

    private String phone;

    private String pwd;
}