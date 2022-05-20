package com.example.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.backend.dao.JobDao;
import com.example.backend.entity.Job;
import com.example.backend.service.JobService;
import org.apache.spark.status.api.v1.OneJobResource;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;

@RestController
public class JobController {
    @Autowired
    JobService jobService;
    @Autowired
    JobDao jobDao;
    @RequestMapping("/getJobByNo")
    public Map<String, Object> getJobByNo(@RequestBody JSONObject jsonObject){
        int job_no = jsonObject.getInteger("job_no");
        return jobService.getJobByNo(job_no);
    }
    @RequestMapping("/getJobSalNum")
    public Map<String, Object> getJobSalNum(@RequestBody JSONObject jsonObject){
        String job_name = jsonObject.getString("job_name");
        double sal1 = jsonObject.getDouble("sal1");
        double sal2 = jsonObject.getDouble("sal2");
        return jobService.getJobSalNum(job_name, sal1, sal2);
    }
    @RequestMapping("/getJobByName")
    public Map<String, Object> getJobByName(@RequestBody JSONObject jsonObject){
        String job_name = jsonObject.getString("job_name");
        System.out.println(job_name);
        return jobService.getJobByName(job_name);
    }
    @RequestMapping("/getJobSalByName")
    public Map<String, Object> getJobSalByName(@RequestBody JSONObject jsonObject){
        String job_name = jsonObject.getString("job_name");
        System.out.println(job_name);
        return jobService.getJobSalByName(job_name);
    }
    @RequestMapping("/getJobByTitle")
    public Map<String, Object> findJobs(@RequestBody JSONObject jsonObject){
        String job_title = jsonObject.getString("job_title");
        int pageNum = jsonObject.getInteger("pageNum");
        int pageSize = jsonObject.getInteger("pageSize");
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        return jobService.getJobByTitle("%" + job_title + "%", pageable);
    }
    @RequestMapping("/getJob")
    public Map<String, Object> getJob(@RequestBody JSONObject user_id){
        return jobService.getJobs((String) user_id.get("user_id"));
    }
    @RequestMapping("/getRanJob")
    public Map<String, Object> getRanJob(){
        return jobService.getJobsByRan();
    }
    @RequestMapping("/getSalByName")
    public Map<String, Object> getSalByName(){

        return jobService.getSalByName();
    }
    @RequestMapping("/getSalByCity")
    public Map<String, Object> getSalByCity(){
        return jobService.getSalByCity();
    }
    @RequestMapping("/getRecommend")
    public Map<String, Object> getRecommend(@RequestBody JSONObject jsonObject){
        String current_user = jsonObject.getString("user_id");
        return jobService.getRecommend(current_user);
    }
}
