    DROP TABLE IF EXISTS Order_Details;
    DROP TABLE IF EXISTS Transactions;
    DROP TABLE IF EXISTS Orders;
    DROP TABLE IF EXISTS Cart_Items;
    DROP TABLE IF EXISTS Products;
    DROP TABLE IF EXISTS Categories;
    DROP TABLE IF EXISTS Users;
    DROP TABLE IF EXISTS Comment;

    CREATE TABLE Users (
        ID INTEGER IDENTITY(1,1) PRIMARY KEY,
        Email VARCHAR(150) NOT NULL UNIQUE,
        Balance NUMERIC(10, 2) NOT NULL DEFAULT 1000.00,
        Gender VARCHAR(10),
        Birth_Date DATE NOT NULL ,
        Username VARCHAR(50)  NOT NULL UNIQUE,
        Pass_Encrypted VARCHAR(500) NOT NULL
    );

    CREATE TABLE Categories (
        Category_ID INTEGER IDENTITY(1,1) PRIMARY KEY,
        Category VARCHAR(100) NOT NULL UNIQUE
    );

    CREATE TABLE Products (
        Product_ID INTEGER IDENTITY(1,1) PRIMARY KEY,
        Seller_ID INTEGER NOT NULL REFERENCES Users(ID),
        Category_ID INTEGER NOT NULL REFERENCES Categories(Category_ID),
        ProductName VARCHAR(300) NOT NULL,
        Price NUMERIC(10, 2) NOT NULL,
        Quantity INTEGER NOT NULL
    );

    CREATE TABLE Orders (
        Order_ID INTEGER IDENTITY(1,1) PRIMARY KEY,
        Buyer_ID INTEGER NOT NULL REFERENCES Users(ID),
        Total_Amount NUMERIC(10, 2) NOT NULL,
        Order_Date TIMESTAMP NOT NULL
    );

    CREATE TABLE Transactions (
        transaction_id INTEGER IDENTITY(1,1) PRIMARY KEY,
        Order_ID INTEGER NOT NULL REFERENCES Orders(Order_ID),
        Buyer_ID INTEGER NOT NULL REFERENCES Users(ID),
        Seller_ID INTEGER NOT NULL REFERENCES Users(ID),
        Amount NUMERIC(10,2) NOT NULL,
        Payment_Status VARCHAR(50) NOT NULL DEFAULT 'Pending',
        Created_At DATETIME2 NOT NULL
    );

    CREATE TABLE Order_Details (
        Order_Details_ID INTEGER IDENTITY(1,1) PRIMARY KEY,
        Product_ID INTEGER NOT NULL REFERENCES Products(Product_ID),
        Order_ID INTEGER NOT NULL REFERENCES Orders(Order_ID),
        Quantity INTEGER NOT NULL,
        Amount NUMERIC(10, 2) NOT NULL,
        transaction_id INTEGER NOT NULL REFERENCES Transactions(transaction_id)
    );

    CREATE TABLE Cart_Items (
        ID INTEGER IDENTITY(1,1) PRIMARY KEY,
        UserID INTEGER NOT NULL REFERENCES Users(ID),
        Product_ID INTEGER NOT NULL REFERENCES Products(Product_ID) ON DELETE CASCADE,
        Quantity INTEGER NOT NULL DEFAULT 1
    )

    CREATE TABLE Comment (
        Comment_ID INTEGER IDENTITY(1,1) PRIMARY KEY,
        Comment_STR VARCHAR(500) NOT NULL,
        Product_ID INTEGER NOT NULL REFERENCES Products(Product_ID) ON DELETE CASCADE,
        User_ID INTEGER NOT NULL REFERENCES Users(ID)
    )