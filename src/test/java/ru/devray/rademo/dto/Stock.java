package ru.devray.rademo.dto;

import java.util.Objects;

public class Stock {
    /*
  "symbol" : "AAPL",
  "price" : 127.45000000,
  "volume" : 81917959
     */

    public String symbol;
    public double price;
    public long volume;

    public Stock() {
    }

    public Stock(String symbol, double price, long volume) {
        this.symbol = symbol;
        this.price = price;
        this.volume = volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(symbol, stock.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }
}
