package com.example.qysqaserver.dto.params;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationParams {
    @Min(value = 0, message = "validation.page.min")
    private int page;
    @Min(value = 1, message = "validation.size.min")
    @Max(value = 100, message = "validation.size.max")
    private int size = 10;
}
