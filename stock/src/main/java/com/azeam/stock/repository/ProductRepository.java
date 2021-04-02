package com.azeam.stock.repository;

import java.util.Optional;

import com.azeam.stock.entity.ProductEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByName(String name);

}
