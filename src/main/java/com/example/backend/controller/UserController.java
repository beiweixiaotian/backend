package com.example.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.backend.entity.Job;
import com.example.backend.entity.User;
import com.example.backend.service.JobService;
import com.example.backend.service.Job_capService;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    JobService jobService;
    @Autowired
    Job_capService job_capService;
    @RequestMapping("/register")
    public Map<String, Object> addUser(@RequestBody User user){
        user.setUser_pwd(user.getUser_pwd());
        return userService.addUser(user);
    }
    @RequestMapping("/updateUser")
    public Map<String, Object> updateUser(@RequestBody User user){
        user.setUser_cap(user.getUser_cap());
        return userService.updateUser(user);
    }
    @RequestMapping("/login")
    public Map<String, Object> getUserById(@RequestBody User user){
//        user.setUser_pwd(user.getUser_pwd());
        System.out.println(user);
        return userService.getUserById(user.getUser_id(), user.getUser_pwd());
    }
    @RequestMapping("/deleteUser")
    public Map<String, Object> deleteUser(@RequestParam String user_id){
        return userService.deleteUser(user_id);
    }
//    @RequestMapping("/getUser")
//    public Map<String, Object> getUser(@RequestBody JSONObject user_id){
//        return userService.getUser((String) user_id.get("user_id"));
//    }
}
