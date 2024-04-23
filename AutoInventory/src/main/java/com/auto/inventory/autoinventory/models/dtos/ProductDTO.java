package com.auto.inventory.autoinventory.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@RequiredArgsConstructor
public class ProductDTO {

    private Long id;

    @NotBlank
    private String name;

    @Min(1)
    private int quantity;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "La fecha de ingreso debe ser menor o igual a la fecha actual")
    private Date entryDate;

    private UserDTO registeredBy;

    private UserDTO lastModifiedBy;

    private LocalDateTime lastModifiedDate;


    public ProductDTO(String newProduct, int i, Date date) {
    }
}
