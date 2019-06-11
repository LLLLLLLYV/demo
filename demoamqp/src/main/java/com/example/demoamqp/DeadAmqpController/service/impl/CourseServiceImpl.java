package com.example.demoamqp.DeadAmqpController.service.impl;

import com.example.demoamqp.DeadAmqpController.bean.Course;
import com.example.demoamqp.DeadAmqpController.dao.CourseDao;
import com.example.demoamqp.DeadAmqpController.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;
    @Override
    public int addCourse(Course course) {
        return courseDao.addCourse(course);
    }
}
