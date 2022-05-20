package com.example.backend.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@IdClass(IcAdmInfoPk.class)
@Entity(name = "user_job")
@Data
public class UserJob implements Serializable {
    @Id
    @Column(name = "user_id")
    private String userId;
    @Id
    @Column(name = "job_id")
    private int jobId;

}
