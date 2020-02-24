package com.example.login_demo.model;

import com.example.login_demo.entity.User;

import java.util.Map;

public class register {

   private int code;

   private String msg;

   private User user;


   Map<String,Object> map;

    public register(int code, String msg, Map<String, Object> map) {
        this.code = code;
        this.msg = msg;
        this.map = map;
    }

    public register(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public register(int code,String msg, User user){
        this.code =code;
        this.msg = msg;
        this.user = user;
    }


    public int getCode() {
        return code;
    }

    public register setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public register setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public register setMap(Map<String, Object> map) {
        this.map = map;
        return this;
    }

    public User getUser() {
        return user;
    }

    public register setUser(User user) {
        this.user = user;
        return this;
    }
}
