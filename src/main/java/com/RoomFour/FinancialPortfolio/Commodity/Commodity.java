package com.RoomFour.FinancialPortfolio.Commodity;

public class Commodity {
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
}
