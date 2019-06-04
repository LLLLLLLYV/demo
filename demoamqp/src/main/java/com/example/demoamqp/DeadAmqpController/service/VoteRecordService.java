package com.example.demoamqp.DeadAmqpController.service;

import com.example.demoamqp.DeadAmqpController.bean.VoteRecord;

import java.util.List;

public interface VoteRecordService {

    List<VoteRecord> queryVoteRecord();

    Integer queryVoteRecordCount();
}
