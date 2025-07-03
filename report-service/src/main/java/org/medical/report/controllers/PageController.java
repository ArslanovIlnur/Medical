package org.medical.report.controllers;

import org.medical.report.model.Research;
import org.medical.report.repositories.ResearchRepository;
import org.medical.report.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class PageController {

    @Value("${pdf.directory}")
    private String pdfDirectory;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private ResearchRepository researchRepository;

    @GetMapping("/data")
    public String reportPage(){
        return "data";
    }

    @GetMapping("/report")
    public String reportWithParams(@RequestParam("researchType") String researchType,
                                   @RequestParam("startDate") String startDate,
                                   @RequestParam("endDate") String endDate,
                                   Model model) {
        List<Research> researchList = researchRepository.findByParams(
                researchType,
                Date.valueOf(startDate),
                Date.valueOf(endDate)
        );

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        Set<String> zones = new HashSet<>();
        researchList.forEach(research -> zones.add(research.getResearchZone()));

        Map<String, Integer> zoneCount = new HashMap<>();
        zones.forEach(zone -> zoneCount.put(zone, researchRepository.findCountByParams(
                researchType,
                Date.valueOf(startDate),
                Date.valueOf(endDate),
                zone
        )));

        int sum = zoneCount.values().stream().mapToInt(Integer::intValue).sum();

        String formattedStart = df.format(Date.valueOf(startDate));
        String formattedEnd = df.format(Date.valueOf(endDate));

        Map<String, Object> pdfData = new HashMap<>();
        pdfData.put("resType", researchType);
        pdfData.put("startDate", formattedStart);
        pdfData.put("endDate", formattedEnd);
        pdfData.put("dataMap", zoneCount);
        pdfData.put("sum", sum);

        String pdfFileName = "report_" + System.currentTimeMillis() + ".pdf";
        pdfService.generatePdfFile("report-pdf", pdfData, pdfFileName);

        model.addAttribute("resType", researchType);
        model.addAttribute("startDate", formattedStart);
        model.addAttribute("endDate", formattedEnd);
        model.addAttribute("dataMap", zoneCount);
        model.addAttribute("sum", sum);
        model.addAttribute("pdfFileName", pdfFileName);

        return "report";
    }


    @GetMapping("/download")
    public ResponseEntity<Resource> downloadPdf(@RequestParam("file") String fileName) throws IOException {
        Path path = Paths.get(pdfDirectory + fileName);
        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
