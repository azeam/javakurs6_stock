package com.azeam.stock.controller;

import com.azeam.stock.dto.ProductDto;
import com.azeam.stock.model.request.ProductDetailsRequestModel;
import com.azeam.stock.model.response.ProductResponseModel;
import com.azeam.stock.service.StockService;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("stock")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping(value="/{pid}")
    public ProductResponseModel getProduct(@PathVariable String pid) {
        ProductDto productDtoIn = new ProductDto();
        productDtoIn.setPid(pid);

        // pass dto in to service layer
        ProductDto productDtoOut = stockService.getProduct(productDtoIn);

        // copy dto out from service layer to response
        ProductResponseModel response = new ProductResponseModel();
        BeanUtils.copyProperties(productDtoOut, response);

        return response;
    }
    
    @GetMapping
    public ProductResponseModel[] getProducts() {
        ProductDto[] productDtoOut = stockService.getProducts();
        ProductResponseModel[] products = new ProductResponseModel[productDtoOut.length];
        
        // build array with models from dtos
        int count = 0;
        for (ProductDto productDto: productDtoOut) {
            ProductResponseModel response = new ProductResponseModel();
            BeanUtils.copyProperties(productDto, response);    
            products[count] = response;
            count++;
        }

        return products;
    }

    @PostMapping
    public ProductResponseModel createProduct(@RequestBody ProductDetailsRequestModel productDetailsModel) {
        // copy json to dto in
        ProductDto productDtoIn = new ProductDto();
        BeanUtils.copyProperties(productDetailsModel, productDtoIn);

        // pass dto in to service layer
        ProductDto productDtoOut = stockService.createProduct(productDtoIn);
 
        // copy dto out from service layer to response
        ProductResponseModel response = new ProductResponseModel();
        BeanUtils.copyProperties(productDtoOut, response);

        return response;
    } 

    @PutMapping(value="/{pid}")
    public ProductResponseModel updateProduct(@PathVariable String pid, @RequestBody ProductDetailsRequestModel productDetailsModel) {
        ProductDto productDtoIn = new ProductDto();
        productDtoIn.setPid(pid);
        
        // get dto of product with id
        ProductDto productDtoToEdit = stockService.getProduct(productDtoIn);

        // pass dto to edit and new data to service
        ProductDto productDtoOut = stockService.updateProduct(productDtoToEdit, productDetailsModel);

        // copy dto out from service layer to response, show updated object
        ProductResponseModel response = new ProductResponseModel();
        BeanUtils.copyProperties(productDtoOut, response);
        return response;
    }

    @DeleteMapping(value="/{pid}")
    public ResponseEntity<String> deleteProduct(@PathVariable String pid) {
        ProductDto productDtoIn = new ProductDto();
        productDtoIn.setPid(pid);
        
        // get dto of product with id
        ProductDto productDtoToEdit = stockService.getProduct(productDtoIn);

        // pass dto to edit and new data to service
        ResponseEntity<String> productDtoOut = stockService.deleteProduct(productDtoToEdit);

        return productDtoOut;
    }

}
