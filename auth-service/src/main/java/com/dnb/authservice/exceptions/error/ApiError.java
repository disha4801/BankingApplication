package com.dnb.authservice.exceptions.error;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError implements Serializable {

    private int statusCode;

    private HttpStatus status;

    private LocalDateTime timestamp;

    private String message;

    List<String> errorDetails;

}
