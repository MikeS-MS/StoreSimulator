package com.mike.store;

import com.mike.store.staff.Cashier;
import com.mike.store.storage.Item;
import com.mike.store.storage.Storage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Store {
    public enum Category {
        Non_Foods,
        Foods
    }

    public final BigDecimal NonFoodsMarkup;
    public final BigDecimal FoodsMarkup;
    public int DaysBeforeExpiryCheck;
    public Storage storage;
    public List<CashRegister> CashRegisters;
    private List<Cashier> Cashiers;
    public Map<Integer, String> Receipts;
    private String Name;
    private final String StoreKind;
    private final String ID;
    public int CurrentReceiptID;

    public Store(String name, String kind, String id, BigDecimal nonFoodsMarkup, BigDecimal foodsMarkup, int daysBeforeExpiryCheck, BigDecimal closeToExpireSale){
        this.Name = name;
        this.StoreKind = kind;
        this.NonFoodsMarkup = nonFoodsMarkup;
        this.FoodsMarkup = foodsMarkup;
        this.DaysBeforeExpiryCheck = daysBeforeExpiryCheck;
        this.CashRegisters = new ArrayList<>();
        this.Cashiers = new ArrayList<>();
        this.storage = new Storage(closeToExpireSale, this);
        this.Receipts = new HashMap<>();
        this.ID = id;
    }

    public void AddCashier(Cashier cashier){
        boolean CanAdd = true;

        for (Cashier current : this.Cashiers){
            if (current == cashier){
                CanAdd = false;
                break;
            }
        }

        if (CanAdd)
            this.Cashiers.add(cashier);
    }

    public Cashier GetCashier(int id){
        if (id < this.Cashiers.size()){
            return this.Cashiers.get(id);
        }

        return new Cashier("Temp", "Temp", Integer.toString(0), BigDecimal.ZERO);
    }

    public void AddCashRegisters(CashRegister cashRegister){
        this.CashRegisters.add(cashRegister);
    }

    public void AddReceipt(int id, String receipt){
        this.Receipts.put(id, receipt);
    }

    public int GetNumOfReceipts(){
        return this.Receipts.size();
    }

    public BigDecimal GetProfit(){
        BigDecimal ItemCosts = this.GetSoldItemsCombinedCost();
        BigDecimal ItemMarkup = this.GetSoldItemsCombinedPrice();
        BigDecimal Salaries = this.GetCombinedSalary();
        return ItemMarkup.subtract(ItemCosts).subtract(Salaries);
    }

    public BigDecimal GetSoldItemsCombinedPrice(){
        BigDecimal price = BigDecimal.ZERO;

        for (Map.Entry<String, List<Item>> entry : this.storage.SoldItems.entrySet()) {
            for(Item item : this.storage.SoldItems.get(entry.getKey())){
                price = price.add(item.GetCalculatedPrice());
            }
        }

        return price;
    }

    public BigDecimal GetSoldItemsCombinedCost(){
        BigDecimal price = BigDecimal.ZERO;

        for (Map.Entry<String, List<Item>> entry : this.storage.SoldItems.entrySet()) {
            for(Item item : this.storage.SoldItems.get(entry.getKey())){
                price = price.add(item.GetBasePrice());
            }
        }

        return price;
    }

    public BigDecimal GetCombinedSalary(){
        BigDecimal combinedSalary = BigDecimal.ZERO;

        for(Cashier cashier : this.Cashiers){
            combinedSalary = combinedSalary.add(cashier.GetSalary());
        }

        return combinedSalary;
    }

    public String toString(){
        return (this.Name + ", which is a " + this.StoreKind + ".");
    }

    public String Path(){
        String temp = this.StoreKind;
        temp = temp.replace(' ', '_');
        return (this.Name + "_" + temp + "_" + this.ID);
    }
}
