package com.RoomFour.FinancialPortfolio.Commodity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/portfolio")
public class CommodityController {
    private CommodityService commodityService;
    @Autowired
    public CommodityController(CommodityService commodityService) {
        this.commodityService = commodityService;
    }

    @PostMapping
    public ResponseEntity<Commodity> addCommodity(@RequestBody Commodity c){
        Commodity addedCommodity = commodityService.addCommodity(c);
        if (addedCommodity == null) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<>(addedCommodity, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Commodity> updateCommodity(@PathVariable long id, @RequestBody Commodity c){
        Commodity updatedCommodity = commodityService.updateCommodity(id, c);
        if (updatedCommodity == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(updatedCommodity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Commodity> deleteCommodity(@PathVariable long id){
        Commodity deletedCommodity = commodityService.deleteCommodity(id);
        if (deletedCommodity == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(deletedCommodity, HttpStatus.OK);
    }
    @GetMapping
    public List<Commodity> getAll() {
        return commodityService.getAll();
    }

    @GetMapping("/getByTicker/{ticker}")
    public List<Commodity> getByTicker(@PathVariable String ticker) {
        return commodityService.getByTicker(ticker);
    }

    @PostMapping("/bulkPost")
    public List<Commodity> bulkAdd(@RequestBody List<Commodity> cList){
        return commodityService.bulkAdd(cList);
    }

    //total-investment
    @GetMapping("/getTotalInvestment")
    public Map<String, Double> getTotalInvestmentForAllTickerSymbols() {
        return commodityService.getTotalInvestmentForAllTickerSymbols();
    }
    @GetMapping("/profitsByTicker")
    public Map<String, Double> getProfitsByTicker() {
        return commodityService.getProfitsByTicker();
    }
    @GetMapping("/getTotalProfits")
    public double getTotalProfits() {
        return commodityService.getTotalProfits();
    }
}
