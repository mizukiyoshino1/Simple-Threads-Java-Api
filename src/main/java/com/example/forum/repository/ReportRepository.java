package com.example.forum.repository;

import com.example.forum.repository.entity.Report;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    /**
     * ログイン済みユーザの投稿を取得する処理
     * 
     */
    List<Report> findByUserId(String userId);
}
