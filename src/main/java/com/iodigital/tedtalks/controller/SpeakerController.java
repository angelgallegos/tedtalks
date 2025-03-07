package com.iodigital.tedtalks.controller;

import com.iodigital.tedtalks.assemblers.SpeakerDTOModelAssembler;
import com.iodigital.tedtalks.dto.SpeakerDTO;
import com.iodigital.tedtalks.models.RatingTypes;
import com.iodigital.tedtalks.service.IRatingService;
import com.iodigital.tedtalks.service.SpeakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("speaker")
@RequiredArgsConstructor
public class SpeakerController {
    private final SpeakerService speakerService;

    private final SpeakerDTOModelAssembler speakerDTOModelAssembler;

    private final PagedResourcesAssembler<SpeakerDTO> pagedResourcesAssembler;

    private final BeanFactory beanFactory;

    @PostMapping("")
    SpeakerDTO add(@RequestBody @Validated SpeakerDTO dto) {
        return speakerService.create(dto);
    }

    @GetMapping("/{id}")
    public SpeakerDTO find(@PathVariable UUID id) {
        SpeakerDTO speakerDTO = speakerService.request(id);
        SpeakerDTO model = speakerDTOModelAssembler.toModel(speakerDTO);
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TalkController.class).all(Pageable.ofSize(10))).withRel("list"));

        return model;
    }

    @PutMapping("/{id}")
    SpeakerDTO update(@PathVariable UUID id, @RequestBody @Validated SpeakerDTO dto) {
        return speakerService.update(id, dto);
    }

    @GetMapping("/list")
    public PagedModel<SpeakerDTO> all(Pageable pageable) {

        return pagedResourcesAssembler.toModel(speakerService.findAll(pageable), speakerDTOModelAssembler);
    }

    @GetMapping("/filter")
    PagedModel<SpeakerDTO> filter(@RequestParam("name") String name, Pageable pageable) {

        return pagedResourcesAssembler.toModel(speakerService.filterPaged(name, pageable), speakerDTOModelAssembler);
    }

    @GetMapping("/list/order-by-rating/{type}")
    PagedModel<SpeakerDTO> listByInfluence(Pageable pageable, @PathVariable String type) {
        IRatingService service = (IRatingService) beanFactory.getBean(Objects.requireNonNull(RatingTypes.findByType(type)).getImplementation());
        return pagedResourcesAssembler.toModel(service.listOrderByRating(pageable), speakerDTOModelAssembler);
    }
}
