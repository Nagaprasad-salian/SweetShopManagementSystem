# 🍬 Sweet Shop Management System

**Developer:** Nagaprasad Salian  
**Project Type:** AI Kata / SEETSHOPMANAGEMENT Project  
**Language:** Java (Swing + SQLite + iText)

---

## 🧾 Overview

The **Sweet Shop Management System** is a desktop-based Java application that helps manage a sweet shop’s daily operations — including **billing, inventory, customers, user management, and sales reporting**.  
Invoices are automatically generated as PDF files for every transaction.

---

## 🛠️ Technologies Used
- **Java Swing** — Graphical User Interface  
- **SQLite (JDBC)** — Local database storage  
- **iText PDF Library** — Automatic invoice generation  
- **JUnit (optional)** — Testing support  

---

## ✅ Features Implemented

| Feature | Description |
|----------|--------------|
| 🔐 **User Authentication** | Admin and Cashier can register and log in securely |
| 🏠 **Dashboard** | Central hub for navigating between modules |
| 💵 **Billing System** | Add items, calculate total, and generate PDF invoice |
| 📦 **Inventory Management** | Manage sweets list and stock levels |
| 📊 **Sales Reports** | View total sales and performance summaries |
| 👥 **Customer Management** | Add, update, or delete customer records |
| 👤 **User Management** | Admin can create and remove cashier accounts |
| 🧭 **Navigation Controls** | Back, Logout, and Exit buttons in every screen |
| 🎨 **Aesthetic UI** | Soft color scheme and modern Swing design |

---

## 🧮 Database Information

**Database:** `sweetshop.db` (using SQLite)  

**Tables:**
- `users` — stores admin and cashier login details  
- `sweet_items` — list of available sweets and prices  
- `sales` — stores sales transactions  
- `sale_items` — items belonging to each sale  
- `customers` — customer details  

**Location:**  
`src/main/resources/db/sweetshop.db`

---

## 🧩 How to Run the Project

### 1️⃣ Compile the Project
```bash
javac -cp ".;lib\sqlite-jdbc-3.50.3.0.jar;lib\itextpdf-5.5.13.3.jar" ^
-d out src\main\java\com\sweetshop\util\*.java ^
src\main\java\com\sweetshop\model\*.java ^
src\main\java\com\sweetshop\dao\*.java ^
src\main\java\com\sweetshop\service\*.java ^
src\main\java\com\sweetshop\ui\*.java



2️⃣ Run the Application
java -cp ".;out;lib\sqlite-jdbc-3.50.3.0.jar;lib\itextpdf-5.5.13.3.jar" com.sweetshop.ui.LoginFrame