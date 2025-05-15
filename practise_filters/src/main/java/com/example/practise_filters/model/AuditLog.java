package com.example.practise_filters.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String requestBody;
    private String responseBody;
    private String requestHeaders;
    private String responseHeaders;
    private String requestMethod;
    private String requestUri;
    private int responseStatus;
    private LocalDateTime timestamp;
}
