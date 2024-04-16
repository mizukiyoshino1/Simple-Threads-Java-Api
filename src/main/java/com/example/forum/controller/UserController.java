package com.example.forum.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
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
@RequestMapping("/api")
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
        String userId = requestBody.get("userId");
        String userName = requestBody.get("userName");
        String profileImgUrl = requestBody.get("profileImgUrl");
        String profileText = requestBody.get("profileText");

        // 空のfilepathを定義
        String filePath = "";

        // プロフィール画像がBase64でエンコードされている場合
        if (profileImgUrl != null && profileImgUrl.startsWith("data:image")) {
            try {
                // Base64データから画像バイト配列に変換
                String[] parts = profileImgUrl.split(",");
                String base64Data = parts[1];
                byte[] imageDataBytes = Base64.getDecoder().decode(base64Data);

                // 保存先フォルダのパス
                String uploadDir = System.getProperty("user.dir") + "/profileImg";

                // フォルダが存在しない場合は作成
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdir();
                }

                // 保存先ファイルパスとファイル名
                String fileName = "profile_image_" + userId + ".png"; // 例: ユーザーIDに基づくファイル名
                filePath = uploadDir + "/" + fileName;

                // ファイルに書き込み
                FileOutputStream fos = new FileOutputStream(filePath);
                fos.write(imageDataBytes);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                // エラー処理
                return;
            }
        }

        // UserエンティティをDI
        User userInfo = new User();
        userInfo.setUserId(userId);
        userInfo.setUserName(userName);
        userInfo.setProfileText(profileText);
        userInfo.setProfileImageUrl(filePath);

        // DBに保存
        userService.updateUserInfo(userInfo);
    }
}
