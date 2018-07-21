package com.elibrelato.analizador.factory;

import com.elibrelato.analizador.config.Config;
import com.elibrelato.analizador.entity.Cliente;

/**
 *
 * @author elibrelato@gmail.com
 */
public class ClienteFactory {
    
    public Cliente getCliente(String dados) {
        Cliente cliente = null;
        String node, value;
        String[] nodes = dados.split(Config.CLIENTE_SPLIT);
        if (nodes.length != Config.CLIENTE_NODES.size()) { // Safety
            System.out.println("Erro na configuracao do CLIENTE!");
        } else {
            cliente = new Cliente();
            for (int i1 = 0; i1 < nodes.length; i1++) {
                node = Config.CLIENTE_NODES.get(i1);
                value = nodes[i1];
                if (node != null) { // Safety
                    switch (node) {
                        case Config.CLIENTE_STRING_CNPJ:
                            cliente.setCnpj(value);
                            break;
                        case Config.CLIENTE_STRING_NAME:
                            cliente.setName(value);
                            break;
                        case Config.CLIENTE_STRING_BUSINESS_AREA:
                            cliente.setBusiness_area(value);
                            break;
                    }
                }
            }
        }
        return cliente;
    }
}
