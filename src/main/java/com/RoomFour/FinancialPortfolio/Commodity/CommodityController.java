package com.RoomFour.FinancialPortfolio.Commodity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
public class CommodityController {
    private CommodityService commodityService;
    @Autowired
    public CommodityController(CommodityService commodityService) {
        this.commodityService = commodityService;
    }
    @GetMapping
    public List<Commodity> getAll() {

        return commodityService.getAll();
    }

    @GetMapping("/getByTicker/{ticker}")
    public List<Commodity> getByTicker(@RequestParam String ticker) {
        return commodityService.getByTicker(ticker);
    }

}
