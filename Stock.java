[00:43, 14/08/2024] Aréeba: import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class PortfolioItem {
    private String tickerSymbol;
    private String companyName;
    private double currentValue;
    private double fluctuation;

    public PortfolioItem(String tickerSymbol, String companyName, double currentValue, double fluctuation) {
        this.tickerSymbol = tickerSymbol;
        this.companyName = companyName;
        this.currentValue = currentValue;
        this.fluctuation = fluctuation;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public double getFluctuation() {
        return fluctuation;
    }

    public void updateValue(double newValue) {
        fluctuation = (newValue - currentValue) / currentValue * 100;
        currentValue = newValue;
    }

    @Override
    public String toString() {
        return tickerSymbol + ": " + companyName + " -> $" + currentValue + " (" + fluctuation + "%)";
    }
}

class Portfolio {
    private Map<String, Integer> holdings;
    private double cashBalance;

    public Portfolio() {
        holdings = new HashMap<>();
        cashBalance = 10000.0;
    }

    public void purchasePortfolioItem(PortfolioItem item, int quantity) {
        double totalCost = item.getCurrentValue() * quantity;
        if (cashBalance >= totalCost) {
            cashBalance -= totalCost;
            holdings.put(item.getTickerSymbol(), holdings.getOrDefault(item.getTickerSymbol(), 0) + quantity);
            System.out.println("\n\tYou bought " + quantity + " shares of " + item.getCompanyName() + " at $" + item.getCurrentValue() + ".");
        } else {
            System.out.println("\n\tInsufficient cash balance!");
        }
    }

    public void sellPortfolioItem(PortfolioItem item, int quantity) {
        if (holdings.containsKey(item.getTickerSymbol()) && holdings.get(item.getTickerSymbol()) >= quantity) {
            cashBalance += item.getCurrentValue() * quantity;
            holdings.put(item.getTickerSymbol(), holdings.get(item.getTickerSymbol()) - quantity);
            if (holdings.get(item.getTickerSymbol()) == 0) {
                holdings.remove(item.getTickerSymbol());
            }
            System.out.println("\n\tYou sold " + quantity + " shares of " + item.getCompanyName() + " at $" + item.getCurrentValue() + ".");
        } else {
            System.out.println("\n\tNot enough shares to sell!");
        }
    }

    public void printPortfolio(ArrayList<PortfolioItem> allItems) {
        System.out.println("\tPortfolio");
        System.out.println("\t---------");
        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            String tickerSymbol = entry.getKey();
            int quantity = entry.getValue();
            PortfolioItem item = getItemByTickerSymbol(tickerSymbol, allItems);
            if (item != null) {
                System.out.println("\t" + item.getCompanyName() + " (" + tickerSymbol + "): " + quantity + " shares at $" + item.getCurrentValue() + " each.");
            }
        }
        System.out.println("\tCash: $" + cashBalance);
    }

    public double getPortfolioValue(ArrayList<PortfolioItem> allItems) {
        double value = cashBalance;
        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            PortfolioItem item = getItemByTickerSymbol(entry.getKey(), allItems);
            if (item != null) {
                value += item.getCurrentValue() * entry.getValue();
            }
        }
        return value;
    }

    private PortfolioItem getItemByTickerSymbol(String tickerSymbol, ArrayList<PortfolioItem> allItems) {
        for (PortfolioItem item : allItems) {
            if (item.getTickerSymbol().equals(tickerSymbol)) {
                return item;
            }
        }
        return null;
    }
}

public class InvestmentPlatform {
    private ArrayList<PortfolioItem> items;
    private Portfolio portfolio;

    public InvestmentPlatform() {
        items = new ArrayList<>();
        portfolio = new Portfolio();

        items.add(new PortfolioItem("AAPL", "Apple Inc.", 150.0, 2.5));
        items.add(new PortfolioItem("GOOG", "Alphabet Inc.", 2500.0, 1.2));
        items.add(new PortfolioItem("MSFT", "Microsoft Corporation", 200.0, 3.1));
    }

    public void printMarketData() {
        for (PortfolioItem item : items) {
            System.out.println("\t\t" + item);
        }
    }

    public PortfolioItem getItemByTickerSymbol(String tickerSymbol) {
        for (PortfolioItem item : items) {
            if (item.getTickerSymbol().equals(tickerSymbol)) {
                return item;
            }
        }
    }
