package banban.springboot.web.controller;

import banban.springboot.apiPayload.ApiResponse;
import banban.springboot.service.MemberService;
import banban.springboot.web.dto.request.MemberRequestDTO;
import banban.springboot.web.dto.response.MemberResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 사용자 생성 API
     */
    @PostMapping("/users/login")
    public ApiResponse<MemberResponseDTO> createUser(@RequestBody @Valid MemberRequestDTO memberRequestDTO) {
        MemberResponseDTO MemberResponseDTO = memberService.createUser(memberRequestDTO);
        return ApiResponse.onSuccess(MemberResponseDTO);
    }

    /**
     * ID로 사용자 조회 API
     */
    @GetMapping("/users/login/{memberId}")
    public ApiResponse<MemberResponseDTO> getUserById(@PathVariable Long memberId) {
        MemberResponseDTO MemberResponseDTO = memberService.findMemberById(memberId);
        return ApiResponse.onSuccess(MemberResponseDTO);
    }

    /**
     * Username으로 사용자 조회 API
     */
    @GetMapping("/users/login/username")
    public ApiResponse<MemberResponseDTO> getUserByUsername(@RequestParam String username) {
        MemberResponseDTO MemberResponseDTO = memberService.getMemberByUsername(username);
        return ApiResponse.onSuccess(MemberResponseDTO);
    }
}

