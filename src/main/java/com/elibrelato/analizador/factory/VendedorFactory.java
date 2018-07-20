package com.elibrelato.analizador.factory;

import com.elibrelato.analizador.config.Config;
import com.elibrelato.analizador.entity.Vendedor;

/**
 *
 * @author elibrelato@gmail.com
 */
public class VendedorFactory {
    
    public Vendedor getVendedor(String dados) {
        Vendedor vendedor = new Vendedor();
        String node, value;
        String[] nodes = dados.split(Config.VENDEDOR_SPLIT);
        
        for (int i1 = 0; i1 < nodes.length; i1++) {
            node = Config.VENDEDOR_NODES.get(i1);
            value = nodes[i1];
            if (node != null) { // Safety
                switch (node) {
                    case Config.VENDEDOR_STRING_CPF:
                        vendedor.setCpf(value);
                        break;
                    case Config.VENDEDOR_STRING_NAME:
                        vendedor.setName(value);
                        break;
                    case Config.VENDEDOR_STRING_SALARY:    
                        try {
                            vendedor.setSalary(Double.parseDouble(value));
                        } catch (NumberFormatException ex) {
                            System.out.println(ex.getMessage());
                        }
                        break;
                }
            }
        }              
        return vendedor;
    }
}
