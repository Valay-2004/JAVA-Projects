# 💰 Personal Expense Tracker 💰

A console-based personal finance application built in Java that helps users track expenses, manage hierarchical categories, set budgets, and generate spending reports.

> ✨ **Built as a self-directed learning project** to practice: complex data aggregation, date-based filtering, budget validation, category hierarchies, advanced file I/O, and statistical calculations.

---

## 🚀 Features

- ✅ **Transaction Management**
  - Add income/expense transactions with amount, date, category, and notes
  - Persistent storage using JSONL (line-delimited JSON) for efficient append-only writes
  - Atomic file writes to prevent data corruption

- 🌳 **Hierarchical Categories**
  - Parent-child category relationships (e.g., `Food → Dining → Lunch`)
  - Hybrid system: pre-defined system categories + user-defined custom categories
  - Recursive tree traversal for budget aggregation across category subtrees

- 💰 **Budget Tracking**
  - Set spending limits for any category with custom date ranges
  - Automatic inclusion of child category spending in parent budget calculations
  - Real-time status: spent vs. limit with visual indicators (✅/⚠️)

- 📊 **Reporting**
  - Monthly budget reports showing all active budgets
  - Console-formatted output with clear spending summaries
  - Date-range filtering using ISO 8601 date strings

---

## 🛠️ Tech Stack

| Component | Technology |
|-----------|-----------|
| Language | Java 17+ |
| JSON Parsing | Gson (com.google.code.gson) |
| Date/Time | `java.time.LocalDate`, `java.time.YearMonth` |
| Money Handling | `java.math.BigDecimal` (no floating-point errors) |
| File I/O | `BufferedReader`, `FileWriter`, `Files.move()` with atomic fallback |
| Architecture | Layered: Model → Repository → Service → CLI |

---

## 📁 Project Structure

```
src/main/java/com/expensetracker/
├── model/
│   ├── Transaction.java          # Immutable transaction record
│   ├── Category.java             # Hierarchical category with type (SYSTEM/USER)
│   ├── Budget.java               # Budget rule with date range
│   ├── TransactionType.java      # Enum: INCOME, EXPENSE
│   ├── CategoryType.java         # Enum: SYSTEM, USER
│   └── dto/
│       └── BudgetStatus.java     # Result DTO for budget calculations
├── repository/
│   ├── TransactionRepository.java # JSONL persistence with atomic writes
│   ├── CategoryRepository.java    # JSON persistence + default initialization
│   └── BudgetRepository.java      # JSON persistence for budget rules
├── service/
│   └── BudgetService.java         # Business logic: tree traversal, aggregation, reporting
├── ui/                            
│   └── Menu.java                  # CLI Menu/UI
└── Main.java                      # CLI entry point with demo/test harness
```

---

## ⚙️ How to Run

### Prerequisites
- Java 17 or higher
- Gson library (`gson-2.10.1.jar` or Maven/Gradle dependency)

### Setup
1. Clone or download the project
2. Add Gson to your classpath:
   ```bash
   # If using manual JAR:
   javac -cp "libs/gson-2.10.1.jar" -d out src/main/java/com/expensetracker/**/*.java
   
   # If using Maven, add to pom.xml:
   <dependency>
       <groupId>com.google.code.gson</groupId>
       <artifactId>gson</artifactId>
       <version>2.10.1</version>
   </dependency>
   ```

### Execute
```bash
# Run the main class
java -cp "out:libs/gson-2.10.1.jar" com.expensetracker.Main
```

### First Run Behavior
- Automatically creates `data/` directory
- Initializes default system categories (`Food`, `Transport`, etc.)
- Creates empty `transactions.jsonl` and `budgets.json` on first write

---

## 🗄️ Data Storage Format

### Transactions: `data/transactions.jsonl`
Line-delimited JSON (one transaction per line) for efficient streaming:
```json
{"id":"uuid","amount":150.00,"categoryId":"cat-groceries","type":"EXPENSE","note":"Groceries","date":"2026-05-10"}
{"id":"uuid","amount":200.00,"categoryId":"cat-dining","type":"EXPENSE","note":"Restaurant","date":"2026-05-15"}
```

### Categories: `data/categories.json`
Standard JSON array with hierarchy via `parentId`:
```json
[
  {"id":"cat-food","name":"Food","parentId":null,"type":"SYSTEM"},
  {"id":"cat-groceries","name":"Groceries","parentId":"cat-food","type":"SYSTEM"},
  {"id":"cat-gym","name":"Gym Membership","parentId":"cat-health","type":"USER"}
]
```

### Budgets: `data/budgets.json`
```json
[
  {
    "id":"budget-food-may",
    "categoryId":"cat-food",
    "limit":500.00,
    "startDate":"2026-05-01",
    "endDate":"2026-05-31"
  }
]
```

---

## 🧠 Key Design Decisions

### Why JSONL for Transactions?
- ✅ Append-only writes are fast and safe
- ✅ Streaming reads avoid loading entire history into memory
- ✅ Simple recovery: corrupted line doesn't break entire file

### Why Recursive Category Traversal?
- Budgets set on parent categories automatically include child spending
- Implemented via depth-first traversal: collect all descendant IDs, then filter transactions
- Trade-off: O(n) category scan per budget check (acceptable for CLI-scale data)

### Why `BigDecimal` for Money?
- Floating-point (`double`) causes rounding errors: `0.1 + 0.2 != 0.3`
- `BigDecimal` ensures precise financial calculations
- Always use `compareTo()` for comparisons, not `==`

### Atomic Write Pattern
```java
// 1. Write to temp file
// 2. Atomically rename temp → target
// 3. Fallback to regular move if ATOMIC_MOVE unsupported
// 4. Cleanup temp file on failure
```
Prevents partial writes from corrupting data during crashes.

---

## 🎓 Learning Outcomes

By building this project, you practice:

- 🔄 **Recursive algorithms**: Tree traversal for category aggregation
- 📦 **Layered architecture**: Separation of data access (repo) and business logic (service)
- 🔐 **Defensive programming**: Null checks, empty-line handling, file existence validation
- 💾 **File I/O patterns**: Streaming reads, atomic writes, encoding considerations
- 🧮 **Financial logic**: Date-range overlap, budget aggregation, precise arithmetic
- 🧪 **Debugging skills**: Gson quirks, file corruption recovery, type safety

---

## 🔜 Possible Extensions

Ideas to level up this project further:

- [ ] Add CLI menu system with user input parsing
- [ ] Implement CSV export for spreadsheet analysis
- [ ] Add unit tests with JUnit for core logic
- [ ] Support recurring transactions (weekly/monthly)
- [ ] Add simple charts using ASCII art or integrate a terminal graphics library
- [ ] Migrate to SQLite for complex queries and indexing
- [ ] Add authentication for multi-user support

---

## 🤝 Contributing

This is a learning project. Feel free to:
- Fork and experiment with extensions
- Report bugs or suggest improvements via issues
- Share your own variations!

---

## 📜 License

MIT License — Use, modify, and learn freely.

---

> 💡 **Built with ❤️ by Valay**  
> *A self-directed project to master Java, file I/O, and business logic design.*