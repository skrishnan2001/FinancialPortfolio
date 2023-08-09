package com.RoomFour.FinancialPortfolio.Commodity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CommodityService {
    private CommodityRepository commodityRepository;
    private Map<String, Double> priceMap;

    @Autowired
    public CommodityService(CommodityRepository commodityRepository) throws IOException {
        this.commodityRepository = commodityRepository;
        BufferedReader r = new BufferedReader(new FileReader("C:\\Users\\Administrator\\Documents\\Neueda\\Spring\\FinancialPortfolio\\src\\main\\java\\com\\RoomFour\\FinancialPortfolio\\Data\\CurrentPrice.json"));
        StringBuilder s = new StringBuilder();
        String line;
        while((line = r.readLine()) != null) {
            s.append(line);
        }

        JSONArray jsonArray = new JSONArray(s.toString());
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
        if (c_ != null && (c_.getSellDate().isBlank() || c_.getSellDate().isEmpty())) {
            c_.setSellDate(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
            c_.setProfit(-(c_.getPricePerUnit() * c_.getQty()) + (priceMap.getOrDefault(c_.getTicker(), 0.0) * c_.getQty()));
            commodityRepository.save(c_);
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
    public Map<String, Double> getTotalInvestmentByTicker() {
        List<Commodity> stocks = commodityRepository.findAll();
        Map<String, Double> totalInvestmentMap = new HashMap<>();
        for (Commodity stock : stocks) {
            totalInvestmentMap.put(stock.getTicker(),calculateTotalInvestment(stock.getTicker()));
        }
        return totalInvestmentMap;
    }

    public double getCurrentPosition(){
        double currentPosition = 0;
        List<Commodity> cList = commodityRepository.findAll();
        for (Commodity s : cList){
            currentPosition += (priceMap.getOrDefault(s.getTicker(), 0.0) * s.getQty()) - (s.getPricePerUnit() * s.getQty());
        }
        return currentPosition;
    }

    public Map<String, Double> getProfitsByTicker() {
        List<Commodity> allStocks = commodityRepository.findAll();
        Map<String, Double> totalProfitsMap = new HashMap<>();
        for (Commodity stock : allStocks) {
            if(!stock.getSellDate().isEmpty() ||!stock.getSellDate().isBlank()) {
                String ticker = stock.getTicker();
                totalProfitsMap.put(ticker, totalProfitsMap.getOrDefault(ticker, 0.0) + stock.getProfit());
            }
        }
        return totalProfitsMap;
    }

    public Map<String, Double> getPotentialProfitsByTicker() {
        List<Commodity> allStocks = commodityRepository.findAll();
        Map<String, Double> totalPotentialProfitsMap = new HashMap<>();
        for (Commodity stock : allStocks) {
            if(stock.getSellDate().isEmpty() || stock.getSellDate().isBlank()) {
                String ticker = stock.getTicker();
                double potentialProfit = totalPotentialProfitsMap.getOrDefault(ticker, 0.0) + (priceMap.getOrDefault(ticker, 0.0) - stock.getPricePerUnit()) * stock.getQty();
                totalPotentialProfitsMap.put(ticker, potentialProfit);
            }
        }
        return totalPotentialProfitsMap;
    }

    public double getTotalInvestment() {
        double totalInv = 0;
        List<Commodity> stocks = commodityRepository.findAll();
        for (Commodity stock : stocks) {
            totalInv += stock.getPricePerUnit() * stock.getQty();
        }
        return totalInv;
    }

    public double getTotalProfits() {
        List<Commodity> allStocks = commodityRepository.findAll();
        double totalProfit=0;
       // Map<String, Double> totalProfitsMap = new HashMap<>();
        for (Commodity stock : allStocks) {
            if(!stock.getSellDate().isEmpty() ||!stock.getSellDate().isBlank()) {
                totalProfit=stock.getProfit();
            }
        }
        return totalProfit;
    }

    public List<Pair> getTopK(int k) {
        List<Commodity> cList = commodityRepository.findAll();

        List<Pair> top = new ArrayList<>();
        Map<String, Double> potentialProfitsMap = getPotentialProfitsByTicker();

        for(Commodity stock : cList) {
            if(stock.getSellDate().isEmpty() || stock.getSellDate().isBlank()) {
                top.add(new Pair(stock, potentialProfitsMap.get(stock.getTicker())));
            }
        }

        Collections.sort(top, (a, b) -> {
            return Double.compare(b.potentialProfits, a.potentialProfits);
        });

        List<Pair> topK = new ArrayList<>();
        for(int i = 0; i < k; i++)
            topK.add(top.get(i));

        return topK;
    }

    public List<Pair> getWorstK(int k) {
        List<Commodity> cList = commodityRepository.findAll();

        List<Pair> worst = new ArrayList<>();
        Map<String, Double> potentialProfitsMap = getPotentialProfitsByTicker();

        for(Commodity stock : cList) {
            if(stock.getSellDate().isEmpty() || stock.getSellDate().isBlank()) {
                worst.add(new Pair(stock, potentialProfitsMap.get(stock.getTicker())));
            }

        }

        Collections.sort(worst, (a, b) -> {
            return Double.compare(a.potentialProfits, b.potentialProfits);
        });

        List<Pair> worstK = new ArrayList<>();
        for(int i = 0; i < k; i++)
            worstK.add(worst.get(i));

        return worstK;
    }
}
