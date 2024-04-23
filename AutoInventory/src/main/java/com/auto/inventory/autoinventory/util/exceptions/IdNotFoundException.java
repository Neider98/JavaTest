package com.auto.inventory.autoinventory.util.exceptions;

public class IdNotFoundException extends RuntimeException{
    
    private static final String ERROR_MESSAGE = "Id no encontrado en %s";

    public IdNotFoundException(String tableName) {
        super(String.format(ERROR_MESSAGE, tableName));
    }

}
