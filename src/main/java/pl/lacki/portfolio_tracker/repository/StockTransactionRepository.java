package pl.lacki.portfolio_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lacki.portfolio_tracker.entity.StockTransaction;


import java.util.List;

@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {
    List<StockTransaction> findByTickerOrderByTransactionDateAsc(String ticker);
}
