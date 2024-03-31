package com.example.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.forum.repository.entity.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {

    // いいね済み登録がされているか確認する
    Like findByUserIdAndReportId(String userId, Integer reportId);

    // いいね済みを削除する
    void deleteByUserIdAndReportId(String userId, Integer reportId);
}
