package com.savvyers.savvyersserver.product.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchRequest {

    @NotBlank(message = "검색 키워드는 필수입니다")
    private String keyword;

    @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다")
    private int page = 0;

    @Min(value = 1, message = "페이지 크기는 최소 1 이상이어야 합니다")
    @Max(value = 24, message = "페이지 크기는 최대 24까지 가능합니다")
    private int size = 10;
}

