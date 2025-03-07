package com.iodigital.tedtalks.service;

import com.iodigital.tedtalks.dto.SpeakerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRatingService {
    Page<SpeakerDTO> listOrderByRating(Pageable pageable);
}
