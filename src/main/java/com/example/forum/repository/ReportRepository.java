package com.example.forum.repository;

import com.example.forum.repository.entity.Report;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    /**
     * ユーザに紐ずく投稿を取得する処理
     * 投稿取得時に、いいね済みかどうかを判定するフラグを含める
     * 
     */
    @Query("SELECT r.id, r.content, u.userId, u.userName, u.profileImageUrl, " +
            "CASE WHEN l.userId IS NOT NULL THEN 1 ELSE 0 END AS likeFlag, " +
            "r.likesCount, r.createdDate, r.updatedDate " +
            "FROM Report r " +
            "LEFT JOIN User u ON r.userId = u.userId " +
            "LEFT JOIN Like l ON l.reportId = r.id AND l.userId = :userId")
    List<Object[]> findAllReportsWithUserInfo(@Param("userId") String userId);

    /**
     * ログイン済みユーザの投稿を取得する処理
     * 
     */
    @Query("SELECT r.id, r.content, u.userId, u.userName, u.profileImageUrl, " +
            "CASE WHEN l.userId IS NOT NULL THEN 1 ELSE 0 END AS likeFlag, " +
            "r.likesCount, r.createdDate, r.updatedDate " +
            "FROM Report r " +
            "LEFT JOIN User u ON r.userId = u.userId " +
            "LEFT JOIN Like l ON l.reportId = r.id AND l.userId = :userId " +
            "WHERE r.userId = :userId")
    List<Object[]> findByUserId(@Param("userId") String userId);

}
