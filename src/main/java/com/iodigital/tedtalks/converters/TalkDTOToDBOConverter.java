package com.iodigital.tedtalks.converters;
import com.iodigital.tedtalks.dto.TalkDTO;
import com.iodigital.tedtalks.entity.Speaker;
import com.iodigital.tedtalks.entity.Talk;
import com.iodigital.tedtalks.service.SpeakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TalkDTOToDBOConverter implements Converter<TalkDTO, Talk> {

    public final SpeakerService speakerService;

    @Override
    public Talk convert(TalkDTO dto) {
        Speaker speaker = speakerService.getDBO(dto.getSpeaker().getId());

        return Talk.builder()
                .date(dto.getDate())
                .views(dto.getViews())
                .likes(dto.getLikes())
                .link(dto.getLink())
                .title(dto.getTitle())
                .speaker(speaker)
                .build();
    }
}
