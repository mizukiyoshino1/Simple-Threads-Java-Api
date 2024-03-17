package com.example.forum.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.forum.controller.form.UserForm;
import com.example.forum.repository.UserRepository;
import com.example.forum.repository.entity.User;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    /**
     * ユーザ情報取得処理
     * 
     */
    public UserForm findUserInfo(String userId) {
        Optional<User> result = userRepository.findByUserId(userId);
        UserForm userInfo = setUserForm(result.orElse(null));
        return userInfo;
    }

    /*
     * ユーザー追加処理
     */
    public void saveUser(String userId, String userName) {
        User user = new User();
        user.setUserId(userId);
        user.setUserName(userName);
        userRepository.save(user);
    }

    /**
     * ユーザ情報変更処理
     * 
     */
    public void updateUserInfo(User reqUserInfo) {
        // ユーザIDを使用して既存のユーザ情報を取得
        Optional<User> existingUserOptional = userRepository.findByUserId(reqUserInfo.getUserId());

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            // 新しいユーザ情報で既存のユーザ情報を更新
            existingUser.setUserName(reqUserInfo.getUserName());
            existingUser.setProfileText(reqUserInfo.getProfileText());
            existingUser.setProfileImageUrl(reqUserInfo.getProfileImageUrl());

            // 更新されたユーザ情報を保存
            userRepository.save(existingUser);
        } else {
            // エラー処理: ユーザが見つからない場合
            throw new RuntimeException("User not found with ID: " + reqUserInfo.getUserId());
        }
    }

    /*
     * DBから取得したデータをFormに設定
     */
    private UserForm setUserForm(User result) {

        if (result == null) {
            // ユーザが見つからない場合の処理
            return null;
        }

        UserForm userForm = new UserForm();

        userForm.setId((Integer) result.getId());
        userForm.setUserId((String) result.getUserId());
        userForm.setUserName((String) result.getUserName());
        // ファイルパスからBase64エンコードされた画像データを取得
        String filePath = (String) result.getProfileImageUrl(); // 仮のファイルパス取得方法
        byte[] imageDataBytes = null;
        if (filePath != null) {
            try {
                File file = new File(filePath);
                imageDataBytes = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
                // エラー処理
            }

            // Base64エンコードされた画像データを文字列に変換して設定
            if (imageDataBytes != null) {
                String imageDataString = Base64.getEncoder().encodeToString(imageDataBytes);
                userForm.setProfileImageUrl("data:image/png;base64," + imageDataString);
            } else {
                // エラー時の処理
                userForm.setProfileImageUrl(""); // エラー時は空文字列を設定するなどの処理を行う
            }
        } else {
            userForm.setProfileImageUrl("");
        }
        userForm.setProfileText((String) result.getProfileText());

        return userForm;
    }

}
