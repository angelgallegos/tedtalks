package com.iodigital.tedtalks.assemblers;

import com.iodigital.tedtalks.controller.TalkController;
import com.iodigital.tedtalks.dto.TalkDTO;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TalkDTOModelAssembler extends RepresentationModelAssemblerSupport<TalkDTO, TalkDTO> {
    public TalkDTOModelAssembler() {
        super(TalkController.class, TalkDTO.class);
    }

    @Override
    public TalkDTO toModel(TalkDTO dto) {
        dto.add(linkTo(methodOn(TalkController.class).find(dto.getId())).withSelfRel());
        return dto;
    }
}
