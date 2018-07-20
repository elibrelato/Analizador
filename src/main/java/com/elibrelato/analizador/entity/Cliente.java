package com.elibrelato.analizador.entity;

/**
 *
 * @author elibrelato@gmail.com
 */
public class Cliente implements Entity {
 
    private String name;
    private String cnpj;
    private String business_area;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getCnpj() {
        return cnpj;
    }
    
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getBusinessArea() {
        return business_area;
    }
    
       public void setBusiness_area(String business_area) {
        this.business_area = business_area;
    }
}
