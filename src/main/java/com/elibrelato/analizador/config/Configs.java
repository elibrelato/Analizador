package com.elibrelato.analizador.config;

/**
 *
 * @author elibrelato@gmail.com
 */
public interface Configs {
    public static final String CAMPO_VENDEDOR = "vendedor";
    public static final String CAMPO_CLIENTE = "cliente";
    public static final String CAMPO_VENDAS = "vendas";
    public static final String CAMPO_ITEM = "item";
    
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_SEPARADOR = "separador";
    public static final String CAMPO_FORMATO = "formato";
    
    public static final String CAMPO_INICIO = "inicio";
    public static final String CAMPO_FIM = "fim";
    public static final String CAMPO_SEPARADOR_DE_ITEMS = "separadordeitems";
    
    
    // dados do vendedor
//    public static final String VENDEDOR_ID = "id";
    public static final String VENDEDOR_STRING_CPF = "CPF";
    public static final String VENDEDOR_STRING_NAME = "Name";
    public static final String VENDEDOR_STRING_SALARY = "Salary";
    
//    // dados do cliente
//    public static final String CLIENTE_ID = "id";
    public static final String CLIENTE_STRING_CNPJ = "CNPJ";
    public static final String CLIENTE_STRING_NAME = "Name";
    public static final String CLIENTE_STRING_BUSINESS_AREA = "Business Area";
    
    // dados da venda
//    public static final String VENDA_ID = "id";
    public static final String VENDA_STRING_SALE_ID = "Sale ID";
    public static final String VENDA_STRING_ITEM = "[Item ID-Item Quantity-Item Price]";
    public static final String VENDA_STRING_SALESMAN_NAME = "Salesman Name";
    
    // dados do item
    public static final String ITEM_STRING_ITEM_ID = "Item ID";
    public static final String ITEM_STRING_ITEM_QUANTITY = "Item Quantity";
    public static final String ITEM_STRING_ITEM_PRICE = "Item Price";
}
