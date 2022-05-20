package com.example.backend.service;

import com.example.backend.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserService {
    public Map<String, Object> addUser(User user);
    public Map<String, Object> updateUser(User user);
    public Map<String, Object> getAllUsers();
    public Map<String, Object> getUserById(String user_id, String user_pwd);
    public Map<String, Object> deleteUser(String user_id);
    public Map<String, Object> getJob(String user_id);
    public Map<String, Object> getUser(String user_id);
}
