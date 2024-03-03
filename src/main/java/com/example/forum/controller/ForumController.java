package com.example.forum.controller;

import com.example.forum.controller.form.ReportForm;
import com.example.forum.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ForumController {

    @Autowired
    ReportService reportService;

    /*
     * 投稿内容表示処理
     * 
     */
    @GetMapping("/contents")
    public List<ReportForm> getAllContents() {
        return reportService.findAllReport();
    }

    /*
     * 新規投稿処理
     * 
     */
    @PostMapping("/add")
    public void addContent(@RequestBody ReportForm reportForm) {
        reportService.saveReport(reportForm);
    }

    /*
     * 投稿の削除処理
     * 
     */
    @DeleteMapping("/delete/{id}")
    public void deleteContent(@PathVariable int id) {
        reportService.deleteReport(id);
    }

}
