package com.elibrelato.analizador.factory;

import com.elibrelato.analizador.config.Config;
import com.elibrelato.analizador.entity.Venda;
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
public class VendaFactoryTest {
    
    public VendaFactoryTest() {
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
     * Test of getVenda method, of class VendaFactory.
     */
    @Test
    public void testGetVenda() {
        String dados = "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo";
        Venda venda = new VendaFactory().getVenda(dados);
        assertEquals("08", venda.getId());
        assertEquals("Paulo", venda.getVendedor());
        assertEquals(3, venda.getItems().size());
    }   
}
