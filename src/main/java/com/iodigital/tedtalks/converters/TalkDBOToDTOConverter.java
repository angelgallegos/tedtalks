package com.iodigital.tedtalks.converters;
import com.iodigital.tedtalks.dto.SpeakerDTO;
import com.iodigital.tedtalks.dto.TalkDTO;
import com.iodigital.tedtalks.entity.Speaker;
import com.iodigital.tedtalks.entity.Talk;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TalkDBOToDTOConverter implements Converter<Talk, TalkDTO> {

    private final Converter<Speaker, SpeakerDTO> converter;

    @Override
    public TalkDTO convert(Talk dbo) {
        TalkDTO dto = new TalkDTO();
        dto.setId(dbo.getId());
        dto.setDate(dbo.getDate());
        dto.setLikes(dbo.getLikes());
        dto.setTitle(dbo.getTitle());
        dto.setLink(dbo.getLink());
        dto.setViews(dbo.getViews());
        dto.setSpeaker(converter.convert(dbo.getSpeaker()));

        return dto;
    }
}
