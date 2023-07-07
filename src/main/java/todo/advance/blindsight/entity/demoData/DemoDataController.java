package todo.advance.blindsight.entity.demoData;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v0/datas")
public class DemoDataController {
    private final DemoDataService demoDataService;

    @PostMapping("/insertDatas")
    public ResponseEntity<String> insertDemoData() {
        try {
            demoDataService.insertDemoData();
            return ResponseEntity.ok("Demo data inserted successfully");
        } catch (Exception e) {
            log.error("Failed to insert demo data: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to insert demo data");
        }
    }
}
