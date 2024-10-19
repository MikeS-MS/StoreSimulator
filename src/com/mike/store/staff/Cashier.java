package com.mike.store.staff;

import com.mike.store.Printable;

import java.math.BigDecimal;

public class Cashier implements Printable {
    private String FirstName;
    private String LastName;
    private final String ID;
    private BigDecimal BaseSalary;
    private boolean IsWorking;

    public Cashier(String firstName, String lastName, String id, BigDecimal baseSalary) {
        FirstName = firstName;
        LastName = lastName;
        this.ID = id;
        BaseSalary = baseSalary;
        this.IsWorking = false;
    }

    public BigDecimal GetSalary(){
        return this.BaseSalary;
    }

    public void SetIsWorking(boolean isWorking){
        this.IsWorking = isWorking;
    }

    public String toString(){
        return (this.FirstName + " " + this.LastName + ", Employee ID: " + this.ID);
    }
}
