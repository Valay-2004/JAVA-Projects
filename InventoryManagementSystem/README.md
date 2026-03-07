# Inventory Management System (IMS)

A fully functional **console-based Inventory Management System** built in pure Java.  
Manage products, categories, and suppliers with persistent CSV storage.

![Java](https://img.shields.io/badge/Java-17%2B-orange)
![License](https://img.shields.io/badge/License-MIT-green)

---

## ✨ Features

### Core Functionality
- **Add Product** – with automatic category & supplier creation prompt
- **List All Products**
- **View Products by Category**
- **Sell Item** (reduce stock)
- **Add Stock** (increase stock)
- **Add Category** (standalone)
- **Add Supplier** (standalone with email validation)
- **Auto-save** after every change
- **Interactive menu** with helpful prompts

### Smart Features
- Inline creation of missing categories/suppliers while adding a product
- Email validation for suppliers (regex + loop until valid)
- Duplicate ID protection for categories & suppliers
- Persistent storage in 3 CSV files (`products.csv`, `categories.csv`, `suppliers.csv`)

---

## 📁 Project Structure


```
InventoryManagementSystem/
├── data/
│   ├── products.csv
│   ├── categories.csv
│   └── suppliers.csv
├── src/
│   ├── model/
│   │   ├── Category.java
│   │   ├── Product.java
│   │   └── Supplier.java
│   ├── service/
│   │   ├── Inventory.java
│   │   ├── InvalidProductException.java
│   │   └── ProductNotFoundException.java
│   ├── storage/
│   │   └── CsvInventoryStorage.java
│   ├── ui/
│   │   └── InventoryConsoleUI.java
│   ├── Main.java
│   └── IMSApp.java
├── .gitignore
├── .idea/ (IntelliJ project files)
└── README.md
```

---

## 🚀 How to Run

### Option 1: IntelliJ IDEA (Recommended)
1. Open the folder `InventoryManagementSystem` as a project
2. Right-click on `src/Main.java` or `src/IMSApp.java` → **Run 'Main.main()'**
3. The console menu will appear immediately

### Option 2: Command Line (Terminal)
```bash
# 1. Compile
javac -d bin src/**/*.java

# 2. Run
java -cp bin Main
# or
java -cp bin IMSApp
```

**Requirements**: Java 8 or higher (tested on Java 17+)

---

## 📊 Sample Data (data/products.csv)

```csv
id,name,price,stock,supplierId,categoryId
P-100,Laptop,59999,9,SUP-1,CAT-1
P-101,Redmi Note 10 Lite,16999,10,SUP-1,CAT-1
P-102,Redmi Watch 3 Active,2999,10,SUP-2,CAT-3
P-103,Parachute Coconut Hail Oil,68,10,SUP-123,CAT-123
```

The system automatically loads these files on startup and saves changes instantly.

---

## 🛠 Technologies Used

- **Pure Java SE** (no external libraries)
- `java.util.Scanner` for input
- `java.math.BigDecimal` for accurate pricing
- Custom CSV reader/writer (`CsvInventoryStorage`)
- Regex email validation
- Custom exceptions for robust error handling

---

## 📌 How It Works (Architecture)

1. **Model Layer** – Plain Java objects (`Product`, `Category`, `Supplier`)
2. **Service Layer** – `Inventory` class handles all business logic
3. **Storage Layer** – `CsvInventoryStorage` reads/writes the three CSV files
4. **UI Layer** – `InventoryConsoleUI` provides the interactive console menu

---

## ⚠️ Known Limitations / Future Improvements

| Issue | Status | Suggested Fix |
|------|--------|-------------|
| Negative stock allowed | Open | Add validation in `reduceStock()` |
| No duplicate product ID check | Open | Add check in `addProduct()` |
| Case-sensitive category search | Open | Convert to lowercase comparison |
| Manual compilation | Easy | Add Maven/Gradle build |
| No unit tests | Planned | Add JUnit + Mockito tests (I already prepared them earlier) |

---

## 📝 Usage Tips

- When adding a product and the category/supplier doesn't exist → type **Y** to create it instantly
- Press **Enter** (empty input) = Yes in most confirmation prompts
- Type **8** to exit → automatically saves everything
- All changes are saved immediately (no "save" button needed)

---

## 📄 License

MIT License – feel free to use, modify, and distribute.

---

## 👨‍💻 Author

**Valay-2004**  
Built as a Java learning/project showcase (Feb–Mar 2026)

---

**Made with ❤️ for learning & demonstration**

Star the repo if you found it useful! ⭐
