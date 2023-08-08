package com.RoomFour.FinancialPortfolio.Commodity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Commodity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String ticker, buyDate, sellDate;
    private double pricePerUnit;
    private int qty;

    public Commodity() {}

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public String getSellDate() {
        return sellDate;
    }

    public void setSellDate(String sellDate) {
        this.sellDate = sellDate;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Commodity(String ticker, String buyDate, String sellDate, double pricePerUnit, int qty) {
        this.ticker = ticker;
        this.buyDate = buyDate;
        this.sellDate = sellDate;
        this.pricePerUnit = pricePerUnit;
        this.qty = qty;
    }

    public Commodity set(Commodity c){
        if(c.getTicker() != null) this.setTicker(c.getTicker());
        if(c.getQty() != 0) this.setQty(c.getQty());
        if(c.getBuyDate() != null) this.setBuyDate(c.getBuyDate());
        if(c.getSellDate() != null) this.setSellDate(c.getSellDate());
        if(c.getPricePerUnit() != 0) this.setPricePerUnit(c.getPricePerUnit());
        return this;
    }

}
