package com.major.domain;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class User implements Serializable {
    private String username;
    private String password;
    private String college;
    private String phonecall;
    private String email;
    private int id;

    public String getPhonecall() {
        return phonecall;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", college='" + college + '\'' +
                ", phonecall='" + phonecall + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                ", secretKey='" + secretKey + '\'' +
                '}';
    }

    public void setPhonecall(String phonecall) {
        this.phonecall = phonecall;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    private String secretKey;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getCall() {
        return phonecall;
    }

    public void setCall(String call) {
        this.phonecall = call;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
