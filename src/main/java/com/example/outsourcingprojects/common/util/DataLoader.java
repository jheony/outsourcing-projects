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
//import java.time.LocalDateTime;
//import java.time.OffsetDateTime;
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
//    private LocalDateTime parseDateTime(String value) {
//        if (value == null || value.isBlank()) {
//            return null;
//        }
//        return OffsetDateTime.parse(value).toLocalDateTime();
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        if (taskRepository.count() >= 100) {
//            System.out.println("Tasks already exceed 100. CSV loading skipped.");
//            return;
//        }
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
//                rows.remove(0); // 헤더 제거
//                List<Task> tasks = new ArrayList<>();
//
//                for (String[] row : rows) {
//                    Task task = new Task(
//                            row[2], // title
//                            row[3], // description
//                            Long.parseLong(row[4]), // priority
//                            Long.parseLong(row[5]), // status
//                            userRepository.findById(Long.parseLong(row[1])).orElse(null), // assignee
//                            parseDateTime(row[6]) // dueDate
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
//
//}
