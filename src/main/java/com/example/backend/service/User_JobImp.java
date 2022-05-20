package com.example.backend.service;

import com.example.backend.dao.JobDao;
import com.example.backend.dao.User_JobDao;
import com.example.backend.entity.Job;
import com.example.backend.entity.UserJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class User_JobImp implements User_JobService{
    @Autowired
    private User_JobDao user_jobDao;
    @Autowired
    private JobDao jobDao;
    @Override
    public Map<String, Object> getUser_Job() {
        Map<String, Object> map = new TreeMap<>();
        try{
            List<UserJob> user_jobs = user_jobDao.findAll();
            map.put("state", 1);
            map.put("total", user_jobs.size());
            map.put("data", user_jobs);

        } catch (Exception e){
            map.put("state", 0);
        }
        return map;
    }

    @Override
    public Map<String, Object> getUserJobByUser(String user_id) {
        Map<String, Object> map = new TreeMap<>();
        try{
            List<UserJob> user_jobs = user_jobDao.findByUserId(user_id);
            map.put("state", 1);
            map.put("total", user_jobs.size());
            map.put("data", user_jobs);

        } catch (Exception e){
            map.put("state", 0);
        }
        return map;
    }

    @Override
    public Map<String, Object> getUserJobByJob(int job_id) {
        Map<String, Object> map = new TreeMap<>();
        try{
            List<UserJob> user_jobs = user_jobDao.findByJobId(job_id);
            map.put("state", 1);
            map.put("total", user_jobs.size());
            map.put("data", user_jobs);

        } catch (Exception e){
            map.put("state", 0);
        }
        return map;
    }

    @Override
    public Map<String, Object> getUserJobByUserJob(String user_id, int job_id) {
        Map<String, Object> map = new TreeMap<>();
        try{
            UserJob user_job = user_jobDao.findByUserIdAndJobId(user_id, job_id);
            if (user_job!=null){
                map.put("data", 1);
            }
            else{
                map.put("data", 0);
            }
            map.put("state", 1);
        } catch (Exception e){
            map.put("state", 0);
        }
        return map;
    }

    @Override
    public Map<String, Object> saveUserJob(UserJob userJob) {
        Map<String, Object> map = new TreeMap<>();
        try{
            user_jobDao.save(userJob);
            map.put("state", 1);

        } catch (Exception e){
            map.put("state", 0);
        }
        return map;
    }

    @Override
    public Map<String, Object> deleteUserJob(UserJob userJob) {
        Map<String, Object> map = new TreeMap<>();
        try{
            user_jobDao.delete(userJob);
            map.put("state", 1);

        } catch (Exception e){
            map.put("state", 0);
        }
        return map;
    }

    @Override
    public Map<String, Object> getCollections(String user_id) {
        Map<String, Object> map = new TreeMap<>();
        try{
            List<UserJob> userJobs = user_jobDao.findByUserId(user_id);
            List<Job> jobs = new ArrayList<>();
            for (UserJob userJob : userJobs){
                jobs.add(jobDao.findByJobNo(userJob.getJobId()));
            }
            map.put("state", 1);
            map.put("data", jobs);

        } catch (Exception e){
            map.put("state", 0);
        }
        return map;
    }
}
