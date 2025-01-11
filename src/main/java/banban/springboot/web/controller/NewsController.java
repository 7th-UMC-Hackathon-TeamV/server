package banban.springboot.web.controller;

import banban.springboot.apiPayload.ApiResponse;
import banban.springboot.service.NewsService;
import banban.springboot.web.dto.request.NewsRequestDTO;
import banban.springboot.web.dto.response.NewsResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "뉴스")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NewsController {

    private final NewsService newsService;

    @Operation(summary = "뉴스 생성")
    @PostMapping("/{groupId}/users/news/{memberId}")
    public ApiResponse<NewsResponseDTO.NewsCreateResponseDTO> createNews(@PathVariable Long groupId, @PathVariable Long memberId, @RequestBody NewsRequestDTO newsRequestDTO) {
        NewsResponseDTO.NewsCreateResponseDTO news = newsService.createNews(groupId, memberId, newsRequestDTO);
        return ApiResponse.onSuccess(news);
    }
}
