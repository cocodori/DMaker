package com.fastcampus.progmming.dmaker.dto;

import com.fastcampus.progmming.dmaker.entity.Developer;
import com.fastcampus.progmming.dmaker.type.DeveloperLevel;
import com.fastcampus.progmming.dmaker.type.DeveloperSkillType;
import com.fastcampus.progmming.dmaker.type.StatusCode;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DeveloperDetailDTO {
    private DeveloperLevel developerLevel;
    private DeveloperSkillType developerSkillType;
    private Integer experienceYears;
    private String memberId;
    private String name;
    private Integer age;
    private StatusCode statusCode;

    public static DeveloperDetailDTO fromEntity(Developer developer) {
        return DeveloperDetailDTO.builder()
                .developerLevel(developer.getDeveloperLevel())
                .developerSkillType(developer.getDeveloperSkillType())
                .experienceYears(developer.getExperienceYears())
                .memberId(developer.getMemberId())
                .name(developer.getName())
                .age(developer.getAge())
                .statusCode(developer.getStatusCode())
                .build();
    }
}
