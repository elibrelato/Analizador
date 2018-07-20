package com.elibrelato.analizador.relatorio;

/*
Depois de processar todos os arquivos dentro do diretório padrão de entrada, o sistema deve criar um arquivo dentro do diretório
de saída padrão, localizado em %HOMEPATH% /data/out. O nome do arquivo deve seguir o padrão, {flat_file_name} .done.dat.

O conteúdo do arquivo de saída deve resumir os seguintes dados:
- Quantidade de clientes no arquivo de entrada
- Quantidade de vendedor no arquivo de entrada
- ID da venda mais cara
- O pior vendedor
*/

import com.elibrelato.analizador.App;
import com.elibrelato.analizador.dados.Dados;
import com.elibrelato.analizador.entity.Item;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author elibrelato@gmail.com
 */
public class Relatorio {

    private final Collection<File> arquivosProcessados;
    private final Collection<String> dados = new ArrayList<>(); 

    /**
     * Inicia um relatorio
     * @param arquivosProcessados - Collection contendo a lista de arquivos a serem utilizadas no relatorio.
     */
    public Relatorio(Collection<File> arquivosProcessados) {
        this.arquivosProcessados = arquivosProcessados;
    }
    
    /**
     * Gera o relatorio e salva os dados com o charset utilizado pela plataforma.
     * @param outputFolder Pasta onde serao salvos os arquivos do relatorio.
     */
    public void gerarRelatorio(File outputFolder) {
        gerarRelatorio(outputFolder, null);
    }

    /**
     * Gera o relatorio e salva os dados com o charset especificado. 
     * 
     * @param outputFolder Pasta onde serao salvos os arquivos do relatorio
     * @param charset Charset utilizado para gravar o arquivo de saida.
     * Charsets suportados: US-ASCII, UTF-8, UTF-16, ISO-8859-1 (ANSI).
     * Caso charset=null, sera utilizado o charset padrao da plataforma (no Windows sera ANSI).
     */
    public void gerarRelatorio(File outputFolder, String charset) {
        arquivosProcessados.forEach(file -> {
            System.out.println("Gerando relatorio do arquivo: " + file.getName());
            try {
                dados.clear();
                dados.add("Quantidade de clientes: " + getQuantidadeDeClientes(file));
                dados.add("Quantidade de vendedores: " + getQuantidadeDeVendedores(file));
                dados.add("ID da venda mais cara: " + getIdDaVendaMaisCara(file));
                dados.add("Pior Vendedor: " + getPiorVendedor(file));

                String outputFileName = outputFolder.getCanonicalPath() + File.separatorChar
                        + FilenameUtils.getBaseName(file.getName()) + ".done." + App.getExtension();

                salvar(outputFileName, dados, charset);
                
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
    }
    
    // COMO NÃO FICOU BEM CLARO O MODELO DE RELATÓRIO SE É INDIVIDUAL OU GERAL.. ACABEI FAZENDO OUTRO MODELO
    
    /**
     * Gera o relatorio e salva os dados com o charset especificado. 
     * 
     * @param outputFolder Pasta onde serao salvos os arquivos do relatorio
     * @param charset Charset utilizado para gravar o arquivo de saida.
     * Charsets suportados: US-ASCII, UTF-8, UTF-16, ISO-8859-1 (ANSI).
     * Caso charset=null, sera utilizado o charset padrao da plataforma (no Windows sera ANSI).
     */
    public void gerarRelatorioGeral(File outputFolder, String charset) {
        dados.clear();
        System.out.println("Gerando relatorio Geral...");
        arquivosProcessados.forEach(file -> {
            System.out.println("Arquivo: " + file.getName());             
            dados.add("Arquivo: " + file.getName() + " - Quantidade de clientes : " + getQuantidadeDeClientes(file));
            dados.add("Arquivo: " + file.getName() + " - Quantidade de vendedores: " + getQuantidadeDeVendedores(file));
            dados.add("Arquivo: " + file.getName() + " - ID da venda mais cara: " + getIdDaVendaMaisCara(file));
            dados.add("Arquivo: " + file.getName() + " - Pior Vendedor: " + getPiorVendedor(file));
        });
        
        dados.add("Quantidade total de clientes: " + getQuantidadeDeClientes());
        dados.add("Quantidade total de vendedores: " + getQuantidadeDeVendedores());
        dados.add("ID da venda mais cara: " + getIdDaVendaMaisCara());
        dados.add("Pior Vendedor: " + getPiorVendedor());
        
        try {
            String outputFileName = outputFolder.getCanonicalPath() + File.separatorChar
                    + new SimpleDateFormat("yyyy-MM-dd-hhmmss").format(Calendar.getInstance().getTime())
                    + ".done." + App.getExtension();
            salvar(outputFileName, dados, charset);
            System.out.println("Relatorio Salvo.");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }     
    }
    
    // Metodos criados para buscar as informações que desejamos visualizar nos relatórios
    
    private double maior, menor, valor, total;
    private boolean primeiro;
    private String resultado;
    private Collection<String> nomes = new ArrayList<>();

    /**
     * Retorna a quantidade de vendedores de um determinado arquivo.
     * @param file Arquivo de referencia. Indica a qual arquivo pertence a informacao retornada.
     * Esse arquivo deve ser previamente processado e seus dados devem estar persistidos na classe <b>Dados</b>.
     * @return Um inteiro contendo a quantidade de vendedores.
     */
    private int getQuantidadeDeVendedores(File file) {
        return Dados.getVendedores().containsKey(file) ? Dados.getVendedores().get(file).size() : 0;
    }

    /**
     * Retorna a quantidade de clientes de um determinado arquivo.
     * @param file Arquivo de referencia. Indica a qual arquivo pertence a informacao retornada.
     * Esse arquivo deve ser previamente processado e seus dados devem estar persistidos na classe <b>Dados</b>.
     * @return Um inteiro contendo a quantidade de clientes.
     */
    private int getQuantidadeDeClientes(File file) {
        return Dados.getClientes().containsKey(file) ? Dados.getClientes().get(file).size() : 0;
    }

    /**
     * Retorna o ID da venda mais cara de um determinado arquivo.
     * @param file Arquivo de referencia. Indica a qual arquivo pertence a informacao retornada.
     * Esse arquivo deve ser previamente processado e seus dados devem estar persistidos na classe <b>Dados</b>.
     * @return Uma String contendo o ID da venda mais cara.
     */
    private String getIdDaVendaMaisCara(File file) {
        maior = 0;
        resultado = "err";
        if (Dados.getVendas().containsKey(file)) {
            Dados.getVendas().get(file).forEach(venda -> {
                valor = getValorTotalDaVenda(venda.getItems());
                if (valor > maior) {
                    maior = valor;
                    resultado = venda.getId();
                }
            });
        }
        return resultado;
    }

    /**
     * Retorna o pior vendedor de um determinado arquivo.
     * @param file Arquivo de referencia. Indica a qual arquivo pertence a informacao retornada.
     * Esse arquivo deve ser previamente processado e seus dados devem estar persistidos na classe <b>Dados</b>.
     * @return Uma String contendo o nome do pior vendedor.
     */
    private String getPiorVendedor(File file) {
        primeiro = true;
        resultado = "err";
        if (Dados.getVendas().containsKey(file)) {
            Dados.getVendas().get(file).forEach(venda -> {
                valor = getValorTotalDaVenda(venda.getItems());
                if (primeiro == true || valor < menor) {
                    menor = valor;
                    resultado = venda.getVendedor();
                    primeiro = false;
                }
            });
        }
        return resultado;
    }
    
    private double getValorTotalDaVenda(List<Item> items) {
        total = 0;
        items.forEach(item -> total += item.getQuantity() * item.getPrice());
        return total;
    }

    /**
     * Retorna a quantidade de vendedores. O valor retornado representa a quantidade
     * de vendedores entre todos os arquivos processados.
     * Para obter o resultado desejado, todos os arquivos devem ser previamente processados
     * e seus dados devem estar persistidos na classe <b>Dados</b>.
     * @return Inteiro contendo a quantidade de vendedores.
     */
    private int getQuantidadeDeVendedores() {
        nomes.clear();
        Dados.getVendedores().forEach((file, vendedores) -> {
            vendedores.forEach(vendedor -> {
                if (vendedor != null && vendedor.getName() != null && !nomes.contains(vendedor.getName())) {
                    nomes.add(vendedor.getName());
                }
            });
        });
        return nomes.size();
    }
    
    /**
     * Retorna a quantidade de clientes. O valor retornado representa a quantidade
     * de clientes entre todos os arquivos processados.
     * Para obter o resultado desejado, todos os arquivos devem ser previamente processados
     * e seus dados devem estar persistidos na classe <b>Dados</b>.
     * @return Inteiro contendo a quantidade de clientes.
     */
    private int getQuantidadeDeClientes() {
        nomes.clear();
        Dados.getClientes().forEach((file, clientes) -> {
            clientes.forEach(cliente -> {
                if (cliente != null && cliente.getName() != null && !nomes.contains(cliente.getName())) {
                    nomes.add(cliente.getName());
                }
            });
        });
        return nomes.size();
    }
    
    /**
     * Retorna o ID da melhor venda. O valor retornado representa a melhor venda entre todos os arquivos processados.
     * Para obter o resultado desejado, todos os arquivos devem ser previamente processados
     * e seus dados devem estar persistidos na classe <b>Dados</b>.
     * @return Um inteiro contendo o ID da melhor venda.
     */
    private String getIdDaVendaMaisCara() {
        maior = 0;
        resultado = "err";
        Dados.getVendas().forEach((file, vendas) -> {
            vendas.forEach(venda -> {
                valor = getValorTotalDaVenda(venda.getItems());
                if (valor > maior) {
                    maior = valor;
                    resultado = venda.getId();
                }
            });
        });
        return resultado;
    }

    /**
     * Retorna o pior vendedor. O valor retornado representa o pior vendedor entre todos os arquivos processados.
     * Para obter o resultado desejado, todos os arquivos devem ser previamente processados
     * e seus dados devem estar persistidos na classe <b>Dados</b>.
     * @return Um inteiro contendo o nome do pior vendedor.
     */
    private String getPiorVendedor() {
        primeiro = true;
        resultado = "err";
        Dados.getVendas().forEach((file, vendas) -> {
            vendas.forEach(venda -> {
                valor = getValorTotalDaVenda(venda.getItems());
                if (primeiro == true || valor < menor) {
                    menor = valor;
                    resultado = venda.getVendedor();
                    primeiro = false;
                }
            });
        });
        return resultado;
    }

    /**
     * Salva o relatorio.
     * @param fileName Nome e path do arquivo de saida. Deve estar no formato "c:/users/user/out/test.done.dat"
     * @param charset Charset utilizado para gravar o arquivo de saida.
     * Charsets suportados: US-ASCII, UTF-8, UTF-16, ISO-8859-1. 
     * Caso charset=null, sera utilizado o charset padrao da plataforma (ANSI no case do windows).
     * @param dados Collection contendo as linhas de dados a ser salvo.
     */
    private void salvar(String fileName, Collection dados, String charset) {
        try {
            File file = new File(fileName);
            FileUtils.writeLines(file, charset, dados, null);
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}