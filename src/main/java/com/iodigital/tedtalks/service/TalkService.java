package com.iodigital.tedtalks.service;

import com.iodigital.tedtalks.dto.TalkDTO;
import com.iodigital.tedtalks.entity.Talk;
import com.iodigital.tedtalks.exceptions.TedTalksNotFoundException;
import com.iodigital.tedtalks.repositories.TalkRepository;
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
public class TalkService implements ICRUDService<TalkDTO> {
    private final TalkRepository talkRepository;
    private final Converter<TalkDTO, Talk> dtodboConverter;
    private final Converter<Talk, TalkDTO> dbodtoConverter;

    public TalkDTO create(TalkDTO dto) {
        Talk dbo = talkRepository.save(Objects.requireNonNull(dtodboConverter.convert(dto)));

        return dbodtoConverter.convert(dbo);
    }

    public TalkDTO request(UUID id) {

        return dbodtoConverter.convert(talkRepository.findById(id).orElseThrow(() -> new TedTalksNotFoundException(Talk.class.getName(), id)));
    }

    public TalkDTO update(UUID id, TalkDTO dto) {
        Talk dbo = talkRepository.findById(id).orElseThrow(() -> new TedTalksNotFoundException(Talk.class.getName(), id));
        BeanCopyUtils.myCopyProperties(dto, dbo);
        talkRepository.save(dbo);
        return dbodtoConverter.convert(dbo);
    }

    public TalkDTO delete(UUID id) {
        Talk talk = talkRepository.findById(id).orElseThrow(() -> new TedTalksNotFoundException(Talk.class.getName(), id));
        TalkDTO talkDTO = dbodtoConverter.convert(talk);
        talkRepository.delete(talk);

        return talkDTO;
    }

    public Page<TalkDTO> findAll(Pageable pageable) {
        return talkRepository.findAll(pageable).map(dbodtoConverter::convert);
    }

    public Page<TalkDTO> filterPaged(String title, Pageable pageable) {
        return talkRepository.findAllByTitleLike(title, pageable).map(dbodtoConverter::convert);
    }
}
