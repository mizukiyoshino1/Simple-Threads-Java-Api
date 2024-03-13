package com.example.forum.service;

import com.example.forum.controller.form.ReportForm;
import com.example.forum.repository.ReportRepository;
import com.example.forum.repository.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;

    /*
     * レコード全件取得処理
     */
    public List<ReportForm> findAllReport() {
        List<Object[]> results = reportRepository.findAllReportsWithUserInfo();
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
    public void deleteReport(int id) {
        reportRepository.deleteById(id);
    }

}
