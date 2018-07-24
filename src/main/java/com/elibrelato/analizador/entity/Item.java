package com.elibrelato.analizador.entity;

import java.math.BigDecimal;

/**
 *
 * @author elibrelato@gmail.com
 */
public class Item {

    private String id;
    private int quantity;
    private BigDecimal price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
