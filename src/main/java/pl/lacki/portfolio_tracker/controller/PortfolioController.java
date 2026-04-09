package pl.lacki.portfolio_tracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lacki.portfolio_tracker.entity.StockTransaction;
import pl.lacki.portfolio_tracker.service.PortfolioService;
import pl.lacki.portfolio_tracker.dto.PositionSummary;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {
    
    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @PostMapping("/transactions")
    public ResponseEntity<StockTransaction> addTransaction(@RequestBody StockTransaction transaction) {
        StockTransaction savedTransaction = portfolioService.addTransaction(transaction);
        return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<StockTransaction>> getAllTransactions() {
        return ResponseEntity.ok(portfolioService.getAllTransactions());
    }

    @GetMapping("/{ticker}/summary")
    public ResponseEntity<PositionSummary> getSummary(@PathVariable String ticker) {
        PositionSummary summary = portfolioService.getPositionSummary(ticker);
        return ResponseEntity.ok(summary);
    }

}
