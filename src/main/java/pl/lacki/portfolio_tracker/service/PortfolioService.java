package pl.lacki.portfolio_tracker.service;

import org.springframework.stereotype.Service;
import pl.lacki.portfolio_tracker.entity.StockTransaction;
import pl.lacki.portfolio_tracker.entity.TransactionType;
import pl.lacki.portfolio_tracker.repository.StockTransactionRepository;
import pl.lacki.portfolio_tracker.dto.PositionSummary;

import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

@Service
public class PortfolioService {
    
    private final StockTransactionRepository repository;

    public PortfolioService(StockTransactionRepository repository) {
        this.repository = repository;
    }

    public StockTransaction addTransaction(StockTransaction transaction) {
        if (transaction.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        if (transaction.getPrice().signum() < 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }

        return repository.save(transaction);
    }

    public List<StockTransaction> getAllTransactions() {
        return repository.findAll();
    }

    public PositionSummary getPositionSummary(String ticker) {
        List<StockTransaction> transactions = repository.findByTickerOrderByTransactionDateAsc(ticker);
        List<StockTransaction> buys = new ArrayList<>();
        int totalSold = 0;

        for (StockTransaction t : transactions) {
            if (t.getTransactionType() == TransactionType.BUY) {
                buys.add(t);
            } else if (t.getTransactionType() == TransactionType.SELL) {
                totalSold += t.getQuantity();
            }
        }

        int currentShares = 0;
        BigDecimal totalActiveCost = BigDecimal.ZERO;

        for (StockTransaction buy : buys) {
            if (totalSold == 0) {
                currentShares += buy.getQuantity();
                totalActiveCost = totalActiveCost.add(buy.getPrice().multiply(BigDecimal.valueOf(buy.getQuantity())));
            }
            else if (totalSold >= buy.getQuantity()) {
                totalSold -= buy.getQuantity();
            } else {
                int remainingShares = buy.getQuantity() - totalSold;
                currentShares += remainingShares;
                totalActiveCost = totalActiveCost.add(buy.getPrice().multiply(BigDecimal.valueOf(remainingShares)));
                totalSold = 0;
            }
        }

        BigDecimal averagePrice = currentShares > 0 ? totalActiveCost.divide(BigDecimal.valueOf(currentShares), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;

        return new PositionSummary(ticker, currentShares, averagePrice);
    }

}
