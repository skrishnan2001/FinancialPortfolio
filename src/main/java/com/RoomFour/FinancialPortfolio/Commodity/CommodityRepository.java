package com.RoomFour.FinancialPortfolio.Commodity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommodityRepository extends CrudRepository<Commodity,Long> {
    List<Commodity> findAll();
    List<Commodity> findByTicker(String ticker);
}
