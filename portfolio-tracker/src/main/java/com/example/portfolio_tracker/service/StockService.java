package com.example.portfolio_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.portfolio_tracker.entity.Stock;
import com.example.portfolio_tracker.repository.StockRepository;

import java.util.List;

@Service
public class StockService {

    private static final String API_KEY = "your_alpha_vantage_api_key";
    private static final String BASE_URL = "https://www.alphavantage.co/query";

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private RestTemplate restTemplate;

    // Add a new stock to the portfolio
    public Stock addStock(Stock stock) {
        stock.calculatePurchasePrice();
        return stockRepository.save(stock);
    }

    // Update an existing stock in the portfolio
    public Stock updateStock(Long id, Stock updatedStock) {
        Stock existingStock = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found with id: " + id));

        existingStock.setName(updatedStock.getName());
        existingStock.setTicker(updatedStock.getTicker());
        existingStock.setQuantity(updatedStock.getQuantity());
        existingStock.setBuyPrice(updatedStock.getBuyPrice());
        return stockRepository.save(existingStock);
    }


    // Delete a stock from the portfolio
    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }

    // Retrieve all stocks in the portfolio
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    // Calculate the total value of the portfolio
    public double calculatePortfolioValue() {
        double portfolioValue = 0.0;
        List<Stock> stocks = stockRepository.findAll();
        for (Stock stock : stocks) {
            double stockPrice = getStockPrice(stock.getTicker());
            portfolioValue += stock.getQuantity() * stockPrice;
        }
        return portfolioValue;
    }

    // Get stock price using RestTemplate
    private double getStockPrice(String ticker) {
        try {
            String url = BASE_URL + "?function=GLOBAL_QUOTE&symbol=" + ticker + "&apikey=" + API_KEY;
            String response = restTemplate.getForObject(url, String.class);

            // Parse the JSON response to extract the stock price
            // Assuming the API returns JSON with a field like "05. price"
            return parsePriceFromResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching stock price for ticker: " + ticker);
        }
    }

    // Parse the JSON response to get the price
    private double parsePriceFromResponse(String response) {
        // Replace with actual JSON parsing logic
        // For example, use Jackson or Gson to extract the "05. price" field
        return 100.0; // Placeholder for parsed price
    }
}
