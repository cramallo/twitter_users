package com.twitter.users.infraestructure.exceptions;

import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
@Builder
public class ErrorResponse {
    HttpStatus status;
    String message;
}
