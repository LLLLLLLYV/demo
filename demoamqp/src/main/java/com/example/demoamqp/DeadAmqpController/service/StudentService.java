package com.example.demoamqp.DeadAmqpController.service;

import com.example.demoamqp.DeadAmqpController.bean.Student;

import java.util.List;

public interface StudentService {

    int addStudent(Student student);

    List<Student> queryStudentByName(String name);
}
