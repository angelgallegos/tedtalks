package com.iodigital.tedtalks.service;

import com.iodigital.tedtalks.dto.BaseDTO;
import com.iodigital.tedtalks.entity.BaseDBO;

import java.util.UUID;

public interface ICRUDService<DTO extends BaseDTO<DTO>> {
    DTO create(DTO dto);

    DTO request(UUID id);

    DTO update(UUID id, DTO dto);

    DTO delete(UUID id);
}
