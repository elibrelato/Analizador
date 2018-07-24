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

import com.elibrelato.analizador.dados.Dados;
import com.elibrelato.analizador.entity.Item;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author elibrelato@gmail.com
 */
public class Relatorio extends DadosDoRelatorio {

    protected File file;
    protected final Collection<File> arquivosProcessados;
//    protected final Collection<String> dados = new ArrayList<>(); 

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
            this.file = file;
            this.clear();
            System.out.println("Gerando relatorio do arquivo: " + file.getName()); 
            this.add("Quantidade de clientes: " + getQuantidadeDeClientes(file));
            this.add("Quantidade de vendedores: " + getQuantidadeDeVendedores(file));
            this.add("ID da venda mais cara: " + getIdDaVendaMaisCara(file));
            this.add("Pior Vendedor: " + getPiorVendedor(file));              
            File outputFile = getOutputFile(outputFolder, file);
            salvar(this, outputFile, charset); 
        });
    }
    
    protected boolean primeiro;
    protected BigDecimal maior, menor, valor;    
    protected String resultado;
    
    // Metodos criados para buscar as informações que desejamos visualizar nos relatórios
    
    /**
     * Retorna a quantidade de vendedores de um determinado arquivo.
     * @param file Arquivo de referencia. Indica a qual arquivo pertence a informacao retornada.
     * Esse arquivo deve ser previamente processado e seus dados devem estar persistidos na classe <b>Dados</b>.
     * @return Um inteiro contendo a quantidade de vendedores.
     */
    protected int getQuantidadeDeVendedores(File file) {
        return Dados.getVendedores().containsKey(file) ? Dados.getVendedores().get(file).size() : 0;
    }

    /**
     * Retorna a quantidade de clientes de um determinado arquivo.
     * @param file Arquivo de referencia. Indica a qual arquivo pertence a informacao retornada.
     * Esse arquivo deve ser previamente processado e seus dados devem estar persistidos na classe <b>Dados</b>.
     * @return Um inteiro contendo a quantidade de clientes.
     */
    protected int getQuantidadeDeClientes(File file) {
        return Dados.getClientes().containsKey(file) ? Dados.getClientes().get(file).size() : 0;
    }

    /**
     * Retorna o ID da venda mais cara de um determinado arquivo.
     * @param file Arquivo de referencia. Indica a qual arquivo pertence a informacao retornada.
     * Esse arquivo deve ser previamente processado e seus dados devem estar persistidos na classe <b>Dados</b>.
     * @return Uma String contendo o ID da venda mais cara.
     */
    protected String getIdDaVendaMaisCara(File file) {
        maior = new BigDecimal("0");
        resultado = "err";
        if (Dados.getVendas().containsKey(file)) {
            Dados.getVendas().get(file).forEach(venda -> {
                valor = getValorTotalDaVenda(venda.getItems());
                if (valor.compareTo(maior) > 0) {
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
    protected String getPiorVendedor(File file) {
        primeiro = true;
        resultado = "err";
        menor = new BigDecimal("0");
        if (Dados.getVendas().containsKey(file)) {
            Dados.getVendas().get(file).forEach(venda -> {
                valor = getValorTotalDaVenda(venda.getItems());
                if (primeiro == true || valor.compareTo(menor) < 0) {
                    menor = valor;
                    resultado = venda.getVendedor();
                    primeiro = false;
                }
            });
        }
        return resultado;
    }
    
    protected BigDecimal getValorTotalDaVenda(List<Item> items) {
        BigDecimal total = new BigDecimal("0");
        items.forEach(item -> total.add(item.getPrice().multiply(new BigDecimal(item.getQuantity()))));
        return total;
    }
    
    protected File getOutputFile(File outputFolder, File file) {
        return new File (outputFolder.getAbsolutePath() + File.separatorChar
                + FilenameUtils.getBaseName(file.getName()) + FilenameUtils.EXTENSION_SEPARATOR_STR
                + "done" + FilenameUtils.EXTENSION_SEPARATOR_STR
                + FilenameUtils.getExtension(file.getName()));
    }

    /**
     * Salva o relatorio.
     * @param dados Collection contendo as linhas de dados a ser salvo.
     * @param file Arquivo de saida. Formato "c:/users/user/out/test.done.dat"
     * @param charset Charset utilizado para gravar o arquivo de saida.
     * Charsets suportados: US-ASCII, UTF-8, UTF-16, ISO-8859-1. 
     * Caso charset=null, sera utilizado o charset padrao da plataforma (ANSI no case do windows).
     */
    protected void salvar(Collection dados, File file, String charset) {
        try {
            FileUtils.writeLines(file, charset, dados, null);
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}