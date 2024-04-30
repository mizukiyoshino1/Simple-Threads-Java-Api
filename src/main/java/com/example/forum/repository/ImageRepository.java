package com.example.forum.repository;

import com.example.forum.repository.entity.Image;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

    // 投稿に紐づく画像を習得する
    List<Image> findByReportId(Integer reportId);
}