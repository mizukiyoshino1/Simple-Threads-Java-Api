package com.example.forum.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.forum.controller.form.LikeNotificationForm;
import com.example.forum.repository.entity.Report;
import com.example.forum.service.LikeService;
import com.example.forum.service.ReportService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "${front.url}")
public class LikeController {

    @Autowired
    LikeService likeService;

    @Autowired
    ReportService reportService;

    /**
     * いいね通知取得処理
     * 
     * @return likeNotification いいね通知情報を返却する
     */
    @PostMapping("notifications")
    public List<LikeNotificationForm> getAllLikes(@RequestBody Map<String, String> requestBody) {
        String userId = requestBody.get("userId");
        List<LikeNotificationForm> likeNotification = likeService.findAllLikes(userId);
        return likeNotification;
    }

    /**
     * いいねの追加・取り消し処理
     * 
     * @param requestBody
     */
    @PostMapping("like")
    public Report likePost(@RequestBody Map<String, String> requestBody) {
        String userId = requestBody.get("userId");
        String reqReportId = requestBody.get("reportId");

        // Integer型に変換
        Integer reportId = Integer.parseInt(reqReportId);

        // いいね済みか確認する(DBに存在しているかどうか)
        Boolean likeFlg = likeService.findLike(userId, reportId);

        // いいねした対象のレポートテーブルを取得する
        Report report = reportService.findReport(reportId);

        // いいね済みでなかった場合
        if (!likeFlg) {
            // レポートテーブルのいいねカウント数を+1する
            reportService.addLikeCount(report);

            // いいね済みを登録する
            likeService.likeSave(userId, reportId);

            // いいねフラグを取得

            // 返却値(※カウント数が更新済み)
            return report;
        } else {
            // レポートテーブルのいいねカウント数を-1する
            reportService.decrementLikeCount(report);

            // いいね済みを削除する
            likeService.deleteLike(userId, reportId);

            // いいねフラグを取得

            // 返却値(※カウント数が更新済み)
            return report;
        }
    }

}
