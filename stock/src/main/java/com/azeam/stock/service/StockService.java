package com.azeam.stock.service;

import com.azeam.stock.dto.ProductDto;
import com.azeam.stock.model.request.ProductDetailsRequestModel;

public interface StockService {
    ProductDto createProduct(ProductDto productDtoIn); 

    ProductDto getProduct(ProductDto productDtoIn);

    ProductDto updateProduct(ProductDto productDtoIn, ProductDetailsRequestModel productDetailsModel);

    void deleteProduct(ProductDto productDtoIn);

    ProductDto[] getProducts();
}
