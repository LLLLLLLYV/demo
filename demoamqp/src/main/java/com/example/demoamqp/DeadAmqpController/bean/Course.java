package com.example.demoamqp.DeadAmqpController.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 课程
 */
@Data
public class Course implements Serializable {

    private Integer id;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 分数
     */
    private Integer courseGrade;

    /**
     * 任课教师姓名
     */
    private String courseTeacher;

    /**
     * 学生id
     */
    private Integer sId;
}
