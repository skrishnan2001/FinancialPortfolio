package com.RoomFour.FinancialPortfolio.Commodity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Commodity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String ticker;
    private Date buyDate;
    private Date sellDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public Date getSellDate() {
        return sellDate;
    }

    public void setSellDate(Date sellDate) {
        this.sellDate = sellDate;
    }

    private double pricePerUnit;
    private int qty;

    public Commodity() {}

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
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

    public Commodity(long id, String ticker, Date buyDate, Date sellDate, double pricePerUnit, int qty) {
        this.id = id;
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
