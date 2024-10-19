package com.mike.store;

import com.mike.store.staff.Cashier;
import com.mike.store.storage.Item;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class CashRegister implements Runnable, Printable {
    public Thread Register;
    private Cashier CurrentCashier;
    private Buyer CurrentBuyer;
    private List<Item> MarkedItems;
    public List<Item> ExpiredItems;
    private int ID;
    public boolean ExpiredProductsNotification;

    public CashRegister(int id){
        this.Setup(null, id);
        this.Register = new Thread(this, ("Register_" + this.ID));
    }

    public CashRegister(Cashier cashier, int id) {
        this.Setup(cashier, id);
        this.Register = new Thread(this, ("Register_" + this.ID));
    }

    private void Setup(Cashier cashier, int id){
        this.SetCashier(cashier);
        this.ID = id;
        this.CurrentBuyer = null;
        this.MarkedItems = new ArrayList<>();
        this.ExpiredProductsNotification = false;
        this.ExpiredItems = new ArrayList<>();
    }

    public void SetBuyer(Buyer buyer){
        this.CurrentBuyer = buyer;
    }

    @Override
    public void run() {
        if (this.CurrentBuyer != null)
            this.MarkProducts(this.CurrentBuyer);
    }

    public void SetCashier(Cashier cashier){
        if(cashier != null)
            if (this.CurrentCashier == null) {
                this.CurrentCashier = cashier;
                this.CurrentCashier.SetIsWorking(true);
            }

    }

    public void RemoveCashier() {
        this.CurrentCashier.SetIsWorking(false);
        this.CurrentCashier = null;
    }

    public void ResetExpiredNotification(){
        this.ExpiredProductsNotification = false;
    }

    public void AddExpiredProduct(Item item){
        this.ExpiredItems.add(item);
    }

    private void MarkProducts(Buyer buyer){
        this.ExpiredItems.clear();
        this.MarkedItems.clear();

        for (Item item : buyer.Basket){
            if(item.Category.compareTo(Store.Category.Foods) == 0) {
                if (item.IsExpired()) {
                    this.AddExpiredProduct(item);
                    this.ExpiredProductsNotification = true;
                }
                else
                    this.MarkedItems.add(item);
            }
            else {
                    this.MarkedItems.add(item);
            }

        }
    }

    public String CheckoutItems(Store store){
        BigDecimal combinedprice = this.GetCombinedPrice();
        StringBuilder status = new StringBuilder();

        if (this.CurrentBuyer.Money.compareTo(combinedprice) >= 0){
            status.append("Checkout Complete!\n");
            String receipt = this.MakeReceipt(store);
            status.append(receipt);
            String firstPath = "receipts";
            String secondPath = store.Path();
            String fileNamePath = Integer.toString(store.CurrentReceiptID);
            File dir1 = new File(firstPath);
            File dir2 = new File(firstPath + "/" + secondPath);
            File file = new File(firstPath + "/" + secondPath + "/" + fileNamePath);

            dir1.mkdir();
            dir2.mkdir();

            try {
                FileWriter writer = new FileWriter(file);
                writer.write(receipt);
                writer.close();
            }
            catch (IOException ioe) {
                System.out.flush();
                System.out.println(ioe.getLocalizedMessage());
            }

            store.AddReceipt(store.CurrentReceiptID, receipt);
            store.CurrentReceiptID++;

            this.CurrentBuyer.Money = this.CurrentBuyer.Money.subtract(combinedprice);

            for (Item item : this.MarkedItems){
                store.storage.MoveItemToSold(item);
            }
        }
        else{
            status.append("Checkout Failed! Not Enough money.\n");
        }

        this.MarkedItems.clear();
        this.CurrentBuyer.Basket.clear();
        this.CurrentBuyer = null;

        return status.toString();
    }

    private BigDecimal GetCombinedPrice(){
        BigDecimal temp = BigDecimal.ZERO;

        for (Item item : this.MarkedItems){
            temp = temp.add(item.GetCalculatedPrice());
        }

        return temp;
    }

    private String MakeReceipt(Store store){
        StringBuilder temp = new StringBuilder("Receipt №: " + store.CurrentReceiptID + "\n");

        for (int i = 0; i < this.MarkedItems.size(); i++){
            temp.append(("\n" + (i+1) + ". " + this.MarkedItems.get(i).toString()));
        }

        temp.append("\n\nThank you for shopping with us today!");

        return temp.toString();
    }

    public int GetID(){
        return this.ID;
    }

    public Cashier GetCashier(){
        return this.CurrentCashier;
    }

    @Override
    public String toString(){
        return ("CashRegister №" + (this.ID) + ". " + this.CurrentCashier.toString());
    }
}
