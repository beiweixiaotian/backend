package com.example.backend.entity;

public class Job_cap {
    private int cap_id;
    private String job_name;
    private String support;
    private String items;

    public int getCap_id() {
        return cap_id;
    }

    public void setCap_id(int cap_id) {
        this.cap_id = cap_id;
    }

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }
}
