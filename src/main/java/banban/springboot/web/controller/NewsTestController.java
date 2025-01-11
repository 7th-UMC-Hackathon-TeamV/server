package banban.springboot.web.controller;

import banban.springboot.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "뉴스-테스트")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/test")
public class NewsTestController {

    private static LocalDateTime mockCurrentTime;

    @Operation(summary = "테스트용 현재 시간 설정")
    @PostMapping("/set-time")
    public ApiResponse<String> setTestTime(
            @RequestParam @Parameter(example = "2024-01-02T14:00:00", description = "설정할 시간 (yyyy-MM-dd'T'HH:mm:ss 형식)")
            String dateTime) {
        mockCurrentTime = LocalDateTime.parse(dateTime);
        return ApiResponse.onSuccess("현재 시간이 " + dateTime + "으로 설정되었습니다.");
    }

    @Operation(summary = "현재 설정된 테스트 시간 조회")
    @GetMapping("/current-time")
    public ApiResponse<String> getCurrentTestTime() {
        return ApiResponse.onSuccess(mockCurrentTime.toString());
    }

    public static LocalDateTime getMockCurrentTime() {
        return mockCurrentTime;
    }
}