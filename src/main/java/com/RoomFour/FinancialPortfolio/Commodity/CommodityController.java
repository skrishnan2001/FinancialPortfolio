package com.RoomFour.FinancialPortfolio.Commodity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/portfolio")
public class CommodityController {
    private CommodityService commodityService;
    @Autowired
    public CommodityController(CommodityService commodityService) {
        this.commodityService = commodityService;
    }
}
