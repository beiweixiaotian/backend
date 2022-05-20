package com.example.backend.service;

import com.example.backend.entity.UserJob;

import java.util.List;
import java.util.Map;

public interface User_JobService {
    public Map<String, Object> getUser_Job();
    public Map<String, Object> getUserJobByUser(String user_id);
    public Map<String, Object> getUserJobByJob(int job_id);
    public Map<String, Object> getUserJobByUserJob(String user_id, int job_id);
    public Map<String, Object> saveUserJob(UserJob userJob);
    public Map<String, Object> deleteUserJob(UserJob userJob);
    public Map<String, Object> getCollections(String user_id);
}
