/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elibrelato.analizador.factory;

import com.elibrelato.analizador.config.Config;
import com.elibrelato.analizador.entity.Item;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
public class ItemsFactoryTest {
    
    public ItemsFactoryTest() {
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
     * Test of getItems method, of class ItemsFactory.
     */
    @Test
    public void testGetItems() {
        String dados = "[1-34-10,2-33-1.50,3-40-0.10]";
        List<Item> expResult = getItems();
        List<Item> items = new ItemsFactory().getItems(dados);
        assertEquals(3, items.size());
        for (int i1 = 0; i1 < items.size(); i1++) {
            assertEquals(expResult.get(i1).getId(), items.get(i1).getId());
            assertEquals(expResult.get(i1).getQuantity(), items.get(i1).getQuantity());
            assertEquals(expResult.get(i1).getPrice(), items.get(i1).getPrice());
        }
    }
    
    private List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        items.add(getItem("1", 34, new BigDecimal("10")));
        items.add(getItem("2", 33, new BigDecimal("1.50")));
        items.add(getItem("3", 40, new BigDecimal("0.10")));
        return items;
    }
    
    private Item getItem(String id, int quantity, BigDecimal value) {
        Item item = new Item();
        item.setId(id);
        item.setQuantity(quantity);
        item.setPrice(value);
        return item;    
    }
}
