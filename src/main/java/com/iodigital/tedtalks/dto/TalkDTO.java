package com.iodigital.tedtalks.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iodigital.tedtalks.config.validation.groups.OnCreate;
import com.iodigital.tedtalks.serializer.CustomInstantDeserializer;
import com.iodigital.tedtalks.serializer.CustomInstantSerializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class TalkDTO extends BaseDTO<TalkDTO> {
    @NotBlank(message = "Title cannot be empty", groups = OnCreate.class)
    private String title;

    @JsonSerialize(using = CustomInstantSerializer.class)
    @JsonDeserialize(using = CustomInstantDeserializer.class)
    private Instant date;

    private Long views;

    private Long likes;

    @NotBlank(message = "Link cannot be empty", groups = OnCreate.class)
    @URL
    private String link;

    @NotNull(message = "Speaker cannot be null", groups = OnCreate.class)
    private SpeakerDTO speaker;
}
