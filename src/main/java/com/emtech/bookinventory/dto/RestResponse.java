package com.emtech.bookinventory.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class RestResponse {
    private ResponseObject body;
    private HttpStatus status;
}
