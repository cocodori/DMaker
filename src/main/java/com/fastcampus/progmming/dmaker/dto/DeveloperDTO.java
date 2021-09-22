package com.fastcampus.progmming.dmaker.dto;

import com.fastcampus.progmming.dmaker.entity.Developer;
import com.fastcampus.progmming.dmaker.type.DeveloperLevel;
import com.fastcampus.progmming.dmaker.type.DeveloperSkillType;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DeveloperDTO {
    private DeveloperLevel developerLevel;
    private DeveloperSkillType developerSkillType;
    private String memberId;

    public static DeveloperDTO fromEntity(Developer developer) {
        return DeveloperDTO.builder()
                .developerLevel(developer.getDeveloperLevel())
                .developerSkillType(developer.getDeveloperSkillType())
                .memberId(developer.getMemberId())
                .build();
    }
}
