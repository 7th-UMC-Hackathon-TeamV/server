package banban.springboot.service;

import banban.springboot.domain.entity.Member;
import banban.springboot.repository.MemberRepository;
import banban.springboot.web.dto.request.MemberRequestDTO;
import banban.springboot.web.dto.response.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberResponseDTO createUser(MemberRequestDTO memberRequestDTO) {
        //DTO to Entity
        Member member = Member.builder()
                .username(memberRequestDTO.getUsername())
                .password(memberRequestDTO.getPassword())
                .build();

        //saved to repository
        Member savedMember = memberRepository.save(member);

        // Entity to ResponseDTO
        return MemberResponseDTO.builder()
                .id(savedMember.getId())
                .username(savedMember.getUsername())
                .build();
    }

    //ID로 사용자 조회
    public MemberResponseDTO findMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자 조회를 할 수 없습니다."));

        return MemberResponseDTO.builder()
                .id(member.getId())
                .username(member.getUsername())
                .build();
    }

    //Username으로 사용자 조회
    public MemberResponseDTO getMemberByUsername(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자 조회를 할 수 없습니다."));

        return MemberResponseDTO.builder()
                .id(member.getId())
                .username(member.getUsername())
                .build();
    }
}