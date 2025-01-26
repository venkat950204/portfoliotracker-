package com.example.portfolio_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.portfolio_tracker.entity.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
    // Custom query methods (if necessary) can be added here
}

