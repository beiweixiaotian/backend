package com.example.backend.dao;

import com.example.backend.entity.Job;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.hibernate.cfg.JPAIndexHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Component
@EnableJpaRepositories
public interface JobDao extends JpaRepository<Job, Integer>{
    // 按岗位名称查找
    List<Job> findByJobName(String job_name);
    // 按岗位标题模糊查找
    Page<Job> findByJobTitleLike(String job_title, Pageable pageable);
    // 随机查找
    @Query(value = "SELECT * FROM `jobs` AS t1 \n" +
            "JOIN (SELECT ROUND(RAND() * ((SELECT MAX(job_no) FROM `jobs`)-(SELECT MIN(job_no) FROM `jobs`))+(SELECT MIN(job_no) FROM `jobs`)) AS job_no) AS t2   \n" +
            "WHERE t1.job_no >= t2.job_no   \n" +
            "ORDER BY t1.job_no LIMIT 10; ", nativeQuery = true)
    List<Job> randomFindJobs();
    // 按编号查找
    Job findByJobNo(int job_no);

    List<Job> findByJobTitle(String job_title);

    List<Job> findByJobCityLike(String job_city);

    List<Job> findByJobNameAndJobSalGreaterThanEqualAndJobSalLessThan(String job_name, double job_sal1, double job_sal2);


}
