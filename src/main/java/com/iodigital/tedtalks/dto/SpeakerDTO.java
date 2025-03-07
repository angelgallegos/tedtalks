package com.iodigital.tedtalks.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class SpeakerDTO extends BaseDTO<SpeakerDTO> {
    @NotBlank(message = "Name cannot be empty")
    private String name;
}
