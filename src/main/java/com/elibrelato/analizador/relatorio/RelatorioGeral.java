package com.elibrelato.analizador.relatorio;

import com.elibrelato.analizador.dados.Dados;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author elibrelato@gmail.com
 */
public class RelatorioGeral extends Relatorio {
    
    // Exemplo de como gerar outro modelo de relatorio...        
    
    public RelatorioGeral(Collection<File> arquivosProcessados) {
        super(arquivosProcessados);
    }

    /**
     * Gera o relatorio e salva os dados com o charset especificado. 
     * 
     * @param outputFolder Pasta onde serao salvos os arquivos do relatorio
     * @param charset Charset utilizado para gravar o arquivo de saida.
     * Charsets suportados: US-ASCII, UTF-8, UTF-16, ISO-8859-1 (ANSI).
     * Caso charset=null, sera utilizado o charset padrao da plataforma (no Windows sera ANSI).
     */
    @Override
    public void gerarRelatorio(File outputFolder, String charset) {
        this.clear();
        System.out.println("Gerando relatorio Geral...");
        arquivosProcessados.forEach(file -> {
            this.file = file;
            System.out.println("Arquivo: " + file.getName());             
            this.add("Arquivo: " + file.getName() + " - Quantidade de clientes : " + getQuantidadeDeClientes(file));
            this.add("Arquivo: " + file.getName() + " - Quantidade de vendedores: " + getQuantidadeDeVendedores(file));
            this.add("Arquivo: " + file.getName() + " - ID da venda mais cara: " + getIdDaVendaMaisCara(file));
            this.add("Arquivo: " + file.getName() + " - Pior Vendedor: " + getPiorVendedor(file));
        });
        
        this.add("Quantidade total de clientes: " + getQuantidadeDeClientes());
        this.add("Quantidade total de vendedores: " + getQuantidadeDeVendedores());
        this.add("ID da venda mais cara: " + getIdDaVendaMaisCara());
        this.add("Pior Vendedor: " + getPiorVendedor());
        

        File outputFile = new File(outputFolder.getAbsolutePath() + File.separatorChar
                + new SimpleDateFormat("yyyy-MM-dd-hhmmss").format(Calendar.getInstance().getTime())
                + FilenameUtils.EXTENSION_SEPARATOR_STR
                + "done" + FilenameUtils.EXTENSION_SEPARATOR_STR
                + FilenameUtils.getExtension(file.getName())); 

        salvar(this, outputFile, charset);
        System.out.println("Relatorio Salvo.");         
    }
    
    protected Collection<String> nomes = new ArrayList<>();
    
    /**
     * Retorna a quantidade de vendedores. O valor retornado representa a quantidade
     * de vendedores entre todos os arquivos processados.
     * Para obter o resultado desejado, todos os arquivos devem ser previamente processados
     * e seus dados devem estar persistidos na classe <b>Dados</b>.
     * @return Inteiro contendo a quantidade de vendedores.
     */
    protected int getQuantidadeDeVendedores() {
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
    protected int getQuantidadeDeClientes() {
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
    protected String getIdDaVendaMaisCara() {
        maior = new BigDecimal("0");
        resultado = "err";
        Dados.getVendas().forEach((file, vendas) -> {
            vendas.forEach(venda -> {
                valor = getValorTotalDaVenda(venda.getItems());
                if (valor.compareTo(maior) > 0) {
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
    protected String getPiorVendedor() {
        primeiro = true;
        resultado = "err";
        menor = new BigDecimal("0");
        Dados.getVendas().forEach((file, vendas) -> {
            vendas.forEach(venda -> {
                valor = getValorTotalDaVenda(venda.getItems());
                if (primeiro == true || valor.compareTo(menor) < 0) {
                    menor = valor;
                    resultado = venda.getVendedor();
                    primeiro = false;
                }
            });
        });
        return resultado;
    }
}
