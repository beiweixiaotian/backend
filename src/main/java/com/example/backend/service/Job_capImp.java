package com.example.backend.service;

import com.example.backend.dao.Job_capDao;
import com.example.backend.entity.Job;
import com.example.backend.entity.Job_cap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Job_capImp implements Job_capService{
    @Autowired
    private Job_capDao job_capDao;
    @Override
    public List<Job_cap> getJobs(String items) {
        return job_capDao.getJobs(items);
    }
}
