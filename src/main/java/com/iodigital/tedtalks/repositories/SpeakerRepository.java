package com.iodigital.tedtalks.repositories;

import com.iodigital.tedtalks.entity.Speaker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpeakerRepository extends JpaRepository<Speaker, UUID>, CustomSpeakerRepository {

    String FILTER_SPEAKER_ON_NAME_QUERY = "select S from Speaker S where UPPER(S.name) like CONCAT('%',UPPER(?1),'%')";

    Optional<Speaker> findByName(String name);

    @Query(FILTER_SPEAKER_ON_NAME_QUERY)
    Page<Speaker> findAllByNameLike(String name, Pageable pageable);
}
