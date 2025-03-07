package com.iodigital.tedtalks.repositories;

import com.iodigital.tedtalks.entity.Talk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TalkRepository extends JpaRepository<Talk, UUID> {

    String FILTER_TALK_ON_TITLE_QUERY = "select T from Talk T where UPPER(T.title) like CONCAT('%',UPPER(?1),'%')";


    @Query(FILTER_TALK_ON_TITLE_QUERY)
    Page<Talk> findAllByTitleLike(String title, Pageable pageable);
}
