package com.example.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.forum.repository.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    /**
     * コメント全件取得処理
     * 
     */
    @Query("SELECT c.id, c.userId, c.reportId, c.commentText, u.userName, u.profileImageUrl " +
            "FROM Comment c LEFT OUTER JOIN User u ON u.userId = c.userId")
    List<Object[]> findCommentsWithUserInfo();
}
