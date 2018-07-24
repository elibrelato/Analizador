package com.elibrelato.analizador.entity;

import java.math.BigDecimal;

/**
 *
 * @author elibrelato@gmail.com
 */
public class Vendedor implements Entity {
 
    private String name;
    private String cpf;
    private BigDecimal salary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public BigDecimal getSalary() {
        return salary;
    }
    
    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
