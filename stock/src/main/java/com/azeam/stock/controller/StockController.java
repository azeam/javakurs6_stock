package com.azeam.stock.controller;

import com.azeam.stock.dto.ProductDto;
import com.azeam.stock.model.request.ProductDetailsRequestModel;
import com.azeam.stock.model.response.ProductResponseModel;
import com.azeam.stock.service.StockService;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ProductResponseModel> getProduct(@PathVariable String pid) {
        ProductDto productDtoIn = new ProductDto();
        productDtoIn.setPid(pid);

        // pass dto in to service layer
        ProductDto productDtoOut = stockService.getProduct(productDtoIn);

        // copy dto out from service layer to response
        ProductResponseModel response = new ProductResponseModel();
        BeanUtils.copyProperties(productDtoOut, response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping
    public ResponseEntity<ProductResponseModel[]> getProducts() {
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
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductResponseModel> createProduct(@RequestBody ProductDetailsRequestModel productDetailsModel) {
        // copy json to dto in
        ProductDto productDtoIn = new ProductDto();
        BeanUtils.copyProperties(productDetailsModel, productDtoIn);

        // pass dto in to service layer
        ProductDto productDtoOut = stockService.createProduct(productDtoIn);
 
        // copy dto out from service layer to response
        ProductResponseModel response = new ProductResponseModel();
        BeanUtils.copyProperties(productDtoOut, response);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    } 

    @PutMapping(value="/{pid}")
    public ResponseEntity<ProductResponseModel> updateProduct(@PathVariable String pid, @RequestBody ProductDetailsRequestModel productDetailsModel) {
        ProductDto productDtoIn = new ProductDto();
        productDtoIn.setPid(pid);
        
        // get dto of product with id
        ProductDto productDtoToEdit = stockService.getProduct(productDtoIn);

        // pass dto to edit and new data to service
        ProductDto productDtoOut = stockService.updateProduct(productDtoToEdit, productDetailsModel);

        // copy dto out from service layer to response, show updated object
        ProductResponseModel response = new ProductResponseModel();
        BeanUtils.copyProperties(productDtoOut, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
        
    }

    @DeleteMapping(value="/{pid}")
    public ResponseEntity<String> deleteProduct(@PathVariable String pid) {
        ProductDto productDtoIn = new ProductDto();
        productDtoIn.setPid(pid);
        
        // get dto of product with id
        ProductDto productDtoToEdit = stockService.getProduct(productDtoIn);

        // pass dto to edit and new data to service
        stockService.deleteProduct(productDtoToEdit);
        return new ResponseEntity<>("Product deleted", HttpStatus.OK);
    }

}
