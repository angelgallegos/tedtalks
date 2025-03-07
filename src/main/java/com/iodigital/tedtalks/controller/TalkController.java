package com.iodigital.tedtalks.controller;

import com.iodigital.tedtalks.assemblers.TalkDTOModelAssembler;
import com.iodigital.tedtalks.config.validation.groups.OnCreate;
import com.iodigital.tedtalks.dto.TalkDTO;
import com.iodigital.tedtalks.service.TalkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@RestController
@RequestMapping("talk")
@RequiredArgsConstructor
public class TalkController {

    private final TalkService talkService;

    private final TalkDTOModelAssembler talkDTOModelAssembler;

    private final PagedResourcesAssembler<TalkDTO> pagedResourcesAssembler;

    @PostMapping("")
    TalkDTO add(@RequestBody @Validated(OnCreate.class) TalkDTO dto) {
        return talkService.create(dto);
    }

    @GetMapping("/{id}")
    public TalkDTO find(@PathVariable UUID id) {
        TalkDTO talkDTO = talkService.request(id);
        TalkDTO model = talkDTOModelAssembler.toModel(talkDTO);
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TalkController.class).all(Pageable.ofSize(10))).withRel("list"));

        return model;
    }

    @PutMapping("/{id}")
    TalkDTO update(@PathVariable UUID id, @RequestBody @Validated TalkDTO dto) {
        return talkService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    TalkDTO delete(@PathVariable UUID id) {
        return talkService.delete(id);
    }

    @GetMapping("/list")
    public PagedModel<TalkDTO> all(Pageable pageable) {

        return pagedResourcesAssembler.toModel(talkService.findAll(pageable), talkDTOModelAssembler);
    }

    @GetMapping("/filter")
    PagedModel<TalkDTO> filter(@RequestParam("title") String title, Pageable pageable) {

        return pagedResourcesAssembler.toModel(talkService.filterPaged(title, pageable), talkDTOModelAssembler);
    }
}
