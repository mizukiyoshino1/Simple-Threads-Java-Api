package com.example.forum.controller.form;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeNotificationForm {
    private Integer reportId;

    private String content;

    private String userId;

    private String userName;

    private String profileImageUrl;

    private Timestamp createdDate;
}
