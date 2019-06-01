package com.example.demoamqp.DeadAmqpController.bean;

import com.example.demoamqp.DeadAmqpController.annotation.ExcelValue;
import com.example.demoamqp.DeadAmqpController.config.ExcelField;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantsCustomer {

   // @ExcelValue(name = "id")
    private String id;
    @ExcelField(title="姓名", type = ExcelField.TYPE_IMPORT_EXPORT, align= ExcelField.ALIGN_CENTER, sort = 1)
    @ExcelValue(name = "姓名")
    private String name;

    @ExcelField(title="年龄", type = ExcelField.TYPE_IMPORT_EXPORT, align= ExcelField.ALIGN_CENTER, sort = 1)
    @ExcelValue(name = "年龄")
    private int age;

 @ExcelField(title="部门ID", type = ExcelField.TYPE_IMPORT_EXPORT, align= ExcelField.ALIGN_CENTER, sort = 1)
    @ExcelValue(name = "部门ID")
    private String deptId;


 @ExcelField(title="员工号", type = ExcelField.TYPE_IMPORT_EXPORT, align= ExcelField.ALIGN_CENTER, sort = 1)
    @ExcelValue(name = "员工号")
    private String empno;
}
