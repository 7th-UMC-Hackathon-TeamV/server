package banban.springboot.web.controller;

import banban.springboot.apiPayload.ApiResponse;
import banban.springboot.service.GroupService;
import banban.springboot.service.MemberService;
import banban.springboot.web.dto.request.MemberRequestDTO;
import banban.springboot.web.dto.response.MemberResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    /* 사용자 생성 API*/
    @PostMapping("{groupKey}/users/login")
    public ApiResponse<MemberResponseDTO> createUser(@RequestBody @Valid MemberRequestDTO memberRequestDTO,@PathVariable("groupKey") String groupKey) {
        MemberResponseDTO MemberResponseDTO = memberService.createUser(memberRequestDTO,groupKey);
        return ApiResponse.onSuccess(MemberResponseDTO);}

    /* ID로 사용자 조회 API */
    @GetMapping("{groupKey}/users/login/{memberId}")
    public ApiResponse<MemberResponseDTO> getUserById(@PathVariable Long memberId,@PathVariable("groupKey") String groupKey) {
        MemberResponseDTO MemberResponseDTO = memberService.findMemberById(memberId,groupKey);
        return ApiResponse.onSuccess(MemberResponseDTO);}

    /**
     Username으로 사용자 조회 API
     */
    @GetMapping("{groupKey}/users/login/username")
    public ApiResponse<MemberResponseDTO> getUserByUsername(@RequestParam String username,@PathVariable("groupKey") String groupKey) {
        MemberResponseDTO MemberResponseDTO = memberService.getMemberByUsername(username,groupKey);
        return ApiResponse.onSuccess(MemberResponseDTO);}
}