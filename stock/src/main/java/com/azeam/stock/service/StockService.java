package com.azeam.stock.service;

import com.azeam.stock.dto.ProductDto;

import javassist.NotFoundException;

public interface StockService {
    ProductDto createProduct(ProductDto productDto); 

    String getProduct(ProductDto productDtoIn);

    String updateProduct();

    String deleteUser();
}
