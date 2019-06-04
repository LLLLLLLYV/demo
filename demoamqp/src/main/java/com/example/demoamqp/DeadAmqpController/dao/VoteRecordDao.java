package com.example.demoamqp.DeadAmqpController.dao;

import com.example.demoamqp.DeadAmqpController.bean.VoteRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRecordDao {

    List<VoteRecord> queryVoteRecord();

    Integer queryVoteRecordCount();
}
