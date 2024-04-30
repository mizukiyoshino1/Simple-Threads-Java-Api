package com.example.forum.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReportForm {

    private int id;

    private String content;

    private String userId;

    private String userName;

    private String profileImageUrl;

    private Integer likeFlg;

    private Integer likeCount;

    private String createdDate;

    private String updatedDate;

    private List<String> imageUrls;
}
