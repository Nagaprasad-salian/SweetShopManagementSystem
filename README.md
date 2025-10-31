# 🍬 Sweet Shop Management System

**Developer:** Nagaprasad Salian  
**Project Type:** AI Kata / SweetShopManagement Project  
**Language:** Java (Swing + SQLite + iText)

---

## 🧾 Overview

The **Sweet Shop Management System** is a desktop-based application that helps manage a sweet shop’s daily operations — including **billing, inventory, customers, and sales reporting**.  
Invoices are automatically generated as PDF files for every transaction.

---

## 🛠️ Technologies Used
- **Java Swing** — User Interface  
- **SQLite (JDBC)** — Database  
- **iText PDF Library** — Invoice generation  
- **JUnit (optional)** — Testing  

---

## ✅ Features Implemented

| Feature | Description |
|----------|--------------|
| 🔐 Login Authentication | Secure admin login with username & password |
| 🏠 Dashboard | Central control for all modules |
| 💵 Billing System | Add items, calculate total, generate PDF invoice |
| 📦 Inventory Management | Manage sweets list and stock levels |
| 📊 Reports | View total sales and performance summary |
| 👥 Customer Management | Add, update, or delete customer records |
| 🧭 Navigation | Back, Logout, and Exit buttons |
| 🎨 Aesthetic UI | Pastel color scheme and modern interface |

---

## 🧮 Database Information

**Database:** SQLite (`sweetshop.db`)

**Tables:**
- `users`
- `sweet_items`
- `sales`
- `sale_items`
- `customers`

Location:  Udupi



---

## 🧩 How to Run the Project

### 1️⃣ Compile
```bash
javac -cp ".;lib\sqlite-jdbc-3.50.3.0.jar;lib\itextpdf-5.5.13.3.jar" -d out src\main\java\com\sweetshop\util\*.java src\main\java\com\sweetshop\model\*.java src\main\java\com\sweetshop\dao\*.java src\main\java\com\sweetshop\service\*.java src\main\java\com\sweetshop\ui\*.java

### RUN
java --enable-native-access=ALL-UNNAMED -cp ".;out;src/main/resources;lib\sqlite-jdbc-3.50.3.0.jar;lib\itextpdf-5.5.13.3.jar" com.sweetshop.ui.LoginFrame



Login Credentials:

Username: admin
Password: admin123