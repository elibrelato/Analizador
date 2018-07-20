package com.elibrelato.analizador;

import com.elibrelato.analizador.dados.Dados;
import com.elibrelato.analizador.parser.Parser;
import com.elibrelato.analizador.relatorio.Relatorio;
import com.elibrelato.analizador.config.Config;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author elibrelato@gmail.com
 */
public class App {

    private Config config = new Config();
    private Parser parser = new Parser();
    private Relatorio relatorio;
    private Collection<File> arquivos;
    
    // Configs
    private static File inputFolder;
    private static File outputFolder;
    private static String[] ext = new String[1];
    private static String charset;
    private static String modeloDoRelatorio;

    public void start() throws Exception {

        System.out.println();
        System.out.println("Iniciando....");
        System.out.print("Carregando configuracoes...");
        
        config.processa_Config(); // processa o arquivo config.txt
        setConfigs(); // carrega as configurações 
        
        System.out.println(" Concluido!");
        System.out.println("Pasta de trabalho: " + inputFolder.getCanonicalPath());
        System.out.println("Pasta de saida   : " + outputFolder.getCanonicalPath());
        System.out.println("Extensao dos arquivos a serem processados: ." + ext[0]);
        System.out.println("Charset do arquivo de saida: " + (charset == null ? "Default" : charset));
        System.out.println("Modelo do Relatorio: " + modeloDoRelatorio);
        System.out.println("Executando...");
        
        Map<String, File> arquivosIn = new HashMap<>();
        Collection<String> arquivosOut = new ArrayList<>();
        Collection<File> arquivosProcessados = new ArrayList<>();
        boolean gerarRelatorio;
        
        while (true) {
            
            if (!inputFolder.exists()) {
                FileUtils.forceMkdir(inputFolder);
            }

            if (!outputFolder.exists()) {
                FileUtils.forceMkdir(outputFolder);
            }           
            
            // Posso implemetar isso de 2 maneiras pois não ficou bem claro o modelo do relatório
            // se o relatorio é por arquivo processado ou apenas 1 relatorio geral.
            // - Para um modelo relatório individual, devo comparar constantemente
            // o conteúdo da pasta In com o conteudo da pasta Out.
            //
            // - Para um relatório geral, devo comparar os arquivos da pasta In com
            // a lista de arquivos já processados.
  
            //
            // RELATÓRIO INDIVIDUAL
            //
            if (modeloDoRelatorio.equalsIgnoreCase("individual")) { 
                // *** Nesse caso arquivosIn é um Map dos nomes e arquivos da pasta In
                // *** e arquivosOut é a lista dos arquivos na pasta Out.
                arquivosIn.clear();
                arquivosOut.clear();
                arquivos = FileUtils.listFiles(inputFolder, ext, false);
                arquivos.forEach(arquivo -> {
                    arquivosIn.put(FilenameUtils.getBaseName(arquivo.getName()), arquivo);
                });
                
                arquivos = FileUtils.listFiles(outputFolder, ext, false);
                arquivos.forEach(arquivo -> {
                    arquivosOut.add(FilenameUtils.getBaseName(FilenameUtils.getBaseName(arquivo.getName())));
                });

                // Cria a lista de arquivos que estao apenas na pasta in.
                arquivos.clear();
                arquivosIn.forEach((nome, file) -> {
                   if (!arquivosOut.contains(nome))
                       arquivos.add(file);
                });     

                if (arquivos.size() > 0) {
                    relatorio = new Relatorio(parser.parse(arquivos));
                    relatorio.gerarRelatorio(outputFolder, charset);
                    Dados.clear();
                }
            }
            
            //
            // RELATORIO GERAL
            //          
            else if (modeloDoRelatorio.equalsIgnoreCase("geral")) {
                // *** Nesse caso arquivosProcessados é a lista dos arquivos que já foram processados.
                // *** Não preciso do Map arquivosIn, então utilizei o Collection arquivos<File>
                // *** para os arquivos da pasta In.               
                gerarRelatorio = false;
                arquivos = FileUtils.listFiles(inputFolder, ext, false);
                
                // Verifica arquivos deletados na pasta In. Esses arquivos podem ainda estar sendo deletados...
                if (!arquivos.isEmpty() && arquivos.size() < arquivosProcessados.size()) {
                    do {
                        arquivosProcessados.clear();
                        arquivosProcessados.addAll(arquivos);
                        
                        // pausa, pois o conteudo da pasta In pode estar sendo apagado.
                        try {Thread.sleep(5000);} catch (InterruptedException ex) {}
                        
                        // Cria uma nova lista de arquivos da pasta in
                        arquivos = FileUtils.listFiles(inputFolder, ext, false);
                        gerarRelatorio = true;
                    } while (!arquivos.isEmpty() && arquivos.size() < arquivosProcessados.size());
                }
                
                if (arquivos.isEmpty()) {
                    arquivosProcessados.clear();
                }
                
                if (!arquivos.isEmpty() && arquivos.size() != arquivosProcessados.size()) {
                    gerarRelatorio = true;
                }
                
                if (gerarRelatorio == true) {
                    arquivosProcessados = parser.parse(arquivos);
                    relatorio = new Relatorio(arquivosProcessados);
                    relatorio.gerarRelatorioGeral(outputFolder, charset);
                    Dados.clear();
                }
            }

            try {Thread.sleep(5000);} catch (InterruptedException ex) {} // pausa de 5 segundos. Previne que o app consuma muitos recuros do sistema.
        }
    }
    
    /**
     * Define as configurações.
     * Utiliza os valores que foram carregados do arquivo config.txt. 
     * Caso algum valor nao esteja configurado, sera definido um valor default.
     */
    private void setConfigs() {
        inputFolder = new File((config.getInputFolder() == null
                ? System.getProperty("user.home") + File.pathSeparator + "data" + File.pathSeparator + "in" // dafault 
                : config.getInputFolder()));
 
        outputFolder = new File((config.getOutputFolder() == null
                ? System.getProperty("user.home") + File.pathSeparator + "data" + File.pathSeparator + "out"  // dafault
                : config.getOutputFolder()));

        ext[0] = (config.getExtension() == null
                ? "dat"
                : config.getExtension().replace(".", ""));

        charset = config.getCharset();
        
        modeloDoRelatorio = (config.getModeloDoRelatorio() == null
                ? "individual"
                : config.getModeloDoRelatorio());
    }
    
    public static String getExtension() {
        return ext[0];
    }
}
