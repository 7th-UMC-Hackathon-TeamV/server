package banban.springboot.web.controller;

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
    @PostMapping
    public ResponseEntity<MemberResponseDTO> createUser(@RequestBody @Valid MemberRequestDTO memberRequestDTO) {
        MemberResponseDTO MemberResponseDTO = memberService.createUser(memberRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(MemberResponseDTO);
    }

    /**
     * ID로 사용자 조회 API
     */
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDTO> getUserById(@PathVariable Long id) {
        MemberResponseDTO MemberResponseDTO = memberService.findMemberById(id);
        return ResponseEntity.ok(MemberResponseDTO);
    }

    /**
     * Username으로 사용자 조회 API
     */
    @GetMapping("/by-username")
    public ResponseEntity<MemberResponseDTO> getUserByUsername(@RequestParam String username) {
        MemberResponseDTO MemberResponseDTO = memberService.getMemberByUsername(username);
        return ResponseEntity.ok(MemberResponseDTO);
    }
}

