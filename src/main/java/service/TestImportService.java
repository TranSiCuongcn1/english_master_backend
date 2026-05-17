package service;

import com.opencsv.CSVReader;
import dto.request.CreateTestRequest;
import dto.request.CreateTestRequest.CreateQuestionRequest;
import dto.response.TestResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestImportService {

    private final TestService testService;

    @Transactional
    public TestResponse importTestWithQuestions(CreateTestRequest request, MultipartFile file) {
        List<CreateQuestionRequest> questions = new ArrayList<>();
        String filename = file.getOriginalFilename();

        try {
            if (filename != null && filename.endsWith(".csv")) {
                questions = parseCsv(file);
            } else if (filename != null && filename.endsWith(".xlsx")) {
                questions = parseExcel(file);
            } else {
                throw new RuntimeException("Unsupported file format. Please upload .xlsx or .csv");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error parsing file: " + e.getMessage(), e);
        }

        request.setQuestions(questions);
        
        // Cập nhật lại số lượng câu hỏi dựa vào thực tế file
        request.setTotalQuestions(questions.size());

        // Gọi TestService để tạo và lưu vào DB
        return testService.create(request);
    }

    private List<CreateQuestionRequest> parseCsv(MultipartFile file) throws Exception {
        List<CreateQuestionRequest> questions = new ArrayList<>();
        try (Reader reader = new InputStreamReader(file.getInputStream());
             CSVReader csvReader = new CSVReader(reader)) {

            List<String[]> records = csvReader.readAll();
            
            // Bỏ qua dòng tiêu đề (header)
            for (int i = 1; i < records.size(); i++) {
                String[] row = records.get(i);
                if (row == null || row.length < 4) continue; // Skip empty rows

                CreateQuestionRequest qr = mapRowToQuestionRequest(row);
                questions.add(qr);
            }
        }
        return questions;
    }

    private List<CreateQuestionRequest> parseExcel(MultipartFile file) throws Exception {
        List<CreateQuestionRequest> questions = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            // Bỏ qua dòng tiêu đề (index 0)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String[] rowData = new String[13];
                boolean isEmptyRow = true;
                for (int j = 0; j < 13; j++) {
                    Cell cell = row.getCell(j, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    if (cell != null) {
                        cell.setCellType(CellType.STRING);
                        rowData[j] = cell.getStringCellValue();
                        isEmptyRow = false;
                    } else {
                        rowData[j] = "";
                    }
                }
                
                if (isEmptyRow) continue;

                CreateQuestionRequest qr = mapRowToQuestionRequest(rowData);
                questions.add(qr);
            }
        }
        return questions;
    }

    private CreateQuestionRequest mapRowToQuestionRequest(String[] row) {
        CreateQuestionRequest qr = new CreateQuestionRequest();
        
        // 0: Order Index (Optional)
        // 1: Part Number
        if (row.length > 1 && !row[1].trim().isEmpty()) {
            try {
                qr.setPart(Integer.parseInt(row[1].trim()));
            } catch (NumberFormatException ignored) {}
        }
        
        // 2: Skill
        if (row.length > 2 && !row[2].trim().isEmpty()) {
            qr.setSkill(row[2].trim());
        }

        // 3: Question Text
        if (row.length > 3 && !row[3].trim().isEmpty()) {
            qr.setText(row[3].trim());
        }

        // 4, 5, 6, 7: Options
        List<String> options = new ArrayList<>();
        if (row.length > 4 && !row[4].trim().isEmpty()) options.add(row[4].trim());
        if (row.length > 5 && !row[5].trim().isEmpty()) options.add(row[5].trim());
        if (row.length > 6 && !row[6].trim().isEmpty()) options.add(row[6].trim());
        if (row.length > 7 && !row[7].trim().isEmpty()) options.add(row[7].trim());
        qr.setOptions(options);

        // Mặc định type là multiple-choice nếu có options
        if (!options.isEmpty()) {
            qr.setType("multiple-choice");
        } else {
            qr.setType("text");
        }

        // 8: Correct Answer
        if (row.length > 8 && !row[8].trim().isEmpty()) {
            qr.setCorrectAnswer(row[8].trim());
        }

        // 9: Explanation
        if (row.length > 9 && !row[9].trim().isEmpty()) {
            qr.setExplanation(row[9].trim());
        }

        // 10: Audio URL
        if (row.length > 10 && !row[10].trim().isEmpty()) {
            qr.setAudioUrl(row[10].trim());
        }

        // 11: Image URL
        if (row.length > 11 && !row[11].trim().isEmpty()) {
            qr.setImageUrl(row[11].trim());
        }

        // 12: Passage Text
        if (row.length > 12 && !row[12].trim().isEmpty()) {
            qr.setPassageText(row[12].trim());
        }

        return qr;
    }
}
