package com.iodigital.tedtalks.batch.talks.processors;

import com.iodigital.tedtalks.dto.SpeakerDTO;
import com.iodigital.tedtalks.entity.Speaker;
import com.iodigital.tedtalks.repositories.SpeakerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SpeakerItemProcessor implements ItemProcessor<SpeakerDTO, Speaker> {

    private final SpeakerRepository speakerRepository;

    @Override
    public Speaker process(SpeakerDTO dto) throws Exception {
        Optional<Speaker> speaker = speakerRepository.findByName(dto.getName());
        if(speaker.isPresent()) {
            return null;
        }
        return Speaker.builder()
                .name(dto.getName())
                .build();
    }
}
