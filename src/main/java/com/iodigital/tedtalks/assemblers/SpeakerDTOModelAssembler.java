package com.iodigital.tedtalks.assemblers;

import com.iodigital.tedtalks.controller.SpeakerController;
import com.iodigital.tedtalks.controller.TalkController;
import com.iodigital.tedtalks.dto.SpeakerDTO;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SpeakerDTOModelAssembler extends RepresentationModelAssemblerSupport<SpeakerDTO, SpeakerDTO> {
    public SpeakerDTOModelAssembler() {
        super(SpeakerController.class, SpeakerDTO.class);
    }

    @Override
    public SpeakerDTO toModel(SpeakerDTO dto) {
        dto.add(linkTo(methodOn(TalkController.class).find(dto.getId())).withSelfRel());
        return dto;
    }
}
