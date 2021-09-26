package com.fastcampus.progmming.dmaker.service;

import com.fastcampus.progmming.dmaker.entity.CreateDeveloper;
import com.fastcampus.progmming.dmaker.entity.Developer;
import com.fastcampus.progmming.dmaker.exception.DMakerErrorCode;
import com.fastcampus.progmming.dmaker.exception.DMakerException;
import com.fastcampus.progmming.dmaker.repository.DeveloperRepository;
import com.fastcampus.progmming.dmaker.type.DeveloperLevel;
import com.fastcampus.progmming.dmaker.type.DeveloperSkillType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DMakerServiceTest {

    @Mock
    private DeveloperRepository developerRepository;

    @InjectMocks
    private DMakerService dMakerService;

    CreateDeveloper.Request defaultCreateRequest = CreateDeveloper.Request.builder()
            .developerLevel(DeveloperLevel.SENIOR)
            .developerSkillType(DeveloperSkillType.FRONT_END)
            .experienceYears(12)
            .name("name")
            .age(12)
            .memberId("id")
            .build();

    @Test
    void createDeveloperTest() {
        //given
        CreateDeveloper.Request request = defaultCreateRequest;

        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.empty());
        ArgumentCaptor<Developer> captor =
                ArgumentCaptor.forClass(Developer.class);

        //when
        dMakerService.createDeveloper(request);

        //then
        verify(developerRepository, times(1)).save(captor.capture());

        Developer savedDeveloper = captor.getValue();
        assertEquals(DeveloperLevel.SENIOR, savedDeveloper.getDeveloperLevel());
        assertEquals(DeveloperSkillType.FRONT_END, savedDeveloper.getDeveloperSkillType());
        assertEquals(12, savedDeveloper.getExperienceYears());
    }

    @Test
    void createDeveloperTest_failed_with_duplicated() {
        //given
        CreateDeveloper.Request request = defaultCreateRequest;

        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(
                        Developer.builder()
                        .developerLevel(DeveloperLevel.SENIOR)
                        .developerSkillType(DeveloperSkillType.BACK_END)
                        .memberId("memberId")
                        .name("kozko")
                        .age(12)
                        .build()
                ));

        //when-then
        DMakerException dMakerException = assertThrows(DMakerException.class, () -> dMakerService.createDeveloper(request));

        assertEquals(DMakerErrorCode.DUPLICATED_MEMBER_ID, dMakerException.getDMakerErrorCode());
    }
}