package com.example.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.forum.repository.UserRepository;
import com.example.forum.repository.entity.User;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    /*
     * ユーザー追加処理
     */
    public void saveUser(String userId, String userName) {
        User user = new User();
        user.setUserId(userId);
        user.setUserName(userName);
        userRepository.save(user);
    }
}
