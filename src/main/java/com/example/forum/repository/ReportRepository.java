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
     * 
     */
    @Query("SELECT r.id, r.content, u.userId, u.userName, u.profileImageUrl, r.createdDate, r.updatedDate FROM Report r LEFT JOIN User u ON r.userId = u.userId")
    List<Object[]> findAllReportsWithUserInfo();

    /**
     * ログイン済みユーザの投稿を取得する処理
     * 
     */
    @Query("SELECT r.id, r.content, u.userId, u.userName, u.profileImageUrl, r.createdDate, r.updatedDate FROM Report r LEFT JOIN User u ON r.userId = u.userId WHERE r.userId = :userId")
    List<Object[]> findByUserId(@Param("userId") String userId);
}
