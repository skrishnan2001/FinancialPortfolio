package com.RoomFour.FinancialPortfolio.Commodity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommodityService {
    private CommodityRepository commodityRepository;
    @Autowired
    public CommodityService(CommodityRepository commodityRepository) {
        this.commodityRepository = commodityRepository;
    }
    public List<Commodity> getAll() {
        return commodityRepository.findAll();
    }

    public List<Commodity> getByTicker(String ticker) {

        return commodityRepository.findByTicker(ticker);
    }

}
