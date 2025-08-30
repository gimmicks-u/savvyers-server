package com.savvyers.savvyersserver.product;

import com.savvyers.savvyersserver.product.entity.ProductInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    public String getProducts() {
        return "hello";
    }
}
