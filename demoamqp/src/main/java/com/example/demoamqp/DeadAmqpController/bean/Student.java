package com.example.demoamqp.DeadAmqpController.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 学生表
 */
@Data
public class Student implements Serializable {

    private Integer id;

    private String name;

    private Integer age;

    private Integer sex;

    private Integer courseId;

    /**
     * 课程
     */
    private List<Course> courseList;
}
