package org.medical.report.service;

import java.util.Map;

public interface PdfService {
    void generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName);
}
