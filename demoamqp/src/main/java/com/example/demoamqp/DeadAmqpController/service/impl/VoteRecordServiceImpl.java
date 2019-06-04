package com.example.demoamqp.DeadAmqpController.service.impl;

import com.example.demoamqp.DeadAmqpController.bean.VoteRecord;
import com.example.demoamqp.DeadAmqpController.dao.VoteRecordDao;
import com.example.demoamqp.DeadAmqpController.service.VoteRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VoteRecordServiceImpl implements VoteRecordService {

    @Autowired
    private VoteRecordDao voteRecordDao;

    @Override
    public List<VoteRecord> queryVoteRecord() {
        List<VoteRecord> voteRecords = voteRecordDao.queryVoteRecord();
        return voteRecords;
    }

    @Override
    public Integer queryVoteRecordCount() {
        Integer integer = voteRecordDao.queryVoteRecordCount();
        return integer;
    }
}
