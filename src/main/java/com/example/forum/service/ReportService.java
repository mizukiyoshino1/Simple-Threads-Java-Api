package com.example.forum.service;

import com.example.forum.controller.form.ReportForm;
import com.example.forum.repository.LikeRepository;
import com.example.forum.repository.ReportRepository;
import com.example.forum.repository.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;

    @Autowired
    LikeRepository likeRepository;

    /*
     * レコード全件取得処理
     */
    public List<ReportForm> findAllReport(String userId) {
        List<Object[]> results = reportRepository.findAllReportsWithUserInfo(userId);
        List<ReportForm> reports = setReportForm(results);
        return reports;
    }

    /**
     * ユーザのレポートを取得する処理
     * 
     */
    public List<ReportForm> findUserReports(String userId) {
        List<Object[]> results = reportRepository.findByUserId(userId);
        List<ReportForm> reports = setReportForm(results);
        return reports;
    }

    /**
     * いいね対象のレポートを取得する処理
     * 
     */
    public Report findReport(Integer reportId) {
        Optional<Report> report = reportRepository.findById(reportId);
        return report.orElse(null);
    }

    /*
     * DBから取得したデータをFormに設定
     */
    private List<ReportForm> setReportForm(List<Object[]> results) {
        List<ReportForm> reports = new ArrayList<>();

        for (Object[] result : results) {
            ReportForm report = new ReportForm();
            report.setId((Integer) result[0]);
            report.setContent((String) result[1]);
            report.setUserId((String) result[2]);
            report.setUserName((String) result[3]);
            // ファイルパスからBase64エンコードされた画像データを取得
            String filePath = (String) result[4]; // 仮のファイルパス取得方法
            byte[] imageDataBytes = null;
            if (filePath != null) {
                try {
                    File file = new File(filePath);
                    imageDataBytes = Files.readAllBytes(file.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                    // エラー処理
                }

                // Base64エンコードされた画像データを文字列に変換して設定
                if (imageDataBytes != null) {
                    String imageDataString = Base64.getEncoder().encodeToString(imageDataBytes);
                    report.setProfileImageUrl("data:image/png;base64," + imageDataString);
                } else {
                    // エラー時の処理
                    report.setProfileImageUrl(""); // エラー時は空文字列を設定するなどの処理を行う
                }
            } else {
                report.setProfileImageUrl("");
            }
            report.setLikeFlg((Integer) result[5]);
            report.setLikeCount((Integer) result[6]);
            reports.add(report);
        }
        return reports;
    }

    /*
     * レコード追加
     */
    public void saveReport(ReportForm reqReport) {
        Report saveReport = setReportEntity(reqReport);
        reportRepository.save(saveReport);
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Report setReportEntity(ReportForm reqReport) {
        Report report = new Report();
        report.setId(reqReport.getId());
        report.setContent(reqReport.getContent());
        report.setUserId(reqReport.getUserId());
        return report;
    }

    /**
     * レコード削除
     * 
     */
    @Transactional
    public void deleteReport(int id) {
        // 関連するlikeテーブルのレコードを削除
        likeRepository.deleteByReportId(id);

        // レポートの削除
        reportRepository.deleteById(id);
    }

    /**
     * レポートに対していいね数を+1する
     * 
     */
    public void addLikeCount(Report report) {
        report.setLikesCount(report.getLikesCount() + 1);
        reportRepository.save(report);
    }

    /**
     * レポートに対していいね数を-1する
     * 
     */
    public void decrementLikeCount(Report report) {
        report.setLikesCount(report.getLikesCount() - 1);
        reportRepository.save(report);
    }

}
