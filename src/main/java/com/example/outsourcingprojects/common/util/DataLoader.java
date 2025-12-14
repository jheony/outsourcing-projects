//package com.example.outsourcingprojects.common.util;
//
//import com.example.outsourcingprojects.common.entity.Task;
//import com.example.outsourcingprojects.domain.task.repository.TaskRepository;
//import com.example.outsourcingprojects.domain.user.repository.UserRepository;
//import com.opencsv.CSVReader;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.nio.charset.StandardCharsets;
//import java.time.OffsetDateTime;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class DataLoader implements CommandLineRunner {
//
//    private final TaskRepository taskRepository;
//    private final UserRepository userRepository;
//
//    // 문자열을 LocalDateTime으로 변환 (null 안전 처리)
//    private LocalDateTime parseDateTime(String value) {
//        if (value == null || value.isBlank()) {
//            return null;
//        }
//        return OffsetDateTime.parse(value).toLocalDateTime();
//    }
//
//    // 문자열을 Long으로 변환, 실패 시 기본값 사용
//    private Long parseLongSafe(String value, Long defaultValue) {
//        try {
//            return Long.parseLong(value);
//        } catch (NumberFormatException e) {
//            return defaultValue;
//        }
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        if (taskRepository.count() >= 100) {
//            System.out.println("Tasks already exceed 100. CSV loading skipped.");
//            return;
//        }
//
//        System.out.println("Loading tasks from CSV 1000 times...");
//
//        for (int i = 0; i < 1000; i++) {
//            try (
//                    InputStream is = getClass().getResourceAsStream("/data/tasks.csv");
//                    InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
//                    CSVReader csvReader = new CSVReader(reader)
//            ) {
//                List<String[]> rows = csvReader.readAll();
//                if (rows.isEmpty()) continue;
//
//                // 첫 행 헤더에서 BOM 제거
//                String[] header = rows.get(0);
//                if (header[0] != null) {
//                    header[0] = header[0].replace("\uFEFF", "");
//                }
//
//                rows.remove(0); // 헤더 제거
//                List<Task> tasks = new ArrayList<>();
//
//                for (String[] row : rows) {
//                    if (row.length < 8) continue; // 최소 컬럼 체크
//
//                    Task task = new Task(
//                            row[2], // title
//                            row[4], // description
//                            parseLongSafe(row[5], 10L), // priority 기본값 10
//                            parseLongSafe(row[6], 10L), // status 기본값 10
//                            userRepository.findById(parseLongSafe(row[1], 1L)).orElse(null), // assignee
//                            parseDateTime(row[7]) // dueDate
//                    );
//
//                    tasks.add(task);
//                }
//
//                taskRepository.saveAll(tasks);
//            }
//
//            if ((i + 1) % 100 == 0) {
//                System.out.println((i + 1) + " times CSV imported.");
//            }
//        }
//
//        System.out.println("CSV import completed 1000 times.");
//    }
//}
