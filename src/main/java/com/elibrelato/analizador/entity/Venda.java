package com.elibrelato.analizador.entity;

/**
 *
 * @author elibrelato@gmail.com
 */
import java.util.List;

public class Venda implements Entity {
    
    private String id;
    private String vendedor;
    private List<Item> items;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
