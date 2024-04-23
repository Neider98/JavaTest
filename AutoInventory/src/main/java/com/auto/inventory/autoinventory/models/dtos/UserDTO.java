package com.auto.inventory.autoinventory.models.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class UserDTO {

    private Long id;

    @NotBlank
    private String name;

    @Min(1)
    private int age;

    @NotBlank
    private PositionDTO position;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate entryDate;

    public UserDTO(long l, String user1, int i, PositionDTO position1, LocalDate now) {
    }

    public UserDTO() {

    }
}
