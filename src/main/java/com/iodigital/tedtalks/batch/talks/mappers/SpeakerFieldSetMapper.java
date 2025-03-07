package com.iodigital.tedtalks.batch.talks.mappers;

import com.iodigital.tedtalks.dto.SpeakerDTO;
import com.iodigital.tedtalks.models.TalkCSVFileHeaders;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class SpeakerFieldSetMapper implements FieldSetMapper<SpeakerDTO> {

    private static final String DATE_FORMAT = "MMMM yyyy";

    @Override
    public SpeakerDTO mapFieldSet(FieldSet fieldSet) throws BindException {
        SpeakerDTO speakerDTO = new SpeakerDTO();
        speakerDTO.setName(fieldSet.readString(TalkCSVFileHeaders.AUTHOR.getValue()));

        return speakerDTO;
    }
}
