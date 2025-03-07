package com.iodigital.tedtalks.batch.talks.processors;

import com.iodigital.tedtalks.entity.Speaker;
import com.iodigital.tedtalks.entity.Talk;
import com.iodigital.tedtalks.dto.TalkDTO;
import com.iodigital.tedtalks.repositories.SpeakerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class TalkItemProcessor implements ItemProcessor<TalkDTO, Talk> {

    private final SpeakerRepository speakerRepository;

    @Override
    public Talk process(TalkDTO dto) throws Exception {

        Talk talk = Talk.builder()
                .title(dto.getTitle())
                .link(dto.getLink())
                .likes(dto.getLikes())
                .views(dto.getViews())
                .date(dto.getDate())
                .build();

        Speaker speaker = speakerRepository.findByName(dto.getSpeaker().getName()).orElse(new Speaker(dto.getSpeaker().getName(), Collections.singleton(talk)));
        talk.setSpeaker(speaker);

        return talk;
    }
}
