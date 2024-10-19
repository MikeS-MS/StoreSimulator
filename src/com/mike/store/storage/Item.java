package com.mike.store.storage;

import com.mike.store.Printable;
import com.mike.store.Store;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Item implements Printable {
    private final String ID;
    private final String Name;
    private final String CategoryName;
    private BigDecimal PriceWithoutIncrease;
    public final Store.Category Category;
    private final LocalDate ExpiryDate;
    private final Storage StorageItemIsStoredIn;

    public Item(String id, String name, String categoryName, BigDecimal priceWithoutIncrease, Store.Category category, LocalDate expiryDate, Storage storageItemIsStoredIn) {
        this.ID = id;
        this.Name = name;
        this.PriceWithoutIncrease = priceWithoutIncrease;
        this.Category = category;
        this.ExpiryDate = expiryDate;
        this.StorageItemIsStoredIn = storageItemIsStoredIn;
        this.CategoryName = categoryName;
    }

    public BigDecimal GetCalculatedPrice() {
        BigDecimal temp = this.PriceWithoutIncrease;
        temp = temp.add(temp.multiply(this.Category.compareTo(Store.Category.Foods) == 0 ? this.StorageItemIsStoredIn.Store.FoodsMarkup : this.StorageItemIsStoredIn.Store.NonFoodsMarkup));

        if (LocalDate.now().isBefore(this.ExpiryDate))
            if(LocalDate.now().getDayOfMonth() >= this.ExpiryDate.getDayOfMonth() - this.StorageItemIsStoredIn.Store.DaysBeforeExpiryCheck)
                temp = temp.subtract(temp.multiply(this.StorageItemIsStoredIn.CloseToExpireSale));

        return temp;
    }

    public BigDecimal GetBasePrice(){
        return this.PriceWithoutIncrease;
    }

    public String GetCategoryName(){
        return this.CategoryName;
    }

    public String GetID(){
        return this.ID;
    }

    public boolean IsExpired(){
        return !(LocalDate.now().isBefore(this.ExpiryDate));
    }

    @Override
    public String toString(){

        return (this.Name + " " + this.CategoryName + (this.Category.compareTo(Store.Category.Foods) == 0 ? ", Expiry Date: " + this.ExpiryDate.toString() : "") + ", Price: " + this.GetCalculatedPrice() + ", Unique ID: " + this.ID);
    }
}
