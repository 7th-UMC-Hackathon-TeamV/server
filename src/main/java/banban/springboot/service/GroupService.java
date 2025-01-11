package banban.springboot.service;

import banban.springboot.domain.entity.TeamGroup;
import banban.springboot.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    // 그룹코드 생성
    public TeamGroup createGroupCode() {
        String groupCode = generateUniqueGroupCode();
        TeamGroup teamGroup = new TeamGroup();
        teamGroup.setGroupKey(groupCode);

        return groupRepository.save(teamGroup);
    }

    // 중복되지 않는 6글자 그룹코드 생성
    private String generateUniqueGroupCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder code;

        do {
            code = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                int index = random.nextInt(characters.length());
                code.append(characters.charAt(index));
            }
        } while (groupRepository.existsByGroupKey(code.toString()));

        return code.toString();
    }
}
