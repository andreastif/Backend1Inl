package com.backend1inl.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ErrorDetails {
    private LocalDateTime timeStamp;
    private String message;
    private String details; /// ghasdgasg
}
