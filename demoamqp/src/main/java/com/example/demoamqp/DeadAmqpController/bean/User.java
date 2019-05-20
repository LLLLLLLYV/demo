package com.example.demoamqp.DeadAmqpController.bean;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class User implements Serializable{

    @NotBlank(message = "缺少id")
    private String id;
    @NotBlank(message = "缺少userName")
    private String userName;
    @NotBlank(message = "缺少password")
    private String password;
    private Boolean status;
}
