package com.azeam.stock.service.impl;

import java.util.Optional;
import java.util.stream.StreamSupport;

import com.azeam.stock.dto.ProductDto;
import com.azeam.stock.entity.ProductEntity;
import com.azeam.stock.repository.ProductRepository;
import com.azeam.stock.service.StockService;
import com.azeam.stock.service.util.Encryption;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StockServiceImpl implements StockService {

    private final ProductRepository productRepository;

    public StockServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    public ProductDto getProduct(ProductDto productDtoIn) {
        Optional<ProductEntity> productFound = productRepository.findByPid(productDtoIn.getPid());
        
        if (!productFound.isPresent()) {
            throw new ResponseStatusException (
                HttpStatus.NOT_FOUND, "Product not found"
            );
        }
        
        ProductDto productDtoOut = new ProductDto();
        BeanUtils.copyProperties(productFound.get(), productDtoOut);
        return productDtoOut;
    }

    @Override
    public String updateProduct(ProductDto productDtoIn) {
        return null;
    }

    @Override
    public String deleteProduct(ProductDto productDtoIn) {
        return null;
    }

    @Override
    public ProductDto createProduct(ProductDto productDtoIn) {
        // set generated pid to entity and save to db
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(productDtoIn, productEntity);
        Encryption enc = new Encryption();
        productEntity.setPid(enc.generateHash(productDtoIn.getName()));
        ProductEntity productEntityOut = productRepository.save(productEntity);

        // return response as dto
        ProductDto productDtoOut = new ProductDto();
        BeanUtils.copyProperties(productEntityOut, productDtoOut);
        return productDtoOut;
    }

    @Override
    public ProductDto[] getProducts() {
        Iterable<ProductEntity> productsFound = productRepository.findAll();
        ProductDto[] productsArray = new ProductDto[(int) StreamSupport.stream(productsFound.spliterator(), false).count()];
        int count = 0;
        for (ProductEntity productEntity : productsFound) {
            ProductDto productDtoOut = new ProductDto();
            BeanUtils.copyProperties(productEntity, productDtoOut);
            productsArray[count] = productDtoOut;
            count++;
        }
        return productsArray;
    }

}
