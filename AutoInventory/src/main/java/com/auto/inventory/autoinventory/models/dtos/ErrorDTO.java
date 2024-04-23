package com.auto.inventory.autoinventory.models.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorDTO {

    private String message;
    private String status;
    private int code;
    private Date date;

}
