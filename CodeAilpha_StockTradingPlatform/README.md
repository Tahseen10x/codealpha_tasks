STOCK TRADING PLATFORM
Project Overview

This is a Java-based stock trading simulation platform that allows users to practice buying and selling stocks in a simulated market environment. The application provides features for managing a portfolio, tracking transaction history, and monitoring profit and loss over time.
Features
Market Data Display

    The system includes a pre-loaded list of stocks with their company names, current prices, and available shares.
    Users can view the market at any time to see which stocks are available for trading.

Buy and Sell Operations

    Users can purchase stocks if they have sufficient balance in their account.
    Users can sell stocks they own to convert them back to cash.
    The system validates transactions to ensure users cannot sell more shares than they own or buy more than their balance allows.

Portfolio Performance Tracking

    The system tracks all stocks owned by the user including quantity, purchase price, and current market value.
    It calculates profit and loss for each stock based on the difference between purchase price and current price.
    A summary shows total portfolio value, account balance, and net worth.

Transaction History

    Every buy and sell operation is recorded with timestamp, stock symbol, quantity, and total value.
    Users can view a complete history of all their trading activities.

Data Persistence

    User data including portfolio and transaction history is saved to a file.
    When the application restarts, it loads the saved data automatically, allowing users to continue from where they left off.

Account Management

    Users can deposit additional funds into their trading account.
    Users can withdraw funds from their account.

Technical Details

    Language: Java
    Data Structures: ArrayList, HashMap
    Input/Output: Serialization for file storage
    Design Pattern: Object-Oriented Programming with separate classes for Stocks, Portfolio, Transactions, and Users

How to Run

    Ensure Java is installed on your system.
    Save the file as StockTradingPlatform.java.
    Compile the program:

    javac StockTradingPlatform.java

    Run the program:

    java StockTradingPlatform

    Follow the on-screen menu to navigate the application.

Code Structure

The project consists of the following classes:

    Stock: Represents a stock with symbol, company name, price, and available shares.
    PortfolioItem: Represents a stock owned by the user with quantity and purchase price.
    Transaction: Records a single buy or sell operation.
    User: Manages user balance, portfolio, and transaction history.
    StockTradingPlatform: Main class that handles the menu system and user interaction.

Sample Usage

When the program runs, the user is prompted to enter their name and initial balance. After creating an account, the user can:

    View available stocks in the market
    Buy shares of any available stock
    Sell shares from their portfolio
    Check their portfolio value and profit/loss
    View transaction history
    Save their progress

The data is automatically saved to a file named user_data.dat which is loaded the next time the program runs.
