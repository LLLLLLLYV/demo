package com.example.demoamqp.DeadAmqpController.service.impl;

import com.example.demoamqp.DeadAmqpController.bean.Student;
import com.example.demoamqp.DeadAmqpController.dao.StudentDao;
import com.example.demoamqp.DeadAmqpController.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDao studentDao;
    @Override
    public int addStudent(Student student) {
        return studentDao.addStudent(student);
    }

    @Override
    public List<Student> queryStudentByName(String name) {
        return studentDao.queryStudentByName(name);
    }
}
