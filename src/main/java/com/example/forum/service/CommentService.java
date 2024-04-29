package com.example.forum.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.repository.CommentRepository;
import com.example.forum.repository.UserRepository;
import com.example.forum.repository.entity.Comment;
import com.example.forum.repository.entity.User;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * コメント取得
     * 
     * @param reqComment
     */
    public List<CommentForm> findAllComment() {
        List<Object[]> results = commentRepository.findCommentsWithUserInfo();
        List<CommentForm> comments = setCommentForm(results);
        return comments;
    }

    /*
     * レコード追加
     */
    public CommentForm saveComment(CommentForm reqComment) {
        Comment saveComment = setCommentEntity(reqComment);
        Comment saved = commentRepository.save(saveComment);

        // ユーザ情報を取得
        Optional<User> userOptiional = userRepository.findByUserId(saved.getUserId());
        User user = userOptiional.get();
        return setCommentForm(saved, user);
    }

    /*
     * DBから取得したデータをFormに設定
     * 
     */
    private List<CommentForm> setCommentForm(List<Object[]> results) {
        List<CommentForm> comments = new ArrayList<>();

        for (Object[] result : results) {
            CommentForm comment = new CommentForm();
            comment.setId((Integer) result[0]);
            comment.setUserId((String) result[1]);
            comment.setReportId((Integer) result[2]);
            comment.setCommentText((String) result[3]);
            comment.setUserName((String) result[4]);
            comment.setProfileImageUrl((String) result[5]);

            comments.add(comment);
        }
        return comments;
    }

    /**
     * DBから取得したデータをFormに設定
     * 
     */
    private CommentForm setCommentForm(Comment saved, User user) {
        CommentForm savedComment = new CommentForm();
        savedComment.setId(saved.getId());
        savedComment.setUserId(saved.getUserId());
        savedComment.setReportId(saved.getReportId());
        savedComment.setCommentText(saved.getCommentText());
        savedComment.setUserName(user.getUserName());
        savedComment.setProfileImageUrl(user.getProfileImageUrl());
        return savedComment;
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Comment setCommentEntity(CommentForm reqComment) {
        Comment comment = new Comment();
        comment.setId(reqComment.getId());
        comment.setUserId(reqComment.getUserId());
        comment.setReportId(reqComment.getReportId());
        comment.setCommentText(reqComment.getCommentText());
        return comment;
    }
}
