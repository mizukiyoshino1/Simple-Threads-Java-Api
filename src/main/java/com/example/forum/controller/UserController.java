package com.example.forum.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.forum.controller.form.UserForm;
import com.example.forum.repository.entity.User;
import com.example.forum.service.UserService;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "${front.url}")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * ユーザ情報取得処理
     * 
     */
    @PostMapping("/userinfo")
    public UserForm getUserInfo(@RequestBody Map<String, String> requestBody) {
        String userId = requestBody.get("userId");
        return userService.findUserInfo(userId);
    }

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

    /*
     * ユーザ情報編集処理
     * 
     */
    @PostMapping("/useredit")
    public void userEdit(@RequestBody Map<String, String> requestBody) {

        // UserエンティティをDI
        User userInfo = new User();
        userInfo.setUserId(requestBody.get("userId"));
        userInfo.setUserName(requestBody.get("userName"));
        userInfo.setProfileText(requestBody.get("profileText"));
        userInfo.setProfileImageUrl(requestBody.get("profileImgUrl"));

        // DBに保存
        userService.updateUserInfo(userInfo);
    }
}
