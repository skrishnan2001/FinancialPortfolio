package com.RoomFour.FinancialPortfolio.Commodity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommodityService {
    private CommodityRepository commodityRepository;
    @Autowired
    public CommodityService(CommodityRepository commodityRepository) {
        this.commodityRepository = commodityRepository;
    }

    public Commodity addCommodity(Commodity c){
        // add validation steps and return null if fails
        return commodityRepository.save(c);
    }

    public Commodity updateCommodity(long id, Commodity c){
        Commodity c_ = commodityRepository.findById(id).orElseGet(()->null);
        if (c_ != null) return c_.set(c);
        return null;
    }

    public Commodity deleteCommodity(long id){
        Commodity c_ = commodityRepository.findById(id).orElseGet(()->null);
        if (c_ != null) commodityRepository.delete(c_);
        return c_;
    }
}
