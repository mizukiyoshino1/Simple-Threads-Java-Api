package com.example.forum.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.forum.repository.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // ユーザ情報の取得
    Optional<User> findByUserId(String userId);
}
