package com.savvyers.savvyersserver.product.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductIdRequest {

    @NotNull(message = "상품 ID는 필수입니다")
    @Positive(message = "상품 ID는 양수여야 합니다")
    private Long id;
}

