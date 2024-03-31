package com.example.forum.controller.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeForm {
    private int id;

    private String userId;

    private Integer reportId;

    private Integer likeFlg;

    private Integer likeCount;

    private String createdDate;
}
