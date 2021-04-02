package com.azeam.stock.service.impl;

import java.util.Optional;

import com.azeam.stock.dto.ProductDto;
import com.azeam.stock.entity.ProductEntity;
import com.azeam.stock.repository.ProductRepository;
import com.azeam.stock.service.StockService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    ProductRepository productRepository;
    
    public String getProduct(ProductDto productDtoIn) {
        Optional<ProductEntity> product = productRepository.findByName(productDtoIn.getName());
        if (!product.isPresent()) {
            // TODO: return 404
        }
        return null;
    }

    public String updateProduct() {
        return null;
    }

    public String deleteUser() {
        return null;
    }

    @Override
    public ProductDto createProduct(ProductDto productDtoIn) {
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(productDtoIn, productEntity);
        ProductEntity productEntityOut = productRepository.save(productEntity);
        ProductDto productDtoOut = new ProductDto();
        BeanUtils.copyProperties(productEntityOut, productDtoOut);
        return productDtoOut;
    }
}
