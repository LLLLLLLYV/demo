package com.example.demoamqp.DeadAmqpController.dao;

import com.example.demoamqp.DeadAmqpController.bean.Course;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseDao {

    @Insert(value = "Insert into course(id,course_name,course_grade," +
            "course_teacher,s_id) value(null,#{courseName},#{courseGrade}," +
            "#{courseTeacher},#{sId})")
    int addCourse(Course course);

    List<Course> queryCourse();
}
