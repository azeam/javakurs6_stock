package com.azeam.stock.service.impl;

import java.util.Optional;
import java.util.stream.StreamSupport;

import com.azeam.stock.dto.ProductDto;
import com.azeam.stock.entity.ProductEntity;
import com.azeam.stock.model.request.ProductDetailsRequestModel;
import com.azeam.stock.repository.ProductRepository;
import com.azeam.stock.service.StockService;
import com.azeam.stock.service.util.Encryption;
import com.azeam.stock.service.util.Filter;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> deleteProduct(ProductDto productDtoIn) {
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(productDtoIn, productEntity);
        productRepository.delete(productEntity);
        // 404 will be thrown earlier if not found
        return ResponseEntity.ok("Product deleted");
    }

    @Override
    public ProductDto createProduct(ProductDto productDtoIn) {
        // name needs to be set, otherwise return 400
        if (productDtoIn.getName() == null) {
            throw new ResponseStatusException (
                HttpStatus.BAD_REQUEST, "No product name set"
            );
        }

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
        
        // build dto array from iterable
        int count = 0;
        for (ProductEntity productEntity : productsFound) {
            ProductDto productDtoOut = new ProductDto();
            BeanUtils.copyProperties(productEntity, productDtoOut);
            productsArray[count] = productDtoOut;
            count++;
        }
        return productsArray;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDtoIn, ProductDetailsRequestModel productDetailsModel) {
        ProductEntity productEntity = new ProductEntity();
        // set old data to entity
        BeanUtils.copyProperties(productDtoIn, productEntity);

        // set new data where not null (only fields that should be updated)
        Filter filter = new Filter();
        BeanUtils.copyProperties(productDetailsModel, productEntity, filter.getNullPropertyNames(productDetailsModel));

        // save new data
        ProductEntity productEntityOut = productRepository.save(productEntity);

        // return response as dto
        ProductDto productDtoOut = new ProductDto();
        BeanUtils.copyProperties(productEntityOut, productDtoOut);
        return productDtoOut;
    }

    

}
