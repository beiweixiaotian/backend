package com.example.backend.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "jobs")
@Data
public class Job {
    @Id
    @Column(name="job_no")
    private int jobNo;
    @Column(name="job_name")
    private String jobName;
    @Column(name="job_city")
    private String jobCity;
    @Column(name="job_title")
    private String jobTitle;
    @Column(name="company_type")
    private String companyType;
    @Column(name="job_salary")
    private String jobSalary;
    @Column(name="job_edu")
    private String jobEdu;
    @Column(name="job_cap")
    private String jobCap;
    @Column(name="job_exp")
    private String jobExp;
    @Column(name="job_href")
    private String jobHref;
    @Column(name="job_sal")
    private double jobSal;
    @Column(name="company_name")
    private String companyName;
}
