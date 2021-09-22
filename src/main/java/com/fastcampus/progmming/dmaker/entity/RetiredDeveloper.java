package com.fastcampus.progmming.dmaker.entity;

import com.fastcampus.progmming.dmaker.type.DeveloperLevel;
import com.fastcampus.progmming.dmaker.type.DeveloperSkillType;
import com.fastcampus.progmming.dmaker.type.StatusCode;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class RetiredDeveloper {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Enumerated(EnumType.STRING)
    private DeveloperLevel developerLevel;
    @Enumerated(EnumType.STRING)
    private DeveloperSkillType developerSkillType;
    private Integer experienceYears;
    private String memberId;

    public RetiredDeveloper(Developer developer) {
        this.id = developer.id;
        this.developerLevel = developer.getDeveloperLevel();
        this.developerSkillType = developer.getDeveloperSkillType();
        this.experienceYears = developer.getExperienceYears();
        this.memberId = developer.getMemberId();
    }
}
