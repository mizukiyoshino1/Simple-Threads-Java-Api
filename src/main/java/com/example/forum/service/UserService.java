package com.example.forum.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.forum.controller.user.UserInformation;
import com.example.forum.repository.UserRepository;
import com.example.forum.repository.entity.User;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    /**
     * ユーザの新規登録
     * 
     */
    public void signUpUser(UserInformation reqUserInfo) {
        User addUser = setUserEntity(reqUserInfo);
        userRepository.save(addUser);
    }

    /**
     * ログイン確認
     * 
     */
    public boolean checkUserExists(String accountId, String password) {
        Optional<User> userOptional = userRepository.findByAccountIdAndPassword(accountId, password);
        return userOptional.isPresent();
    }

    /**
     * リクエストから取得した情報をEntityに設定
     * 
     */
    private User setUserEntity(UserInformation reqUserInfo) {
        User user = new User();
        user.setId(reqUserInfo.getId());
        user.setName(reqUserInfo.getName());
        user.setAccountId(reqUserInfo.getAccountId());
        user.setPassword(reqUserInfo.getPassword());
        return user;
    }
}
