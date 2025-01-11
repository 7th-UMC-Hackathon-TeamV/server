package banban.springboot.web.controller;

import banban.springboot.apiPayload.ApiResponse;
import banban.springboot.domain.entity.TeamGroup;
import banban.springboot.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api")
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/random-group")
    @Operation(summary = "그룹 코드 생성 API",description = "그룹 코드 생성하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<String> updatePromotionProject(){

        TeamGroup newGroup = groupService.createGroupCode();

        return ApiResponse.onSuccess(newGroup.getGroupKey());
    }
}
