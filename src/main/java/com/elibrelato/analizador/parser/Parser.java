package com.elibrelato.analizador.parser;

import com.elibrelato.analizador.config.Config;
import com.elibrelato.analizador.dados.Dados;
import com.elibrelato.analizador.entity.Entity;
import com.elibrelato.analizador.factory.ClienteFactory;
import com.elibrelato.analizador.factory.VendaFactory;
import com.elibrelato.analizador.factory.VendedorFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author elibrelato@gmail.com
 */
public class Parser {
    
    private Collection<File> arquivosProcessados = new ArrayList<>();

    /**
     * Executa o parse dos dados de uma lista de arquivos e salva os dados.
     * @param listaDeArquivos Lista de arquivos a serem processados.
     * @return Collection contendo a lista de arquivos que foi processada.
     */
    public Collection<File> parse(Collection<File> listaDeArquivos) {
        arquivosProcessados.clear();
        listaDeArquivos.forEach(file-> {
            parse(file);
            arquivosProcessados.add(file);
        });
        return arquivosProcessados;
    }

    /**
     * Efetua o parse dos dados de um arquivo e salva os dados.
     * @param file Arquivo a ser processado.
     */
    public void parse(File file) {
        
        System.out.println("Processando o arquivo: " + file.getName()); 
        Entity entity;
        String dados;
        
        try {
            FileInputStream f = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(f, StandardCharsets.UTF_8)); //"UTF-8"));
            
            dados = reader.readLine();
            
            if (dados != null) {
                if (dados.startsWith("\uFEFF")) { // it's UTF-8, throw away the BOM character and continue
                    dados = dados.substring(1);
                } else { // it's not UTF-8, reopen
                    reader.close(); // also closes fis
                    f = new FileInputStream(file); // reopen from the start
                    reader = new BufferedReader(new InputStreamReader(f));
                    dados = reader.readLine();
                }
            }                      
            
            while (dados != null) {
                entity = getEntity(dados);                              
                if (entity != null) { // Safety
                    Dados.salvar(file, entity);
                }               
                dados = reader.readLine();
            }
            
            reader.close();
            f.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Nao foi possivel encontrar o arquivo " + file.getName());
        } catch (IOException ex) {
            System.out.println("Nao foi possivel abrir o arquivo " + file.getName());
        }
    }

    /**
     * Efetua o parse dos dados.
     * @param dados Dados a serem processados.
     */
    private Entity getEntity(String dados) {
        if (dados.startsWith(Config.VENDEDOR_UNIQUE_ID)) {
            return new VendedorFactory().getVendedor(dados);
        } else if (dados.startsWith(Config.CLIENTE_UNIQUE_ID)) {
            return new ClienteFactory().getCliente(dados);
        } else if (dados.startsWith(Config.VENDA_UNIQUE_ID)) {
            return new VendaFactory().getVenda(dados);
        }
        return null;
    }
}
