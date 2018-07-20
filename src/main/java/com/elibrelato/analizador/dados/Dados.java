package com.elibrelato.analizador.dados;

import com.elibrelato.analizador.entity.Cliente;
import com.elibrelato.analizador.entity.Entity;
import com.elibrelato.analizador.entity.Venda;
import com.elibrelato.analizador.entity.Vendedor;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base de dados em memoria que armazenara temporariamente os dados de todos arquivos processados.
 * @author elibrelato@gmail.com
 */

public class Dados {
    
    private static Map<File, List<Vendedor>> vendedores = new HashMap<>();
    private static Map<File, List<Cliente>> clientes = new HashMap<>();
    private static Map<File, List<Venda>> vendas = new HashMap<>();
    
    /**
     * Limpa a base de dados persistida em memoria.
     */
    public static void clear() {
        vendedores.clear();
        clientes.clear();
        vendas.clear();
    }
    
    /**
     * Retora a base de dados de vendedores que esta persistida em memoria.
     * @return um Map contendo os arquivos que foram processados e uma lista com os vendedores desse arquivo.
     */
    public static Map<File, List<Vendedor>> getVendedores() {
        return vendedores;
    }
    
    /**
     * Retora a base de dados de clientes que esta persistida em memoria.
     * @return um Map contendo os arquivos que foram processados e uma lista com os clientes desse arquivo.
     */
    public static Map<File, List<Cliente>> getClientes() {
        return clientes;
    }
    
    /**
     * Retora a base de dados de vendas que esta persistida em memoria.
     * @return um Map contendo os arquivos que foram processados e uma lista com os vendas desse arquivo.
     */
    public static Map<File, List<Venda>> getVendas() {
        return vendas;
    }
    
    public static void salvar(File file, Vendedor vendedor) {
        if (vendedores.containsKey(file)) {
            vendedores.get(file).add(vendedor);
        } else {
            List<Vendedor> lista = new ArrayList<>();
            lista.add(vendedor);
            vendedores.put(file, lista);
        }      
    }
    
    public static void salvar(File file, Cliente cliente) {
        if (clientes.containsKey(file)) {
            clientes.get(file).add(cliente);
        } else {
            List<Cliente> lista = new ArrayList<>();
            lista.add(cliente);
            clientes.put(file, lista);
        }
    }
    
    public static void salvar(File file, Venda venda) {
        if (vendas.containsKey(file)) {
            vendas.get(file).add(venda);
        } else {
            List<Venda> lista = new ArrayList<>();
            lista.add(venda);
            vendas.put(file, lista);
        }
    }
    
    /**
     * Salva uma entidade na base de dados persistida em memoria.
     * @param file Arquivo que foi processado.
     * @param entity Entidade a salvar
     */
    public static void salvar(File file, Entity entity) {
        if (entity instanceof Vendedor) {
            salvar(file, (Vendedor) entity);
        } else if (entity instanceof Cliente) {
            salvar(file, (Cliente) entity);
        } else if (entity instanceof Venda) {
            salvar(file, (Venda) entity);
        }
    }
}
