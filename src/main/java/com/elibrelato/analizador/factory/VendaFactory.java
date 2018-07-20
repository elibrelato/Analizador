package com.elibrelato.analizador.factory;

import com.elibrelato.analizador.config.Config;
import com.elibrelato.analizador.entity.Venda;

/**
 *
 * @author elibrelato@gmail.com
 */
public class VendaFactory {
    
    public Venda getVenda(String dados) {
        Venda venda = new Venda();
        ItemsFactory itemsFactory = new ItemsFactory();        
        String node, value;
        String[] nodes = dados.split(Config.VENDA_SPLIT);
        
        for (int i1 = 0; i1 < nodes.length; i1++) {
            node = Config.VENDA_NODES.get(i1);
            value = nodes[i1];           
            if (node != null) { // Safety
                switch (node) {
                    case Config.VENDA_STRING_SALE_ID:
                        venda.setId(value);
                        break;
                    case Config.VENDA_STRING_SALESMAN_NAME:
                        venda.setVendedor(value);
                        break;
                    case Config.VENDA_STRING_ITEM:
                        venda.setItems(itemsFactory.getItems(value));
                        break; 
                }
            }
        }              
//        System.out.println("Criado Venda: " + venda.getId() + ", Vendedor: " + venda.getVendedor() + ", Items: " + venda.getItems().size());
        return venda;
    }
}
