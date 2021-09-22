package com.fastcampus.progmming.dmaker.entity;

import com.fastcampus.progmming.dmaker.type.DeveloperLevel;
import com.fastcampus.progmming.dmaker.type.DeveloperSkillType;
import com.fastcampus.progmming.dmaker.type.StatusCode;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@Builder
@AllArgsConstructor @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Developer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Enumerated(EnumType.STRING)
    private DeveloperLevel developerLevel;

    @Enumerated(EnumType.STRING)
    private DeveloperSkillType developerSkillType;

    private Integer experienceYears;
    private String memberId;
    private String name;
    private Integer age;

    @Enumerated(EnumType.STRING)
    private StatusCode statusCode;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
