package com.example.forum.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.service.CommentService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "${front.url}")
public class CommentController {

    @Autowired
    CommentService commentService;

    /**
     * コメントを取得する処理
     * 
     */
    @GetMapping("getcomments")
    public List<CommentForm> getAllComments() {
        return commentService.findAllComment();
    }

    /**
     * コメント追加処理
     * 
     */
    @PostMapping("comment")
    public CommentForm addComment(@RequestBody Map<String, String> requestBody) {
        // 画面から取得したデータを変数に格納
        String userId = requestBody.get("userId");
        String reqReportId = requestBody.get("reportId");
        String commentText = requestBody.get("commentText");
        Integer reportId = Integer.parseInt(reqReportId);

        // CommentFormにデータを設定
        CommentForm reqComment = new CommentForm();
        reqComment.setUserId(userId);
        reqComment.setReportId(reportId);
        reqComment.setCommentText(commentText);

        // コメントをテーブルに追加(追加した情報を取得)
        CommentForm savedComment = commentService.saveComment(reqComment);

        return savedComment;
    }

}
