package com.fastcampus.progmming.dmaker.dto;

import com.fastcampus.progmming.dmaker.exception.DMakerErrorCode;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DMakerErrorResponse {
    private DMakerErrorCode errorCode;
    private String errorMessage;
}
