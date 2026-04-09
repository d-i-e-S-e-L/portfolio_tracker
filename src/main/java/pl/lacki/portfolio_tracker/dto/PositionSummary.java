package pl.lacki.portfolio_tracker.dto;

import java.math.BigDecimal;

public record PositionSummary(
    String ticker,
    int currentShares,
    BigDecimal averagePrice
) {}
