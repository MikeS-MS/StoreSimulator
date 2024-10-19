package com.mike.store.storage;

import com.mike.store.Store;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Storage {
    public Map<String, List<Item>> Items;
    public Map<String, List<Item>> SoldItems;
    public BigDecimal CloseToExpireSale;
    public final Store Store;

    public Storage(BigDecimal closeToExpireSale, Store store){
        this.CloseToExpireSale = closeToExpireSale;
        this.Items = new HashMap<>();
        this.SoldItems = new HashMap<>();
        this.Items = new HashMap<>();
        this.Store = store;
    }

    public void AddItem(String itemName, String id, String name, BigDecimal priceWithoutIncrease, Store.Category category, LocalDate expiryDate){
        Item item = new Item(id, name, itemName, priceWithoutIncrease, category, expiryDate, this);

        if (this.Items.get(itemName) == null) {
            List<Item> ListOfItem = new ArrayList<Item>();
            ListOfItem.add(item);
            this.Items.put(itemName, ListOfItem);
        }
        else
            this.Items.get(itemName).add(item);
    }

    public void MoveItemToSold(Item item){
        String name = item.GetCategoryName();

        if (this.SoldItems.get(name) == null) {
            List<Item> ListOfItem = new ArrayList<Item>();
            ListOfItem.add(item);
            this.SoldItems.put(name, ListOfItem);
        }
        else
            this.SoldItems.get(name).add(item);

        this.RemoveItem(item);
    }

    public void RemoveItem(Item item){
        String name = item.GetCategoryName();

        if (this.Items != null)
            if(this.Items.get(name) != null)
                this.Items.get(name).remove(item);
        if(this.Items.get(name).size() < 1)
            this.Items.remove(name);
    }
}