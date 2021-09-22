package com.fastcampus.progmming.dmaker.service;

import com.fastcampus.progmming.dmaker.dto.DeveloperDTO;
import com.fastcampus.progmming.dmaker.dto.DeveloperDetailDTO;
import com.fastcampus.progmming.dmaker.entity.CreateDeveloper;
import com.fastcampus.progmming.dmaker.entity.Developer;
import com.fastcampus.progmming.dmaker.entity.EditDeveloper;
import com.fastcampus.progmming.dmaker.entity.RetiredDeveloper;
import com.fastcampus.progmming.dmaker.exception.DMakerException;
import com.fastcampus.progmming.dmaker.repository.DeveloperRepository;
import com.fastcampus.progmming.dmaker.repository.RetiredDeveloperRepository;
import com.fastcampus.progmming.dmaker.type.DeveloperLevel;
import com.fastcampus.progmming.dmaker.type.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.fastcampus.progmming.dmaker.exception.DMakerErrorCode.*;

@RequiredArgsConstructor
@Service
public class DMakerService {
    private final DeveloperRepository developerRepository;
    private final RetiredDeveloperRepository retiredDeveloperRepository;

    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request) {
        validateCreateDeveloperRequest(request);

        Developer developer = Developer.builder()
                .developerLevel(request.getDeveloperLevel())
                .developerSkillType(request.getDeveloperSkillType())
                .experienceYears(request.getExperienceYears())
                .memberId(request.getMemberId())
                .name(request.getName())
                .age(request.getAge())
                .statusCode(StatusCode.EMPLOYED)
                .build();

        developerRepository.save(developer);

        return CreateDeveloper.Response.fromEntity(developer);
    }

    private void validateCreateDeveloperRequest(CreateDeveloper.Request request) {
        validateDeveloperLevel(request.getDeveloperLevel(), request.getExperienceYears());

        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent((developer -> { throw new DMakerException(DUPLICATED_MEMBER_ID); }));
    }

    public List<DeveloperDTO> getAllDevelopers() {
        return developerRepository.findDevelopersByStatusCodeEquals(StatusCode.EMPLOYED)
                .stream()
                .map(DeveloperDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public DeveloperDetailDTO editDeveloper(String memberId, EditDeveloper.Request request) {
        validateEditDeveloperRequest(request);

        Developer developer = developerRepository.findByMemberId(memberId).orElseThrow(() -> new DMakerException(NO_DEVELOPER));
        developer.setDeveloperLevel(request.getDeveloperLevel());
        developer.setDeveloperSkillType(request.getDeveloperSkillType());
        developer.setExperienceYears(request.getExperienceYears());

        return DeveloperDetailDTO.fromEntity(developer);
    }

    private void validateEditDeveloperRequest(EditDeveloper.Request request) {
        validateDeveloperLevel(request.getDeveloperLevel(), request.getExperienceYears());
    }

    public DeveloperDetailDTO getDeveloperDetail(String memberId) {
        return developerRepository.findByMemberId(memberId)
                .map(DeveloperDetailDTO::fromEntity)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
    }


    private void validateDeveloperLevel(DeveloperLevel developerLevel, Integer experienceYears) {
        if (developerLevel == DeveloperLevel.SENIOR &&
                experienceYears < 10) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        if (developerLevel == DeveloperLevel.JUNGNIOR &&
                (experienceYears < 4 || experienceYears > 10)) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        if (developerLevel == DeveloperLevel.JUNIOR && experienceYears > 4) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
    }

    @Transactional
    public DeveloperDetailDTO deleteDeveloper(String memberId) {
        Developer developer = developerRepository.findByMemberId(memberId).orElseThrow(() -> new DMakerException(NO_DEVELOPER));
        developer.setStatusCode(StatusCode.RETIRED);
        retiredDeveloperRepository.save(new RetiredDeveloper(developer));
        return DeveloperDetailDTO.fromEntity(developer);
    }
}
