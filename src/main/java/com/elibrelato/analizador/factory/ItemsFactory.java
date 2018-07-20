package com.elibrelato.analizador.factory;

import com.elibrelato.analizador.config.Config;
import com.elibrelato.analizador.entity.Item;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author elibrelato@gmail.com
 */
public class ItemsFactory {
    
    public List<Item> getItems(String dados) {
        List<Item> itemsList = new ArrayList<>();
        String[] nodes, items = dados.replace(Config.ITEM_BEGINS, "").replace(Config.ITEM_ENDS, "").split(Config.ITEM_ITEMS_SPLIT);
        String node, value;
        Item item;
        
        for (String unparsedItem : items) {
            item = new Item();
            nodes = unparsedItem.split(Config.ITEM_SPLIT);
            for (int i1 = 0; i1 < nodes.length; i1++) {
                node = Config.ITEM_NODES.get(i1);
                value = nodes[i1];
                if (node != null) { // Safety
                    switch (node) {
                        case Config.ITEM_STRING_ITEM_ID:
                            item.setId(value);
                            break;
                        case Config.ITEM_STRING_ITEM_PRICE:
                            try {
                                item.setPrice(Double.parseDouble(value));
                            } catch (NumberFormatException e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        case Config.ITEM_STRING_ITEM_QUANTITY:
                            try {
                                item.setQuantity(Integer.parseInt(value));
                            } catch (NumberFormatException e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                    }
                }
            }
            itemsList.add(item);
        }
        return itemsList; 
    }
}
