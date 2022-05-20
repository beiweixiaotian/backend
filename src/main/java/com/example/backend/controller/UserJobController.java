package com.example.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.backend.entity.UserJob;
import com.example.backend.service.User_JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserJobController {
    @Autowired
    private User_JobService user_jobService;
    @RequestMapping("/getUserJob")
    public Map<String, Object> getUserJob(){
        return user_jobService.getUser_Job();
    }
    @RequestMapping("/getUserJobByUser")
    public Map<String, Object> getUserJobByUser(@RequestBody JSONObject jsonObject){
        String user_id = jsonObject.getString("user_id");
        return user_jobService.getUserJobByUser(user_id);
    }
    @RequestMapping("/getUserJobByJob")
    public Map<String, Object> getUserJobByJob(@RequestBody JSONObject jsonObject){
        int job_no = jsonObject.getInteger("job_no");
        return user_jobService.getUserJobByJob(job_no);
    }
    @RequestMapping("/getUserJobByUserJob")
    public Map<String, Object> getUserJobByUserJob(@RequestBody JSONObject jsonObject){
        String user_id = jsonObject.getString("userId");
        int job_id = jsonObject.getInteger("jobId");
        return user_jobService.getUserJobByUserJob(user_id, job_id);
    }

    @RequestMapping("/saveUserJob")
    public Map<String, Object> saveUserJob(@RequestBody UserJob userJob){
        return user_jobService.saveUserJob(userJob);
    }
    @RequestMapping("/deleteUserJob")
    public Map<String, Object> deleteUserJob(@RequestBody UserJob userJob){
        return user_jobService.deleteUserJob(userJob);
    }
    @RequestMapping("/getCollections")
    public Map<String, Object> getCollections(@RequestBody JSONObject jsonObject){
        String user_id = jsonObject.getString("user_id");
        return user_jobService.getCollections(user_id);
    }
}
