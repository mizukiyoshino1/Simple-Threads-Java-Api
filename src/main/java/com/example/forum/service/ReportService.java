package com.example.forum.service;

import com.example.forum.controller.form.ReportForm;
import com.example.forum.repository.CommentRepository;
import com.example.forum.repository.ImageRepository;
import com.example.forum.repository.LikeRepository;
import com.example.forum.repository.ReportRepository;
import com.example.forum.repository.entity.Image;
import com.example.forum.repository.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    ImageRepository imageRepository;

    /*
     * レコード全件取得処理
     */
    public List<ReportForm> findAllReport(String userId) {
        List<Object[]> results = reportRepository.findAllReportsWithUserInfo(userId);
        return setReportForm(results);
    }

    /**
     * ユーザのレポートを取得する処理
     * 
     */
    public List<ReportForm> findUserReports(String userId) {
        List<Object[]> results = reportRepository.findByUserId(userId);
        return setReportForm(results);
    }

    /**
     * いいね対象のレポートを取得する処理
     * 
     */
    public Report findReport(Integer reportId) {
        Optional<Report> report = reportRepository.findById(reportId);
        return report.orElse(null);
    }

    /**
     * 検索用語を用いてレポートを検索する処理
     * 
     */
    public List<ReportForm> searchReportsByTerm(String userId, String searchTerm) {
        List<Object[]> results = reportRepository.findByContentContaining(userId, searchTerm);
        List<ReportForm> reports = setReportForm(results);
        return reports;
    }

    /*
     * レコード追加
     */
    public void saveReport(ReportForm reqReport) {
        Report saveReport = setReportEntity(reqReport);
        saveReport = reportRepository.save(saveReport);

        // 画像データを保存
        if (reqReport.getImageUrls() != null) {
            for (String imageUrl : reqReport.getImageUrls()) {
                Image image = new Image();
                image.setImageUrl(imageUrl);
                // 保存したレポートのIDを設定
                image.setReportId(saveReport.getId());
                // DBに保存
                imageRepository.save(image);
            }
        }
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
        // コメントの削除
        commentRepository.deleteByReportId(id);

        // 画像の削除
        imageRepository.deleteByReportId(id);

        // いいね情報の削除
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

    /**
     * 投稿に紐ずく画像取得取得
     * 
     * @param reportId
     * @return
     */
    public List<String> findImagesByReportId(Integer reportId) {
        return imageRepository.findByReportId(reportId).stream()
                .map(Image::getImageUrl)
                .collect(Collectors.toList());
    }

    /**
     * DBから取得したデータをFormに格納&imagesテーブルから紐づく情報を取得する処理
     * 
     * @param results
     * @return
     */
    private List<ReportForm> setReportForm(List<Object[]> results) {
        List<ReportForm> reports = new ArrayList<>();
        for (Object[] result : results) {
            Integer reportId = (Integer) result[0];
            List<String> imageUrls = findImagesByReportId(reportId); // 画像URLを取得

            ReportForm report = new ReportForm();
            report.setId(reportId);
            report.setContent((String) result[1]);
            report.setUserId((String) result[2]);
            report.setUserName((String) result[3]);
            report.setProfileImageUrl((String) result[4]);
            report.setLikeFlg((Integer) result[5]);
            report.setLikeCount((Integer) result[6]);
            report.setImageUrls(imageUrls);
            reports.add(report);
        }
        return reports;
    }
}
