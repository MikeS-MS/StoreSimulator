package com.mike.store;

import com.mike.store.storage.Item;

import java.math.BigDecimal;
import java.util.*;

public class Buyer {
    public List<Item> Basket;


    public BigDecimal Money;

    public Buyer(){
        this.Basket = new ArrayList<>();
        this.Money = BigDecimal.ZERO;
    }

    public Buyer(BigDecimal money){
        this.Basket = new ArrayList<>();
        this.Money = money;
    }

    public boolean AddItemToBasket(Item item){
        if (!this.Basket.contains(item)){
         this.Basket.add(item);
         return true;
        }
        return false;
    }

    public void RemoveItemFromBasket(Item item){
        this.Basket.remove(item);
    }
}
