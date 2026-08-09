package com.mouady.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CityException extends RuntimeException {
    String message;
    public CityException(String message) {
        super(message);
    }
}
