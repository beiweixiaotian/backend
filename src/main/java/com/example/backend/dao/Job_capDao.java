package com.example.backend.dao;

import com.example.backend.entity.Job;
import com.example.backend.entity.Job_cap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Job_capDao {
    @Select("select * from job_cap where items = #{items}")
    List<Job_cap> getJobs(String items);
}
