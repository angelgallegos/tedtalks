package com.iodigital.tedtalks.converters;

import com.iodigital.tedtalks.dto.SpeakerDTO;
import com.iodigital.tedtalks.entity.Speaker;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SpeakerDTOToDBOConverter implements Converter<SpeakerDTO, Speaker> {
    @Override
    public Speaker convert(SpeakerDTO dto) {
        return Speaker.builder()
                .name(dto.getName())
                .build();
    }
}
