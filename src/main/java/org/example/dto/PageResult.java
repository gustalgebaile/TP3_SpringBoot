package org.example.dto;

import java.util.List;

public record PageResult<T>(
        List<T> content,
        int total,
        int page,
        int size,
        int totalPages
) {}
