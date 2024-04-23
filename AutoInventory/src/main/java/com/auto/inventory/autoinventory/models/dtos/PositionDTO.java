package com.auto.inventory.autoinventory.models.dtos;

import lombok.Data;

@Data
public class PositionDTO {

    private Long id;

    private String name;

    public PositionDTO(long l, String position1) {
    }
}
