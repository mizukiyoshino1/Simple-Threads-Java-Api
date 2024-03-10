package com.example.forum.controller.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportForm {

    private int id;

    private String content;

    private String userId;

    private String userName;

    private String createdDate;

    private String updatedDate;

}
