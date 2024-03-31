package com.example.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.forum.repository.LikeRepository;
import com.example.forum.repository.entity.Like;

import jakarta.transaction.Transactional;

@Service
public class LikeService {

    @Autowired
    LikeRepository likeRepository;

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
}
