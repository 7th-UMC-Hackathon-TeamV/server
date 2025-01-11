package banban.springboot.web.controller;

import banban.springboot.apiPayload.ApiResponse;
import banban.springboot.domain.entity.News;
import banban.springboot.service.NewsService;
import banban.springboot.web.dto.request.NewsRequestDTO;
import banban.springboot.web.dto.response.NewsResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "뉴스")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api")
public class NewsController {

    private final NewsService newsService;

    @Operation(summary = "뉴스 생성")
    @PostMapping("/{groupId}/users/news/{memberId}")
    public ApiResponse<NewsResponseDTO.NewsCreateResponseDTO> createNews(@PathVariable Long groupId, @PathVariable Long memberId, @Valid @RequestBody NewsRequestDTO newsRequestDTO) {
        NewsResponseDTO.NewsCreateResponseDTO news = newsService.createNews(groupId, memberId, newsRequestDTO);
        return ApiResponse.onSuccess(news);
    }
    // 뉴스 공감 누르기
    @PostMapping("/news/{newsId}/likes")
    @Operation(summary = "공감 누르기 API",description = "뉴스에 공감 누르는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "newsId", description = "뉴스글의 ID, path variable 입니다!")
    })
    public ApiResponse<Void> updatePromotionProject(@PathVariable(name = "newsId") Long newsId){

        newsService.addNewsLike(newsId);

        return ApiResponse.onSuccess(null);
    }
}
