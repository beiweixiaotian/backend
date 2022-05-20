package com.example.backend.dao;

import com.example.backend.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
public interface UserDao {
    @Insert("insert into user (user_id, user_pwd) values(#{user_id}, #{user_pwd})")
    int addUser(User user);

    @Update("update user set user_pos=#{user_pos}, user_maxsal=#{user_maxsal}, user_minsal=#{user_minsal}, user_name=#{user_name}, user_cap=#{user_cap}, user_edu=#{user_edu}, user_exp=#{user_exp} where user_id=#{user_id}")
    int updateUser(User user);

    @Select("select * from user")
    List<User> getAllUsers();

    @Select("select * from user where user_id = #{user_id}")
    User getUserById(String user_id);

    @Delete("delete from user where user_id = #{user_id}")
    int deleteUser(String user_id);
}
