package com.example.backend.service;

import com.example.backend.entity.Job;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface JobService {
    public Map<String, Object> getJobByName(String job_name);
    public Map<String, Object> getJobSalByName(String job_name);
    public Map<String, Object> getJobSalNum(String job_name, double sal1, double sal2);
    public Map<String, Object> getJobByTitle(String job_title, Pageable pageable);
    public Map<String, Object> getJobs(String user_id);
    public Map<String, Object> getJobsByRan();
    public Map<String, Object> getJobByNo(int job_no);
    public Map<String, Object> getSalByName();
    public Map<String, Object> getSalByCity();
    public Map<String, Object> getRecommend(String current_user);

}
