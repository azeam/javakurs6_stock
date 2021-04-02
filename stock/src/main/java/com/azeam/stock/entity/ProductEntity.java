package com.azeam.stock.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity(name="product")
public class ProductEntity implements Serializable {
   
    private static final long serialVersionUID = 5923385817945218499L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 50)
    private String product_id;

    @Column(length = 50)
    private String category;

    @Column
    private int cost;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct_id() {
        return this.product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCost() {
        return this.cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

}
