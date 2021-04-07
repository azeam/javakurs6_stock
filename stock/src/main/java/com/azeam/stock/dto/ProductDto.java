package com.azeam.stock.dto;

import java.io.Serializable;

public class ProductDto implements Serializable {
    private static final long serialVersionUID = 8906566600256227911L;
    private long id;
    private String name;
    private String pid;
    private String category;
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

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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
