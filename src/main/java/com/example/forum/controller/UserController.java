package com.example.forum.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.forum.service.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    UserService userService;

    /*
     * ユーザ登録処理
     * 
     */
    @PostMapping("/adduser")
    public void addUser(@RequestBody Map<String, String> requestBody) {
        String userName = requestBody.get("userName");
        String userId = requestBody.get("userId");
        userService.saveUser(userId, userName);
    }
}
