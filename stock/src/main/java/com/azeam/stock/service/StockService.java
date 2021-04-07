package com.azeam.stock.service;

import com.azeam.stock.dto.ProductDto;

public interface StockService {
    ProductDto createProduct(ProductDto productDtoIn); 

    ProductDto getProduct(ProductDto productDtoIn);

    String updateProduct(ProductDto productDtoIn);

    String deleteProduct(ProductDto productDtoIn);

    ProductDto[] getProducts();
}
