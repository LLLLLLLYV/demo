package com.example.demoamqp.DeadAmqpController.bean;

import com.example.demoamqp.DeadAmqpController.annotation.ExcelValue;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class User implements Serializable{
    @ExcelValue(name = "ID")
    @NotBlank(message = "缺少id")
    private String id;

    @ExcelValue(name = "用户名")
    @NotBlank(message = "缺少userName")
    private String userName;

    @ExcelValue(name = "密码")
    @NotBlank(message = "缺少password")
    private String password;

    @ExcelValue(name = "状态")
    private Boolean status;
}
