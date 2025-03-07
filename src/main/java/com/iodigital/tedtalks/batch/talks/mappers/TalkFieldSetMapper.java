package com.iodigital.tedtalks.batch.talks.mappers;

import com.iodigital.tedtalks.dto.SpeakerDTO;
import com.iodigital.tedtalks.dto.TalkDTO;
import com.iodigital.tedtalks.models.TalkCSVFileHeaders;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.validation.BindException;

public class TalkFieldSetMapper implements FieldSetMapper<TalkDTO> {

    private static final String DATE_FORMAT = "MMMM yyyy";

    @Override
    public TalkDTO mapFieldSet(FieldSet fieldSet) throws BindException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String dateString = fieldSet.readString(TalkCSVFileHeaders.DATE.getValue());
        TalkDTO talkDTO = new TalkDTO();
        talkDTO.setTitle(fieldSet.readString(TalkCSVFileHeaders.TITLE.getValue()));
        talkDTO.setViews(fieldSet.readLong(TalkCSVFileHeaders.VIEWS.getValue()));
        talkDTO.setLikes(fieldSet.readLong(TalkCSVFileHeaders.LIKES.getValue()));
        talkDTO.setLink(fieldSet.readString(TalkCSVFileHeaders.LINK.getValue()));
        try {
            talkDTO.setDate(dateFormat.parse(dateString).toInstant());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        SpeakerDTO speakerDTO = new SpeakerDTO();
        speakerDTO.setName(fieldSet.readString(TalkCSVFileHeaders.AUTHOR.getValue()));
        talkDTO.setSpeaker(speakerDTO);

        return talkDTO;
    }
}
