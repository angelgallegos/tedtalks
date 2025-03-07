package com.iodigital.tedtalks.repositories;

import com.iodigital.tedtalks.entity.Speaker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomSpeakerRepository {
    Page<Speaker> listOrderedByAvgRating(Pageable pageable);
    Page<Speaker> listOrderedByWilsonRating(Pageable pageable);
}
