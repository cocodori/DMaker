package com.fastcampus.progmming.dmaker.controller;

import com.fastcampus.progmming.dmaker.dto.DMakerErrorResponse;
import com.fastcampus.progmming.dmaker.dto.DeveloperDTO;
import com.fastcampus.progmming.dmaker.dto.DeveloperDetailDTO;
import com.fastcampus.progmming.dmaker.entity.CreateDeveloper;
import com.fastcampus.progmming.dmaker.entity.EditDeveloper;
import com.fastcampus.progmming.dmaker.exception.DMakerException;
import com.fastcampus.progmming.dmaker.service.DMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class DMakerController {
    private final DMakerService dMakerService;

    @GetMapping("/developers")
    public List<DeveloperDTO> getAllDevelopers() {
        return dMakerService.getAllDevelopers();
    }

    @GetMapping("/developer/{memberId}")
    public DeveloperDetailDTO getDeveloperDetail(@PathVariable String memberId) {
        return dMakerService.getDeveloperDetail(memberId);

    }

    @PostMapping("/create-developer")
    public CreateDeveloper.Response createDevelopers(
            @Valid @RequestBody CreateDeveloper.Request request
            ) {
        log.info("request : {}", request);

        return dMakerService.createDeveloper(request);
    }

    @PutMapping("/developer/{memberId}")
    public DeveloperDetailDTO editDeveloper(
            @PathVariable String memberId,
            @Valid @RequestBody EditDeveloper.Request request
    ) {
        return dMakerService.editDeveloper(memberId, request);
    }

    @DeleteMapping("/developer/{memberId}")
    public DeveloperDetailDTO deleteDeveloper(
            @PathVariable String memberId) {
        return dMakerService.deleteDeveloper(memberId);
    }
}
