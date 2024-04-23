package com.example.forum.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.forum.controller.form.LikeNotificationForm;
import com.example.forum.repository.LikeRepository;
import com.example.forum.repository.entity.Like;

import jakarta.transaction.Transactional;

@Service
public class LikeService {

    @Autowired
    LikeRepository likeRepository;

    /**
     * いいね通知取得処理
     * 
     * @return
     */
    public List<LikeNotificationForm> findAllLikes(String userId) {
        List<Object[]> results = likeRepository.findReportDetailsByUserId(userId);
        List<LikeNotificationForm> likeNotification = setLikeNotificationForm(results);
        return likeNotification;
    }

    /**
     * いいね済みか確認する処理
     * 
     * @return true: いいね済み , false: いいね済みでない
     */
    public boolean findLike(String userId, Integer reportId) {
        Like isLiked = likeRepository.findByUserIdAndReportId(userId, reportId);

        // 値が存在していればtrueを返す
        if (isLiked != null) {
            // いいね済み
            return true;
        } else {
            // いいね済みでない
            return false;
        }
    }

    /**
     * いいね済み登録処理
     * 
     */
    public void likeSave(String userId, Integer reportId) {
        Like like = new Like();
        like.setUserId(userId);
        like.setReportId(reportId);

        // DBに登録
        try {
            likeRepository.save(like);
        } catch (Exception e) {
            // エラーログを出力
            e.printStackTrace();
        }
    }

    /**
     * いいね済み削除処理
     * 
     */
    @Transactional
    public void deleteLike(String userId, Integer reportId) {
        likeRepository.deleteByUserIdAndReportId(userId, reportId);
    }

    /*
     * DBから取得したデータをFormに設定
     * 
     */
    private List<LikeNotificationForm> setLikeNotificationForm(List<Object[]> results) {
        List<LikeNotificationForm> likeNotification = new ArrayList<>();

        for (Object[] result : results) {
            LikeNotificationForm notification = new LikeNotificationForm();
            notification.setReportId((Integer) result[0]);
            notification.setContent((String) result[1]);
            notification.setUserId((String) result[2]);
            notification.setUserName((String) result[3]);
            notification.setProfileImageUrl((String) result[4]);
            notification.setCreatedDate((Timestamp) result[5]);
            likeNotification.add(notification);
        }
        return likeNotification;
    }
}
