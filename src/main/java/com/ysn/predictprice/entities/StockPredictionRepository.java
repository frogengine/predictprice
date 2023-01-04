package com.ysn.predictprice.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockPredictionRepository extends JpaRepository<StockPrediction, Long> {
    @Query(value = "SELECT predict_date,s.ticker,accuracy,ci.company_name as company_name,consensus,predict_time,prediction_today,price_yesterday,predicted_change_rate,yesterday_date,status\n" +
            "FROM stock_prediction s\n" +
            "INNER JOIN(\n" +
            "SELECT ticker, MAX(predict_date) AS lastdate\n" +
            "FROM stock_prediction\n" +
            "WHERE ticker!='GOOGL' and ticker!='AMZN' and ticker!='AAPL' and ticker!='MSFT' and ticker!='TSLA' and ticker!='NVDA' and ticker!='PEP' and ticker!='AVGO' and ticker!='AZN' and ticker!='COST' and ticker!='CSCO' and ticker!='ADBE' and ticker!='CMCSA' and ticker!='TXN' and ticker!='AMGN' and ticker!='NFLX' and ticker!='QCOM' and ticker!='SNY' and ticker!='INTU' and ticker!='PDD' and ticker!='INTC' and ticker!='GILD' and ticker!='ADP' and ticker!='AMAT'\n"+
            "GROUP BY ticker\n" +
            ")sm ON s.ticker = sm.ticker AND s.predict_date=sm.lastdate\n" +
            "LEFT JOIN stock_symbols ci\n" +
            "ON ci.ticker = s.ticker", nativeQuery = true)
    List<StockPrediction> listFromLastOnes();
}