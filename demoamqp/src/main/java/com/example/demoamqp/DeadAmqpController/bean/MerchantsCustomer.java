package com.example.demoamqp.DeadAmqpController.bean;

import com.example.demoamqp.DeadAmqpController.annotation.ExcelValue;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantsCustomer {

   // @ExcelValue(name = "id")
    private String id;
    @ExcelValue(name = "姓名")
    private String name;

    @ExcelValue(name = "年龄")
    private int age;

 @ExcelValue(name = "部门ID")
    private String deptId;


 @ExcelValue(name = "员工号")
    private String empno;
}
