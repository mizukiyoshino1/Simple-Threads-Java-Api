package com.example.forum.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.forum.controller.user.UserInformation;
import com.example.forum.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * ユーザの新規登録
     * 
     */
    @PostMapping("/signup")
    public void signUpUser(@RequestBody UserInformation userInformation) {
        userService.signUpUser(userInformation);
    }

    /**
     * ログイン処理
     * 
     */
    @PostMapping("/login")
    public ResponseEntity<Void> isLogin(@RequestBody UserInformation userInformation) {
        // accountIdを使ってユーザが存在するか確認する
        boolean userExists = userService.checkUserExists(userInformation.getAccountId(), userInformation.getPassword());
        if (userExists) {
            return ResponseEntity.ok().build(); // 存在すれば200を返す
        } else {
            return ResponseEntity.notFound().build(); // 存在しなければ404を返す
        }
    }

}
