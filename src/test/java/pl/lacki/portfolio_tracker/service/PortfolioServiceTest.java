package pl.lacki.portfolio_tracker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.lacki.portfolio_tracker.dto.PositionSummary;
import pl.lacki.portfolio_tracker.entity.StockTransaction;
import pl.lacki.portfolio_tracker.entity.TransactionType;
import pl.lacki.portfolio_tracker.repository.StockTransactionRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PortfolioServiceTest {

    @Mock
    private StockTransactionRepository repository;

    @InjectMocks
    private PortfolioService portfolioService;

    @Test
    void shouldCalculateFifoAveragePriceCorrectlyWhenPartialSaleOccurs() {
        // GIVEN
        String ticker = "AAPL";

        StockTransaction buy1 = new StockTransaction();
        buy1.setTransactionType(TransactionType.BUY);
        buy1.setQuantity(10);
        buy1.setPrice(new BigDecimal("10.00"));

        StockTransaction buy2 = new StockTransaction();
        buy2.setTransactionType(TransactionType.BUY);
        buy2.setQuantity(10);
        buy2.setPrice(new BigDecimal("100.00"));

        StockTransaction sell1 = new StockTransaction();
        sell1.setTransactionType(TransactionType.SELL);
        sell1.setQuantity(10);
        sell1.setPrice(new BigDecimal("150.00"));

        List<StockTransaction> mockTransactions = Arrays.asList(buy1, buy2, sell1);

        when(repository.findByTickerOrderByTransactionDateAsc(ticker)).thenReturn(mockTransactions);

        // WHEN
        PositionSummary summary = portfolioService.getPositionSummary(ticker);

        // THEN
        assertEquals(10, summary.currentShares(), "Current shares should be 10.");
        assertEquals(new BigDecimal("100.00"), summary.averagePrice(), "Average price should be 100.00.");
    }
}