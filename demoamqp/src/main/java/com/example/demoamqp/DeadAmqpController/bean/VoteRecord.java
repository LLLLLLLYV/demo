package com.example.demoamqp.DeadAmqpController.bean;

import com.example.demoamqp.DeadAmqpController.annotation.ExcelValue;
import lombok.Data;

@Data
public class VoteRecord {

    @ExcelValue(name = "id")
    private String id;
    @ExcelValue(name = "userId")
    private String  userId;
    @ExcelValue(name = "voteId")
    private String voteId;
    @ExcelValue(name = "groupId")
    private String groupId;
    @ExcelValue(name = "createTime")
    private String createTime;
}
