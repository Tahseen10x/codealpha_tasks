import java.io.*;
import java.util.*;

/**
 * STOCK CLASS: Represents a single stock
 */
class Stock {
    private String symbol;
    private String companyName;
    private double price;
    private int availableShares;

    public Stock(String symbol, String companyName, double price, int shares) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
        this.availableShares = shares;
    }

    // Getters and Setters
    public String getSymbol() { return symbol; }
    public String getCompanyName() { return companyName; }
    public double getPrice() { return price; }
    public int getAvailableShares() { return availableShares; }
    
    public void setPrice(double price) { this.price = price; }
    public void updateShares(int amount) { this.availableShares += amount; }

    public void printStockInfo() {
        System.out.printf("%-8s | %-20s | $%-8.2f | %d shares%n", 
            symbol, companyName, price, availableShares);
    }
}

/**
 * PORTFOLIO ITEM: Represents a stock owned by the user
 */
class PortfolioItem {
    private String symbol;
    private int quantity;
    private double buyPrice;

    public PortfolioItem(String symbol, int quantity, double buyPrice) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
    }

    public String getSymbol() { return symbol; }
    public int getQuantity() { return quantity; }
    public double getBuyPrice() { return buyPrice; }
    
    public void addShares(int qty, double price) {
        this.quantity += qty;
        this.buyPrice = price; // Update average price
    }
    
    public void removeShares(int qty) {
        this.quantity -= qty;
    }

    public double getTotalValue(double currentPrice) {
        return quantity * currentPrice;
    }

    public double getProfitLoss(double currentPrice) {
        return (currentPrice - buyPrice) * quantity;
    }
}

/**
 * TRANSACTION: Records all buy/sell history
 */
class Transaction implements Serializable {
    private String type; // BUY or SELL
    private String symbol;
    private int quantity;
    private double price;
    private String timestamp;

    public Transaction(String type, String symbol, int quantity, double price) {
        this.type = type;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.timestamp = new Date().toString();
    }

    public void print() {
        System.out.printf("%-5s | %-8s | %5d shares | $%.2f | %s%n", 
            type, symbol, quantity, price * quantity, timestamp);
    }
}

/**
 * USER CLASS: Manages user portfolio and balance
 */
class User implements Serializable {
    private String name;
    private double balance;
    private ArrayList<PortfolioItem> portfolio;
    private ArrayList<Transaction> transactionHistory;

    public User(String name, double initialBalance) {
        this.name = name;
        this.balance = initialBalance;
        this.portfolio = new ArrayList<>();
        this.transactionHistory = new ArrayList<>();
    }

    public String getName() { return name; }
    public double getBalance() { return balance; }
    public ArrayList<PortfolioItem> getPortfolio() { return portfolio; }
    public ArrayList<Transaction> getTransactionHistory() { return transactionHistory; }

    public void addBalance(double amount) { this.balance += amount; }
    public void deductBalance(double amount) { this.balance -= amount; }

    public PortfolioItem findInPortfolio(String symbol) {
        for (PortfolioItem item : portfolio) {
            if (item.getSymbol().equalsIgnoreCase(symbol)) {
                return item;
            }
        }
        return null;
    }

    public void buyStock(String symbol, int quantity, double price) {
        double totalCost = quantity * price;
        if (totalCost > balance) {
            System.out.println("❌ Insufficient funds!");
            return;
        }
        
        deductBalance(totalCost);
        
        PortfolioItem item = findInPortfolio(symbol);
        if (item != null) {
            item.addShares(quantity, price);
        } else {
            portfolio.add(new PortfolioItem(symbol, quantity, price));
        }
        
        transactionHistory.add(new Transaction("BUY", symbol, quantity, price));
        System.out.println("✅ Bought " + quantity + " shares of " + symbol);
    }

    public void sellStock(String symbol, int quantity, double price) {
        PortfolioItem item = findInPortfolio(symbol);
        if (item == null || item.getQuantity() < quantity) {
            System.out.println("❌ You don't have enough shares!");
            return;
        }

        double totalSale = quantity * price;
        addBalance(totalSale);
        
        item.removeShares(quantity);
        if (item.getQuantity() == 0) {
            portfolio.remove(item);
        }
        
        transactionHistory.add(new Transaction("SELL", symbol, quantity, price));
        System.out.println("✅ Sold " + quantity + " shares of " + symbol);
    }

    public void printPortfolio(HashMap<String, Stock> marketStocks) {
        System.out.println("\n========== YOUR PORTFOLIO ==========");
        System.out.println("-------------------------------------");
        
        if (portfolio.isEmpty()) {
            System.out.println("No stocks owned yet.");
            return;
        }

        double totalValue = 0;
        double totalInvested = 0;

        for (PortfolioItem item : portfolio) {
            Stock stock = marketStocks.get(item.getSymbol());
            double currentPrice = (stock != null) ? stock.getPrice() : 0;
            double value = item.getQuantity() * currentPrice;
            double profitLoss = item.getProfitLoss(currentPrice);
            
            totalValue += value;
            totalInvested += item.getBuyPrice() * item.getQuantity();

            System.out.printf("Stock: %s | Qty: %d | Buy: $%.2f | Curr: $%.2f | Value: $%.2f | P/L: $%.2f%n",
                item.getSymbol(), item.getQuantity(), item.getBuyPrice(), currentPrice, value, profitLoss);
        }
        
        System.out.println("-------------------------------------");
        System.out.printf("Total Balance: $%.2f%n", balance);
        System.out.printf("Total Portfolio Value: $%.2f%n", totalValue);
        System.out.printf("Net Worth: $%.2f%n", balance + totalValue);
        
        double netProfit = (balance + totalValue) - 10000; // Assuming starting balance
        System.out.printf("Net Profit/Loss: $%.2f%n", netProfit);
    }

    public void printTransactionHistory() {
        System.out.println("\n========== TRANSACTION HISTORY ==========");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        for (Transaction t : transactionHistory) {
            t.print();
        }
    }
}

/**
 * MAIN CLASS: Trading Platform Interface
 */
public class StockTradingPlatform {
    private HashMap<String, Stock> marketStocks;
    private User currentUser;
    private Scanner scanner;

    public StockTradingPlatform() {
        marketStocks = new HashMap<>();
        scanner = new Scanner(System.in);
        initializeMarket();
        loadUserData(); // Try to load saved data
    }

    public static void main(String[] args) {
        new StockTradingPlatform().startMenu();
    }

    // --- Initialization ---
    
    private void initializeMarket() {
        // Adding some fake stocks to the market
        marketStocks.put("AAPL", new Stock("AAPL", "Apple Inc.", 175.50, 1000));
        marketStocks.put("GOOG", new Stock("GOOG", "Alphabet Inc.", 140.25, 800));
        marketStocks.put("MSFT", new Stock("MSFT", "Microsoft", 380.00, 1200));
        marketStocks.put("TSLA", new Stock("TSLA", "Tesla Inc.", 245.75, 500));
        marketStocks.put("AMZN", new Stock("AMZN", "Amazon.com", 178.90, 750));
    }

    // --- File I/O Methods ---

    private void saveUserData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("user_data.dat"))) {
            out.writeObject(currentUser);
            System.out.println("💾 Data saved successfully!");
        } catch (IOException e) {
            System.out.println("❌ Error saving data: " + e.getMessage());
        }
    }

    private void loadUserData() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("user_data.dat"))) {
            currentUser = (User) in.readObject();
            System.out.println("✅ Welcome back, " + currentUser.getName() + "!");
        } catch (Exception e) {
            // No saved data found, create new user
            createNewUser();
        }
    }

    private void createNewUser() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter initial balance: $");
        double balance = scanner.nextDouble();
        scanner.nextLine();
        
        currentUser = new User(name, balance);
        System.out.println("✅ Account created with balance: $" + balance);
    }

    // --- Menu Methods ---

    private void startMenu() {
        int choice;
        do {
            showMainMenu();
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: showMarketData(); break;
                case 2: buyStock(); break;
                case 3: sellStock(); break;
                case 4: currentUser.printPortfolio(marketStocks); break;
                case 5: currentUser.printTransactionHistory(); break;
                case 6: depositMoney(); break;
                case 7: withdrawMoney(); break;
                case 8: saveUserData(); break;
                case 9: 
                    saveUserData(); 
                    System.out.println("Goodbye!"); 
                    break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 9);
    }

    private void showMainMenu() {
        System.out.println("\n========== STOCK TRADING PLATFORM ==========");
        System.out.println("Balance: $" + currentUser.getBalance());
        System.out.println("---------------------------------------------");
        System.out.println("1. View Market Data");
        System.out.println("2. Buy Stock");
        System.out.println("3. Sell Stock");
        System.out.println("4. View My Portfolio");
        System.out.println("5. View Transaction History");
        System.out.println("6. Deposit Money");
        System.out.println("7. Withdraw Money");
        System.out.println("8. Save Data");
        System.out.println("9. Exit");
        System.out.print("Choose: ");
    }

    private void showMarketData() {
        System.out.println("\n========== MARKET DATA ==========");
        System.out.println("Symbol | Company Name        | Price    | Shares");
        System.out.println("------------------------------------------------");
        for (Stock stock : marketStocks.values()) {
            stock.printStockInfo();
        }
    }

    private void buyStock() {
        System.out.print("Enter Stock Symbol (e.g., AAPL): ");
        String symbol = scanner.nextLine().toUpperCase();
        
        Stock stock = marketStocks.get(symbol);
        if (stock == null) {
            System.out.println("❌ Stock not found!");
            return;
        }

        System.out.println("Current Price: $" + stock.getPrice());
        System.out.print("How many shares to buy? ");
        int qty = scanner.nextInt();
        scanner.nextLine();

        if (qty <= 0 || qty > stock.getAvailableShares()) {
            System.out.println("❌ Invalid quantity!");
            return;
        }

        currentUser.buyStock(symbol, qty, stock.getPrice());
        stock.updateShares(-qty);
    }

    private void sellStock() {
        System.out.print("Enter Stock Symbol to sell: ");
        String symbol = scanner.nextLine().toUpperCase();
        
        Stock stock = marketStocks.get(symbol);
        if (stock == null) {
            System.out.println("❌ Stock not found!");
            return;
        }

        PortfolioItem item = currentUser.findInPortfolio(symbol);
        if (item == null) {
            System.out.println("❌ You don't own this stock!");
            return;
        }

        System.out.println("You have " + item.getQuantity() + " shares.");
        System.out.print("How many to sell? ");
        int qty = scanner.nextInt();
        scanner.nextLine();

        if (qty <= 0 || qty > item.getQuantity()) {
            System.out.println("❌ Invalid quantity!");
            return;
        }

        currentUser.sellStock(symbol, qty, stock.getPrice());
        stock.updateShares(qty);
    }

    private void depositMoney() {
        System.out.print("Enter amount to deposit: $");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        if (amount > 0) {
            currentUser.addBalance(amount);
            System.out.println("✅ Deposited $" + amount);
        }
    }

    private void withdrawMoney() {
        System.out.print("Enter amount to withdraw: $");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        if (amount > 0 && amount <= currentUser.getBalance()) {
            currentUser.deductBalance(amount);
            System.out.println("✅ Withdrew $" + amount);
        } else {
            System.out.println("❌ Invalid amount!");
        }
    }
}