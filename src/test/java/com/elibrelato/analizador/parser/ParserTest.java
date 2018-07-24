package com.elibrelato.analizador.parser;

import com.elibrelato.analizador.config.Config;
import com.elibrelato.analizador.dados.Dados;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elibrelato@gmail.com
 */
public class ParserTest {
    
    public ParserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        Config config = new Config();
        config.processa_Config();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of parse method, of class Parser.
     */
    @Test
    public void testParse_Collection() {
        Collection<File> listaDeArquivos = new ArrayList<>();
        File file1 = getTemporaryFile();
        File file2 = getTemporaryFile2();
        listaDeArquivos.add(file1);
        listaDeArquivos.add(file2);
        Collection<File> result = new Parser().parse(listaDeArquivos);
        FileUtils.deleteQuietly(file1);
        FileUtils.deleteQuietly(file2);
        assertEquals(2, result.size());
        assertTrue(result.contains(file1));
        assertTrue(result.contains(file2));
        asserts1(file1);
        asserts2(file2);
    }

    /**
     * Test of parse method, of class Parser.
     */
    @Test
    public void testParse_File() {
        File file = getTemporaryFile();
        new Parser().parse(file);
        FileUtils.deleteQuietly(file); 
        asserts1(file);       
    }
    
    private void asserts1(File file) {
        assertEquals("Pedro", Dados.getVendedores().get(file).get(0).getName());
        assertEquals("1234567891234", Dados.getVendedores().get(file).get(0).getCpf());        
        assertEquals(new BigDecimal("50000"), Dados.getVendedores().get(file).get(0).getSalary());
        assertEquals("Paulo", Dados.getVendedores().get(file).get(1).getName());
        assertEquals("3245678865434", Dados.getVendedores().get(file).get(1).getCpf()); 
        assertEquals(new BigDecimal("40000.99"), Dados.getVendedores().get(file).get(1).getSalary());
        
        assertEquals("Jose da Silva", Dados.getClientes().get(file).get(0).getName());
        assertEquals("2345675434544345", Dados.getClientes().get(file).get(0).getCnpj());
        assertEquals("Rural", Dados.getClientes().get(file).get(0).getBusinessArea());
        assertEquals("Eduardo Pereira", Dados.getClientes().get(file).get(1).getName());
        assertEquals("2345675433444345", Dados.getClientes().get(file).get(1).getCnpj());
        assertEquals("Rural", Dados.getClientes().get(file).get(1).getBusinessArea());
        
        assertEquals("Pedro", Dados.getVendas().get(file).get(0).getVendedor());
        assertEquals("10", Dados.getVendas().get(file).get(0).getId());
        assertEquals(3, Dados.getVendas().get(file).get(0).getItems().size());
        assertEquals("Paulo", Dados.getVendas().get(file).get(1).getVendedor());
        assertEquals("08", Dados.getVendas().get(file).get(1).getId());
        assertEquals(3, Dados.getVendas().get(file).get(1).getItems().size());
    }
    
    private void asserts2(File file) {
        assertEquals("Maria de Oliveira", Dados.getVendedores().get(file).get(0).getName());
        assertEquals("9876543211234", Dados.getVendedores().get(file).get(0).getCpf());        
        assertEquals(new BigDecimal("456.78"), Dados.getVendedores().get(file).get(0).getSalary());
        
        assertEquals("Jose da Silva", Dados.getClientes().get(file).get(0).getName());
        assertEquals("2345675434544345", Dados.getClientes().get(file).get(0).getCnpj());
        assertEquals("Rural", Dados.getClientes().get(file).get(0).getBusinessArea());
        
        assertEquals("Maria de Oliveira", Dados.getVendas().get(file).get(0).getVendedor());
        assertEquals("355", Dados.getVendas().get(file).get(0).getId());
        assertEquals(2, Dados.getVendas().get(file).get(0).getItems().size());      
    }
    
    private File getTemporaryFile() {       
        Collection<String> dados = new ArrayList<>();
        File file = new File(FileUtils.getTempDirectoryPath() + File.separatorChar + "teste1.tmp");
        dados.add("001ç1234567891234çPedroç50000");
        dados.add("001ç3245678865434çPauloç40000.99");
        dados.add("002ç2345675434544345çJose da SilvaçRural");
        dados.add("002ç2345675433444345çEduardo PereiraçRural");
        dados.add("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro");
        dados.add("003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo");           
        try {
            FileUtils.writeLines(file, null, dados, null);
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return file;
    }
    
    private File getTemporaryFile2() {       
        Collection<String> dados = new ArrayList<>();
        File file = new File(FileUtils.getTempDirectoryPath() + File.separatorChar + "teste2.tmp");
        dados.add("001ç9876543211234çMaria de Oliveiraç456.78");
        dados.add("002ç2345675434544345çJose da SilvaçRural");
        dados.add("003ç355ç[1-8-231.11,2-3-2.50]çMaria de Oliveira");
         
        try {
            FileUtils.writeLines(file, null, dados, null);
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return file;
    }
}
