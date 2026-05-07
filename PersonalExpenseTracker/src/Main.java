import repository.BudgetRepository;
import repository.CategoryRepository;
import repository.TransactionRepository;
import ui.Menu;

public class Main {
    public static void main(String[] args){
        // Initialize repositories
        TransactionRepository txnRepo = new TransactionRepository();
        CategoryRepository catRepo = new CategoryRepository();
        BudgetRepository bRepo = new BudgetRepository();

        // Start interactive menu
        Menu menu = new Menu(txnRepo, catRepo, bRepo);
        menu.start();
    }
}