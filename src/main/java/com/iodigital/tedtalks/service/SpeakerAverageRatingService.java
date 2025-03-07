package com.iodigital.tedtalks.service;

import com.iodigital.tedtalks.dto.SpeakerDTO;
import com.iodigital.tedtalks.entity.Speaker;
import com.iodigital.tedtalks.repositories.SpeakerRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SpeakerAverageRatingService extends SpeakerService {
    public SpeakerAverageRatingService(
        SpeakerRepository speakerRepository,
        Converter<SpeakerDTO, Speaker> dtodboConverter,
        Converter<Speaker, SpeakerDTO> dbodtoConverter
    ) {
        super(speakerRepository, dtodboConverter, dbodtoConverter);
    }

    public Page<SpeakerDTO> listOrderByRating(Pageable pageable) {
        return speakerRepository.listOrderedByAvgRating(pageable).map(dbodtoConverter::convert);
    }
}
