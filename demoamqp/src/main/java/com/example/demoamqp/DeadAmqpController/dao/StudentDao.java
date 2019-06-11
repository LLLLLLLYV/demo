package com.example.demoamqp.DeadAmqpController.dao;

import com.example.demoamqp.DeadAmqpController.bean.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDao {

    Integer addStudent(Student student);

    List<Student> queryStudentByName(String name);
}
