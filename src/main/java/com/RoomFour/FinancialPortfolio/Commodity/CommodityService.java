package com.RoomFour.FinancialPortfolio.Commodity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
@Service
public class CommodityService {
    private CommodityRepository commodityRepository;
    private Map<String, Double> priceMap;
    @Autowired
    public CommodityService(CommodityRepository commodityRepository) {
        this.commodityRepository = commodityRepository;
        JSONArray jsonArray = new JSONArray("<placeholder>");
        priceMap = new HashMap<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject stockObject = jsonArray.getJSONObject(i);
            String symbol = stockObject.getString("Symbol");
            double currentPrice = stockObject.getDouble("currentPrice");
            priceMap.put(symbol, currentPrice);
        }
    }

    public Commodity addCommodity(Commodity c){
        // add validation steps and return null if fails
        if (!c.getTicker().matches("[A-Z]*")) return null;
        if(c.getPricePerUnit() == 0) return null;
        if(c.getQty() == 0) return null;
        c.setBuyDate(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        return commodityRepository.save(c);
    }

    public Commodity updateCommodity(long id, Commodity c){
        Commodity c_ = commodityRepository.findById(id).orElseGet(()->null);
        if (c_ != null) return c_.set(c);
        return null;
    }

    public Commodity deleteCommodity(long id){
        Commodity c_ = commodityRepository.findById(id).orElseGet(()->null);
        if (c_ != null) {
            c_.setSellDate(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
            c_.setProfit((c_.getPricePerUnit() * c_.getQty()) - (priceMap.get(c_.getTicker()) * c_.getQty()));
        }
        return c_;
    }
    public List<Commodity> getAll() {
        return commodityRepository.findAll();
    }

    public List<Commodity> getByTicker(String ticker) {

        return commodityRepository.findByTicker(ticker);
    }


    public List<Commodity> bulkAdd(List<Commodity> cList){
        return (List<Commodity>)commodityRepository.saveAll(cList);
    }


    //total investment calculation
    public double calculateTotalInvestment(String ticker) {
        List<Commodity> stocks = commodityRepository.findByTicker(ticker);
        double totalInvestment=0;
        for (Commodity stock : stocks) {
            totalInvestment += stock.getPricePerUnit() * stock.getQty();
        }
        return totalInvestment;
    }
    public Map<String, Double> getTotalInvestmentForAllTickerSymbols() {
        List<Commodity> stocks = commodityRepository.findAll();
        Map<String, Double> totalInvestmentMap = new HashMap<>();
        for (Commodity stock : stocks) {
            totalInvestmentMap.put(stock.getTicker(),calculateTotalInvestment(stock.getTicker()));
        }
        return totalInvestmentMap;
    }


}
