package com.elibrelato.analizador.factory;

import com.elibrelato.analizador.config.Config;
import com.elibrelato.analizador.entity.Cliente;
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
public class ClienteFactoryTest {
    
    public ClienteFactoryTest() {
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
     * Test of getCliente method, of class ClienteFactory.
     */
    @Test
    public void testGetCliente() {
        String dados = "002ç2345675434544345çJose da SilvaçRural";
        Cliente cliente = new ClienteFactory().getCliente(dados);
        assertEquals("Jose da Silva", cliente.getName());
        assertEquals("2345675434544345", cliente.getCnpj());
        assertEquals("Rural", cliente.getBusinessArea());
    }  
}
