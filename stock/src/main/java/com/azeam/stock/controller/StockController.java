package com.azeam.stock.controller;

import com.azeam.stock.dto.ProductDto;
import com.azeam.stock.entity.ProductEntity;
import com.azeam.stock.model.request.ProductDetailsRequestModel;
import com.azeam.stock.model.response.ProductResponseModel;
import com.azeam.stock.service.StockService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("stock")
public class StockController {

    @Autowired
    StockService stockService;
    
    @GetMapping
    public String getProduct() {
       // return stockService.getProduct();
       return null;
    }

    @PostMapping
    public ProductResponseModel createProduct(@RequestBody ProductDetailsRequestModel productDetailsModel) {
        // copy json to dto in
        ProductDto productDtoIn = new ProductDto();
        BeanUtils.copyProperties(productDetailsModel, productDtoIn);

        // pass dto to service layer
        ProductDto productDtoOut = stockService.createProduct(productDtoIn);

        // copy dto out from service layer to response
        ProductResponseModel response = new ProductResponseModel();
        BeanUtils.copyProperties(productDtoOut, response);
        return response;
    } 

    @PutMapping
    public String updateProduct() {
        return stockService.updateProduct();
    }

    @DeleteMapping
    public String deleteUser() {
        return stockService.deleteUser();
    }
}
