package com.emtech.bookinventory.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.Year;

@Data
public class ResponseObject {

    private Object payload;

    private String message;
}
