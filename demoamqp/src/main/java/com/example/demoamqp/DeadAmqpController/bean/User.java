package com.example.demoamqp.DeadAmqpController.bean;

import com.example.demoamqp.DeadAmqpController.annotation.ExcelValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
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

    public void initUserMethod() {
        System.out.println("初始化user的bean之前执行");
    }

    public void destoryUserMethod() {
        System.out.println("bean销毁之后执行");
    }
}