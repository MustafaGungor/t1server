package com.mebitech.web.controller;

/**
 * Created by tayipdemircan on 24.10.2016.
 */
public class UserDto {
    private String userName;
    private String password;
    private Long levelId;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }
}
