package com.iodigital.tedtalks.converters;

import com.iodigital.tedtalks.dto.SpeakerDTO;
import com.iodigital.tedtalks.entity.Speaker;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SpeakerDBOToDTOConverter implements Converter<Speaker, SpeakerDTO> {
    @Override
    public SpeakerDTO convert(Speaker dbo) {
        SpeakerDTO dto = new SpeakerDTO();
        dto.setName(dbo.getName());
        dto.setId(dbo.getId());

        return dto;
    }
}
