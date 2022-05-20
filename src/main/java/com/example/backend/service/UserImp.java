package com.example.backend.service;

import com.example.backend.dao.UserDao;
import com.example.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Service
public class UserImp implements UserService{
    @Autowired
    private UserDao userDao;
    @Override
    public Map<String, Object> addUser(User user) {
        Map<String, Object> map = new TreeMap<>();
        try{
            map.put("state", userDao.addUser(user));
            map.put("msg", "注册成功");
        } catch (Exception e){
            map.put("state", 0);
            map.put("msg", "名称已占用");
        }
        return map;
    }

    @Override
    public Map<String, Object> updateUser(User user) {
//        System.out.println(user);
        Map<String, Object> map = new HashMap<>();
        try{
            userDao.updateUser(user);
            map.put("state", 1);
            map.put("msg", "更新个人简历成功");
        } catch (Exception e){
            map.put("state", 0);
            map.put("msg", "修改失败");
        }
        return map;
    }

    @Override
    public Map<String, Object> getAllUsers() {
        return null;
    }

    @Override
    public Map<String, Object> getUserById(String user_id, String user_pwd) {
        Map<String, Object> map = new TreeMap<>();
        try{
            User user = userDao.getUserById(user_id);
            if (user != null && user.getUser_pwd().equals(user_pwd)){
                map.put("state", 1);
                map.put("user", user_id);
            }
            else if (user == null){
                map.put("state", 0);
                map.put("msg", "用户不存在");
            }
            else if (!user.getUser_pwd().equals(user_pwd)){
                map.put("state", 0);
                map.put("msg", "密码错误");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> deleteUser(String user_id) {
        return null;
    }

    @Override
    public Map<String, Object> getJob(String user_id) {
        return null;
    }

    @Override
    public Map<String, Object> getUser(String user_id) {
        Map<String, Object> map = new TreeMap<>();
        try{
            User user = userDao.getUserById(user_id);
            if (user != null){
                map.put("state", 1);
                map.put("user", user);
            }
            else {
                map.put("state", 0);
                map.put("msg", "用户不存在");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }
}
