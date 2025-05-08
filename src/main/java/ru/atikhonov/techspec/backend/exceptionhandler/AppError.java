package ru.atikhonov.techspec.backend.exceptionhandler;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AppError {
    private final int statusCode;
    private final String message;
}
