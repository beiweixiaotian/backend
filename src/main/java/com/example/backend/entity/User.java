package com.example.backend.entity;

import com.huaban.analysis.jieba.JiebaSegmenter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Entity(name = "user")
public class User {
    @Id
    private String user_id;
    private String user_pos;
    private int user_maxsal;
    private int user_minsal;
    private String user_name;
    private String user_pwd;
    private String user_cap;
    private String user_edu;
    private int user_exp;
    private int user_has_job;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_pos() {
        return user_pos;
    }

    public void setUser_pos(String user_pos) {
        this.user_pos = user_pos;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getUser_cap() {
        return user_cap;
    }

    public String[] getUser_caps(){
        return this.user_cap.split(";");
    }

    public void setUser_cap(String user_cap) {
        this.user_cap = user_cap;
        if (user_cap != null){
            String[] wash = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "、", ";", ",", "，", " ", "+",
                    "等", "有", "年", "熟悉", "精通", "掌握", "的", "常用", "及", "各种", "熟练", "进行", "运用", "并","能",
                    "使用", "和", "良好", "得", "语句", "地", ";", "；", "\\", "/", "以上", "习惯", "一", "", "。"};
            Set<String> set = new HashSet<>();
            Collections.addAll(set, wash);
            StringBuilder stringBuilder = new StringBuilder();
            JiebaSegmenter jiebaSegmenter = new JiebaSegmenter();
            List<String> list = new ArrayList<>(jiebaSegmenter.sentenceProcess(user_cap));
            for (String str : list) {
                if (set.contains(str)) {
                    continue;
                }
                stringBuilder.append(str).append(";");
            }
            this.user_cap = stringBuilder.toString();
        }
    }

    public String getUser_edu() {
        return user_edu;
    }

    public void setUser_edu(String user_edu) {
        this.user_edu = user_edu;
    }

    public int getUser_exp() {
        return user_exp;
    }

    public void setUser_exp(int user_exp) {
        this.user_exp = user_exp;
    }

    public int getUser_maxsal() {
        return user_maxsal;
    }

    public void setUser_maxsal(int user_maxsal) {
        this.user_maxsal = user_maxsal;
    }

    public int getUser_minsal() {
        return user_minsal;
    }

    public void setUser_minsal(int user_minsal) {
        this.user_minsal = user_minsal;
    }

    public int getUser_has_job() {
        return user_has_job;
    }

    public void setUser_has_job(int user_has_job) {
        this.user_has_job = user_has_job;
    }

    public static String getSHA256(String str){
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }
    private static String byte2Hex(byte[] bytes){
        StringBuilder stringBuffer = new StringBuilder();
        String temp = null;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

}
