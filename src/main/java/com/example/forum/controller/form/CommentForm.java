package com.example.forum.controller.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentForm {
    private int id;

    private String userId;

    private Integer reportId;

    private String commentText;

    private String userName;

    private String profileImageUrl;
}
