package com.example.forum.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.repository.CommentRepository;
import com.example.forum.repository.entity.Comment;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

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
    public void saveComment(CommentForm reqComment) {
        Comment saveComment = setCommentEntity(reqComment);
        commentRepository.save(saveComment);
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
