package com.example.backend.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class IcAdmInfoPk implements Serializable {

    private static final long serialVersionUID = -1570834456846591727L;

    private String userId;
    private int jobId;
}