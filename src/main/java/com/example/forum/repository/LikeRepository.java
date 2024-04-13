package com.example.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.forum.repository.entity.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {

    // いいね済み登録がされているか確認する
    Like findByUserIdAndReportId(String userId, Integer reportId);

    // いいね済みを削除する
    void deleteByUserIdAndReportId(String userId, Integer reportId);

    // ログインしているユーザの投稿に対し、誰からいいねがきているか確認する
    @Query("SELECT r.id, r.content, l.userId, u.userName, u.profileImageUrl, l.createdDate " +
            "FROM Like l " +
            "JOIN (SELECT r.id AS id, r.content AS content FROM Report r WHERE r.userId = :userId) AS r " +
            "ON l.reportId = r.id " +
            "LEFT JOIN User u " +
            "ON u.userId = l.userId " +
            "ORDER BY l.createdDate DESC " +
            "LIMIT 20")
    List<Object[]> findReportDetailsByUserId(@Param("userId") String userId);

}
