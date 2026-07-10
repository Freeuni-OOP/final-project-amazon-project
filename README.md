[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/skmUAHf8)
# Final-Project
OOP ფინალური პროექტი

# In this Project we are using following tools and languages:
1. Java Spring Boot
2. MS SQL
3. HTML
4. CSS
5. React
6. Docker

# Application Features:
1. On this application user can buy and sell items.
   In order to achieve that user needs to 'sign in' or 'sign up'
   1. While 'sign up' User needs to enter his/her unique 'username', password, email, BirthDate and Gender;
   2. User's password should be at least 8 characters long, should contain alphabet letters, at least one number
      and uppercase letter
   3. While 'sign in' User needs to enter his/her email and password;
2. After 'sign up' User has some balance of money, which she/he can spend with buying items
   or increase with selling something;
3. If user wants to buy anything she/he can save items in cart on following condition:
   1. You can not save more than 50 different items in cart but you can have more than 50 units of same item;
4. While shopping you can sort items in price increasing or decreasing order,
   on addition to that you can also have price range for that items;
5. After buying items saved in your cart, you can see those items in your orders and transactions;
6. If user wants to sell anything, she/he can upload that product with images (max 5 images) and additional info.
   If no image is added, website adds default image.
7. On website you can also change themes: default is light theme, but you can change it to dark;

# For launching the project you need to follow these steps:
1. If you have not downloaded node.js in your computer yet: visit page "https://nodejs.org/en/download"
   and download it for your environment;
2. Open up docker and leave it open for this project;
3. Open intelliJ terminal and run following command from the root directory:
   1. compose up db -d
   2. Then wait a bit so that docker can start up
4. Open AmazonBackendApplication.java and press <run button> on current file;
5. After step 3 wait some time to make sure Backend is done doing setup tasks (max 5s);
    1. After step number 4 you should see new container running in your Docker;
6. Then open up InteliJ terminal, stand on Frontend folder and run the command:
    1. npm run dev
7. If step number 5 does not work run these commands: 
    1. npm install
    2. npm run dev
8. In terminal you are going to see website link. 
   Click on that link and enjoy "Amazon Project" in your default browser;
