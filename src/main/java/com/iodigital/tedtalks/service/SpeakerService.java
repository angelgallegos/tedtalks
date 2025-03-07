package com.iodigital.tedtalks.service;

import com.iodigital.tedtalks.dto.SpeakerDTO;
import com.iodigital.tedtalks.entity.Speaker;
import com.iodigital.tedtalks.exceptions.TedTalksNotFoundException;
import com.iodigital.tedtalks.repositories.SpeakerRepository;
import com.iodigital.tedtalks.utils.BeanCopyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpeakerService implements ICRUDService<SpeakerDTO>, IRatingService {

    protected final SpeakerRepository speakerRepository;

    protected final Converter<SpeakerDTO, Speaker> dtodboConverter;

    protected final Converter<Speaker, SpeakerDTO> dbodtoConverter;

    public Speaker getDBO(UUID id) {
        return speakerRepository.findById(id).orElseThrow(() -> new TedTalksNotFoundException(Speaker.class.getName(), id));
    }

    public SpeakerDTO create(SpeakerDTO dto) {
        Speaker dbo = speakerRepository.save(Objects.requireNonNull(dtodboConverter.convert(dto)));

        return dbodtoConverter.convert(dbo);
    }

    public SpeakerDTO request(UUID id) {

        return dbodtoConverter.convert(speakerRepository.findById(id).orElseThrow(() -> new TedTalksNotFoundException(Speaker.class.getName(), id)));
    }

    public SpeakerDTO update(UUID id, SpeakerDTO dto) {
        Speaker dbo = speakerRepository.findById(id).orElseThrow(() -> new TedTalksNotFoundException(Speaker.class.getName(), id));
        BeanCopyUtils.myCopyProperties(dto, dbo);
        speakerRepository.save(dbo);
        return dbodtoConverter.convert(dbo);
    }

    public SpeakerDTO delete(UUID id) {
        throw new RuntimeException("Not implemented");
    }

    public Page<SpeakerDTO> findAll(Pageable pageable) {
        return speakerRepository.findAll(pageable).map(dbodtoConverter::convert);
    }

    public Page<SpeakerDTO> filterPaged(String title, Pageable pageable) {
        return speakerRepository.findAllByNameLike(title, pageable).map(dbodtoConverter::convert);
    }

    @Override
    public Page<SpeakerDTO> listOrderByRating(Pageable pageable) {
        return null;
    }
}
