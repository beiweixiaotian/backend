package com.example.backend.dao;

import com.example.backend.entity.Job_cap;
import com.example.backend.entity.User;
import com.example.backend.entity.UserJob;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableJpaRepositories
public interface User_JobDao extends JpaRepository<UserJob, Integer>{

    List<UserJob> findByJobId(int job_id);

    List<UserJob> findByUserId(String user_id);

    UserJob findByUserIdAndJobId(String user_id, int job_id);

}
