/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elibrelato.analizador.factory;

import com.elibrelato.analizador.config.Config;
import com.elibrelato.analizador.entity.Vendedor;
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
public class VendedorFactoryTest {
    
    public VendedorFactoryTest() {
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
     * Test of getVendedor method, of class VendedorFactory.
     */
    @Test
    public void testGetVendedor() {
        String dados = "001ç3245678865434çPauloç40000.99";
        double salary = 40000.99d;
        VendedorFactory instance = new VendedorFactory();
        Vendedor result = instance.getVendedor(dados);
        assertEquals("Paulo", result.getName());
        assertEquals("3245678865434", result.getCpf());
        assertTrue(result.getSalary() == salary);
    }  
}
