package com.mike.store;

import com.mike.store.staff.Cashier;
import com.mike.store.storage.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.Random;

class ItemData{
    public final String CategoryName;
    public final String Name;
    public final Store.Category category;
    public final BigDecimal Price;

    ItemData(String categoryName, String name, Store.Category category, BigDecimal price) {
        this.CategoryName = categoryName;
        this.Name = name;
        this.category = category;
        this.Price = price;
    }
}

public class UserInterface {
    private Scanner scanner;
    private List<Store> Stores;
    private Store CurrentStore;
    private String ChosenItem;
    private CashRegister CurrentRegister;
    private Buyer buyer;
    private boolean isRunning;
    private boolean SkipAskingForMoney;
    private int Stage;

    public UserInterface(){
        this.isRunning = true;
        SkipAskingForMoney = false;
        this.Stage = 0;
        this.Stores = new ArrayList<Store>();
        this.scanner = new Scanner(System.in);
        this.ChosenItem = "";
        this.SetupStores();
        this.Start();
    }

    private void SetupStores(){
        Random random = new Random();
        int stores = random.nextInt(11) + 5;
        List<String> StoreKinds = new ArrayList<String>();
        StoreKinds.add("Local Store");
        StoreKinds.add("Convenience Store");

        List<String> StoreNames = new ArrayList<String>();
        StoreNames.add("Emerita");
        StoreNames.add("Daria");
        StoreNames.add("Joleen");
        StoreNames.add("Samira");
        StoreNames.add("Alicia");
        StoreNames.add("Elsie");
        StoreNames.add("Gerry");
        StoreNames.add("Jessenia");
        StoreNames.add("Todd");
        StoreNames.add("Candice");


        List<String> EmployeeFirstNames = new ArrayList<>();
        List<String> EmployeeLastNames = new ArrayList<>();
        EmployeeFirstNames.add("Kay");
        EmployeeFirstNames.add("Armanda");
        EmployeeFirstNames.add("Lon");
        EmployeeFirstNames.add("Erica");
        EmployeeFirstNames.add("Callie");
        EmployeeFirstNames.add("Janina");
        EmployeeFirstNames.add("Sharell");
        EmployeeFirstNames.add("Deandrea");
        EmployeeFirstNames.add("Maryalice");
        EmployeeFirstNames.add("Clara");
        EmployeeFirstNames.add("Shad");
        EmployeeFirstNames.add("Dot");
        EmployeeFirstNames.add("Aundrea");
        EmployeeFirstNames.add("Sheree");
        EmployeeFirstNames.add("Elroy");
        EmployeeFirstNames.add("Cruz");
        EmployeeFirstNames.add("August");
        EmployeeFirstNames.add("Christoper");
        EmployeeFirstNames.add("Karie");
        EmployeeFirstNames.add("Daine");

        EmployeeLastNames.add("ESalamanca");
        EmployeeLastNames.add("Schreiber");
        EmployeeLastNames.add("Dicicco");
        EmployeeLastNames.add("Lisenby");
        EmployeeLastNames.add("Feaster");
        EmployeeLastNames.add("Ostler");
        EmployeeLastNames.add("Farrand");
        EmployeeLastNames.add("Peed");
        EmployeeLastNames.add("Brott");
        EmployeeLastNames.add("Kenna");
        EmployeeLastNames.add("Bricker");
        EmployeeLastNames.add("Wieczorek");
        EmployeeLastNames.add("Aquirre");
        EmployeeLastNames.add("Gillmore");
        EmployeeLastNames.add("Heston");
        EmployeeLastNames.add("Overbey");
        EmployeeLastNames.add("Viens");
        EmployeeLastNames.add("Diangelo");
        EmployeeLastNames.add("Beumer");
        EmployeeLastNames.add("Hallam");

        List<ItemData> ItemCategories = new ArrayList<>();
        ItemCategories.add(new ItemData("Tomato", "Pink", Store.Category.Foods, BigDecimal.valueOf(0.20)));
        ItemCategories.add(new ItemData("Cucumber", "Long", Store.Category.Foods, BigDecimal.valueOf(0.30)));
        ItemCategories.add(new ItemData("Corn", "American", Store.Category.Foods, BigDecimal.valueOf(0.60)));
        ItemCategories.add(new ItemData("Carrot", "Small", Store.Category.Foods, BigDecimal.valueOf(0.50)));
        ItemCategories.add(new ItemData("Bread", "White", Store.Category.Foods, BigDecimal.valueOf(0.40)));
        ItemCategories.add(new ItemData("Chair", "Plastic", Store.Category.Non_Foods, BigDecimal.valueOf(10.0)));
        ItemCategories.add(new ItemData("Table", "Glass", Store.Category.Non_Foods, BigDecimal.valueOf(30.0)));
        ItemCategories.add(new ItemData("Phone", "Samsung", Store.Category.Non_Foods, BigDecimal.valueOf(150.0)));
        ItemCategories.add(new ItemData("Lamp", "Desk", Store.Category.Non_Foods, BigDecimal.valueOf(5.0)));


        for(int i = 0; i < stores; i++){
            int randomName = random.nextInt(StoreNames.size()), randomKind = random.nextInt(StoreKinds.size());
            int randomMarkupFoods = random.nextInt(20) + 5;
            int randomMarkupNonFoods = random.nextInt(50) + 20;
            int randomBeforeExpireSale = random.nextInt(5) + 1;
            BigDecimal MarkupFoods = BigDecimal.valueOf(randomMarkupFoods);
            BigDecimal MarkupNonFoods = BigDecimal.valueOf(randomMarkupNonFoods);
            BigDecimal BeforeExpireSale = BigDecimal.valueOf(randomBeforeExpireSale);
            MarkupFoods = MarkupFoods.divide(BigDecimal.TEN);
            MarkupNonFoods = MarkupNonFoods.divide(BigDecimal.TEN);
            BeforeExpireSale = BeforeExpireSale.divide(BigDecimal.TEN);
            Store store = new Store(StoreNames.get(randomName), StoreKinds.get(randomKind), Integer.toString(random.nextInt(999999) + 100000), MarkupNonFoods, MarkupFoods, random.nextInt(14) + 1, BeforeExpireSale);

            int cashRegisterNum = random.nextInt(5) + 1;
            int randomSalary = random.nextInt(1000) + 500;

            for (int j = 0; j < cashRegisterNum; j++){
                int randomEmployeeName = random.nextInt(EmployeeFirstNames.size()), randomEmployeeLastName = random.nextInt(EmployeeLastNames.size());

                Cashier cashier = new Cashier(EmployeeFirstNames.get(randomEmployeeName), EmployeeLastNames.get(randomEmployeeLastName), Integer.toString(j), BigDecimal.valueOf(randomSalary));
                CashRegister cashRegister = new CashRegister(j+1);

                store.AddCashier(cashier);
                store.AddCashRegisters(cashRegister);
            }

            int howManyDifferentItems = random.nextInt(ItemCategories.size()) + 1;

            List<ItemData> categoriesSet = new ArrayList<>(ItemCategories);
            Collections.shuffle(categoriesSet);

            for (int j = 0; j < howManyDifferentItems; j++){
                int howManyOfItem = random.nextInt(50) + 1;

                for (int k = 0; k < howManyOfItem/2; k++){
                    int randomID = random.nextInt(999999) + 100000;
                    store.storage.AddItem( categoriesSet.get(j).CategoryName, Integer.toString(randomID), categoriesSet.get(j).Name, categoriesSet.get(j).Price, categoriesSet.get(j).category, LocalDate.now().plusDays(50));
                }

                for (int k = howManyOfItem/2; k < howManyOfItem; k++){
                    int randomID = random.nextInt(999999) + 100000;
                    store.storage.AddItem(categoriesSet.get(j).CategoryName, Integer.toString(randomID), categoriesSet.get(j).Name, categoriesSet.get(j).Price, categoriesSet.get(j).category, LocalDate.now().minusDays(random.nextInt(100)));
                }
            }

            List<Integer> CashRegisters = new ArrayList<>();
            for (int k = 0; k < store.CashRegisters.size(); k++){
                CashRegisters.add(k);
            }

            Collections.shuffle(CashRegisters);
            int index = 0;

            for(Integer integer : CashRegisters){
                store.CashRegisters.get(index).SetCashier(store.GetCashier(integer));
                index++;
            }

            this.Stores.add(store);
        }
    }

    private void Reset(){
        this.CurrentRegister = null;
        this.CurrentStore = null;
        this.ChosenItem = "";
        this.Stage = 0;
        this.SkipAskingForMoney = true;
    }

    public void Start(){
        this.isRunning = true;

        while(this.isRunning){
            switch (this.Stage) {
                case 0 -> {
                    this.AskUserForStore();
                }
                case 1 -> {
                    if(!this.SkipAskingForMoney)
                        this.AskUserMoney();
                    else
                        this.Stage++;
                }
                case 2 -> {
                    this.AskForItemsToAddToBasket();
                }
                case 3 -> {
                    this.GoToCashRegister();
                }
                case 4 -> {
                    this.CheckoutItems();
                }
                case 5 -> {
                    this.AskForStartAgain();
                }
            }
        }
    }

    private String GetInput(){
        String input = this.scanner.nextLine();

        if (input.toLowerCase().equals("exit")){
            System.exit(0);
            return "";
        }

        return input;
    }

    private boolean PromptIfDone(String textToPrint){
        System.out.println(textToPrint + "?");
        System.out.println("Type \"Y\" for yes or anything else for no.");
        String input = this.GetInput();

        if (input.length() > 0)
            return input.charAt(0) == 'y';
        return false;
    }

    private void PrintList(List<Item> list){
        for (int i = 0; i < list.size(); i++){
            System.out.println((i + 1) + ". " + list.get(i).toString());
        }
    }

    private void PrintList(ArrayList<CashRegister> list){
        for (int i = 0; i < list.size(); i++){
            System.out.println((i + 1) + ". " + list.get(i).toString());
        }
    }

    private static BigDecimal GetPriceFromList(List<Item> Items){
        BigDecimal accumulatedPrice = BigDecimal.ZERO;

        for (Item item : Items){
            accumulatedPrice = accumulatedPrice.add(item.GetCalculatedPrice());
        }

        return accumulatedPrice;
    }

    private static void FlushConsole(){
        System.out.flush();
    }

    private void FlushAndPrintTopInfo(){
        FlushConsole();
        if (this.CurrentStore != null) {
            System.out.println("Selected Store: " + this.CurrentStore.toString());
            if (this.CurrentRegister != null) {
                System.out.println("Currently on register №" + this.CurrentRegister.GetID());
                if (this.CurrentRegister.GetCashier() != null)
                    System.out.println("Being serviced by " + this.CurrentRegister.GetCashier().toString());
            }
            if(this.buyer != null) {
                System.out.println("Current balance: " + this.buyer.Money);
                if (this.buyer.Basket.size() > 0) {
                    System.out.println("Items currently in basket:");
                    this.PrintList(this.buyer.Basket);
                    System.out.println("Total: " + GetPriceFromList(this.buyer.Basket));
                }
            }
            System.out.println("\n");
        }
    }


    private void AskUserForStore(){
        boolean Done = false;

        do {
            try {
                int choice = 0;
                this.FlushAndPrintTopInfo();
                System.out.println("Choose a store you would like to go to.");

                for (int i = 0; i < this.Stores.size(); i++) {
                    System.out.println((i + 1) + ". " + this.Stores.get(i).toString());
                }

                do {
                    System.out.println("\nMust be a number between 1 and " + this.Stores.size());
                    choice = Integer.parseInt(this.GetInput());

                    if(!(choice > 0 && choice < this.Stores.size() + 1))
                        this.FlushAndPrintTopInfo();
                } while (!(choice > 0 && choice < this.Stores.size() + 1));

                this.CurrentStore = this.Stores.get(choice - 1);

                this.Stage++;
                Done = true;
            }
            catch (InputMismatchException ime) {
                System.out.println(ime.getLocalizedMessage());
                System.out.println("\nPress enter to try again...");
                Done = false;
                String temp = this.scanner.nextLine();
            }
            catch (NumberFormatException nfe) {
                System.out.println(nfe.getLocalizedMessage());
                System.out.println("\nPress enter to try again...");
                Done = false;
                String temp = this.scanner.nextLine();
            }
        } while (!Done);
    }

    private void AskUserMoney() throws InputMismatchException {
        boolean Done = false;

        do {
            try {
                BigDecimal money = BigDecimal.ZERO;
                this.FlushAndPrintTopInfo();
                System.out.println("Sir/Ma'am, how much money do you have on you?");

                do {
                    System.out.println("\nMust be a value starting from 0.1 and up");
                    money = new BigDecimal(this.GetInput());

                    if(money.compareTo(BigDecimal.ZERO) <= 0)
                        this.FlushAndPrintTopInfo();
                } while (!(money.compareTo(BigDecimal.ZERO) == 1));

                this.buyer = new Buyer(money);
                this.Stage++;
                Done = true;
            }
            catch (InputMismatchException ime) {
                System.out.println(ime.getLocalizedMessage());
                System.out.println("\nPress enter to try again...");
                Done = false;
                String temp = this.scanner.nextLine();
            }
            catch (NumberFormatException nfe) {
                System.out.println(nfe.getLocalizedMessage());
                System.out.println("\nPress enter to try again...");
                Done = false;
                String temp = this.scanner.nextLine();
            }
        } while (!Done);
    }

    private void AskForItemsToAddToBasket(){
        boolean Done = false;

        do {
            try {
                this.ChosenItem = "";
                int index = 0;
                int choice = 0;
                StringBuilder choices = new StringBuilder();

                this.FlushAndPrintTopInfo();
                System.out.println("Here's a list of all the items the store offers.\n");

                for (Map.Entry<String, List<Item>> entry : this.CurrentStore.storage.Items.entrySet()) {
                    choices.append(((index + 1) + ". " + entry.getKey() + ", In stock: " + entry.getValue().size() + "\n"));
                    index++;
                }

                do{
                    System.out.println(choices);
                    System.out.println("\nValue must be between 1 and " + (index + 1));
                    choice = Integer.parseInt(this.GetInput());

                    if(!(choice > 0 && choice < (index + 1)))
                        this.FlushAndPrintTopInfo();
                }   while(!(choice > 0 && choice < (index + 1)));

                index = 0;
                for (Map.Entry<String, List<Item>> entry : this.CurrentStore.storage.Items.entrySet()) {
                    if (index == choice - 1) {
                        this.ChosenItem = entry.getKey();
                        break;
                    }
                    index++;
                }
            }
            catch (InputMismatchException ime) {
                System.out.println(ime.getLocalizedMessage());
                System.out.println("\nPress enter to try again...");
                Done = false;
                String temp = this.scanner.nextLine();
            }
            catch (NumberFormatException nfe) {
                System.out.println(nfe.getLocalizedMessage());
                System.out.println("\nPress enter to try again...");
                Done = false;
                String temp = this.scanner.nextLine();
            }

            Done = this.AskForSpecificItems();
        } while(!Done);

        this.ChosenItem = "";
        this.Stage++;
    }

    private boolean AskForSpecificItems(){
        boolean Done = false;

        do{
            try{
                int choice = 0, size = this.CurrentStore.storage.Items.get(this.ChosenItem).size();

                this.FlushAndPrintTopInfo();
                System.out.println("Choose from all the available " + this.ChosenItem + "s/es");

                do{
                    this.PrintList(this.CurrentStore.storage.Items.get(this.ChosenItem));
                    System.out.println("\nMust be a value between 1 and " + this.CurrentStore.storage.Items.get(this.ChosenItem).size());
                    choice = Integer.parseInt(this.GetInput());

                    if(!(choice > 0 && choice < size + 1))
                        this.FlushAndPrintTopInfo();
                }  while (!(choice > 0 && choice < size + 1));

                this.buyer.Basket.add(this.CurrentStore.storage.Items.get(this.ChosenItem).get(choice - 1));

                Done = this.PromptIfDone("Are you done adding " + this.ChosenItem + "s/es");
            }
            catch (InputMismatchException ime) {
                System.out.println(ime.getLocalizedMessage());
                System.out.println("\nPress enter to try again...");
                Done = false;
                String temp = this.scanner.nextLine();
            }
            catch (NumberFormatException nfe) {
                System.out.println(nfe.getLocalizedMessage());
                System.out.println("\nPress enter to try again...");
                Done = false;
                String temp = this.scanner.nextLine();
            }
        } while(!Done);

        return this.PromptIfDone("Are you done adding items to the basket");
    }

    private void GoToCashRegister() {
        boolean Done = false;

        do {
            try {
                this.FlushAndPrintTopInfo();
                System.out.println("Select cash register to go to: ");
                int choice = 0;

                do {
                    this.PrintList((ArrayList<CashRegister>) this.CurrentStore.CashRegisters);
                    System.out.println("\nMust be a number starting from 1 and up to " + this.CurrentStore.CashRegisters.size());
                    choice = Integer.parseInt(this.GetInput());


                    if(!(choice > 0 && choice < this.CurrentStore.CashRegisters.size() + 1)) {
                        FlushConsole();
                        System.out.println("The number you entered is out of the given range.");
                        System.out.println("\nPress enter to try again...");
                        String bruh = this.scanner.nextLine();
                        this.FlushAndPrintTopInfo();
                    }
                    else {
                        if (this.CurrentStore.CashRegisters.get(choice - 1).GetCashier() == null) {
                            FlushConsole();
                            System.out.println("Cash Register does not have a cashier operating it.");
                            System.out.println("\nPress enter to try again...");
                            String bruh = this.scanner.nextLine();
                            choice = 0;
                            this.FlushAndPrintTopInfo();
                        }
                    }
                } while (!(choice > 0 && choice < this.CurrentStore.CashRegisters.size() + 1));

                this.CurrentRegister = this.CurrentStore.CashRegisters.get(choice - 1);
                this.Stage++;
                Done = true;
            }
            catch (InputMismatchException ime) {
                System.out.println(ime.getLocalizedMessage());
                System.out.println("\nPress enter to try again...");
                Done = false;
                String temp = this.scanner.nextLine();
            }
            catch (NumberFormatException nfe) {
                System.out.println(nfe.getLocalizedMessage());
                System.out.println("\nPress enter to try again...");
                Done = false;
                String temp = this.scanner.nextLine();
            }
        } while (!Done);
    }

    private void CheckoutItems() {
        this.CurrentRegister.SetBuyer(this.buyer);
        try {
            this.CurrentRegister.Register.start();
            this.CurrentRegister.Register.join();
        }
        catch (final Exception e){

        }
        this.ExpiredNotification();
        FlushConsole();
        System.out.println(this.CurrentRegister.CheckoutItems(this.CurrentStore));
        this.Stage++;
    }

    public void ExpiredNotification(){
        if(this.CurrentRegister.ExpiredProductsNotification){
            boolean Done = false;
            int choice = 0;

            do{
                try{
                    do{
                        this.FlushAndPrintTopInfo();
                        System.out.println("It seems some of the items you got are expired.\n");
                        this.PrintList(this.CurrentRegister.ExpiredItems);
                        System.out.println("\nWhat would you like to do with them?");
                        System.out.println("1. Replace all items with non-expired ones");
                        System.out.println("2. Remove from marked items.");

                        choice = Integer.parseInt(this.GetInput());

                        if(!((choice > 0) && (choice < 3))){
                            this.FlushAndPrintTopInfo();
                            System.out.println("The choice you tried to select does not exist.");
                        }
                    }while(!((choice > 0) && (choice < 3)));

                    if (choice == 1){
                        this.TryReplaceExpiredItems();
                    }
                    else if (choice == 2){
                        for (Item item : this.CurrentRegister.ExpiredItems) {
                            this.CurrentStore.storage.RemoveItem(item);
                            this.buyer.RemoveItemFromBasket(item);
                        }
                        this.CurrentRegister.ExpiredItems.clear();
                        this.CurrentRegister.ResetExpiredNotification();
                    }
                    Done = true;
                }
                catch (InputMismatchException ime) {
                    System.out.println(ime.getLocalizedMessage());
                    System.out.println("\nPress enter to try again...");
                    Done = false;
                    String temp = this.scanner.nextLine();
                }
                catch (NumberFormatException nfe) {
                    System.out.println(nfe.getLocalizedMessage());
                    System.out.println("\nPress enter to try again...");
                    Done = false;
                    String temp = this.scanner.nextLine();
                }
            }while(!Done);
        }
    }

    private void TryReplaceExpiredItems() {
        for (Item item : this.CurrentRegister.ExpiredItems) {
            System.out.println("Replacing " + item.toString());
            System.out.println("Status: " + this.ReplaceExpiredItem(item) + "\n");
        }
        this.CurrentRegister.ExpiredItems.clear();
        this.CurrentRegister.ResetExpiredNotification();
        try {
            this.CurrentRegister.Register.start();
            this.CurrentRegister.Register.join();
        }
        catch (final Exception e){

        }
    }

    private String ReplaceExpiredItem(Item item){
        String status = "";

        this.CurrentStore.storage.Items.get(item.GetCategoryName()).remove(item);

        if (this.CurrentStore.storage.Items.get(item.GetCategoryName()).size() > 0) {
                for (Item temp : this.CurrentStore.storage.Items.get(item.GetCategoryName())) {
                    if (!temp.IsExpired()) {
                        if (this.buyer.AddItemToBasket(temp)) {
                            StringBuilder newStatus = new StringBuilder("Replaced with " + temp.toString());
                            status = newStatus.toString();
                            break;
                        } else {
                            status = "Not replaced - out of stock";
                        }
                    }
                }
            this.buyer.Basket.remove(item);
        }
        else {
            status = "Not replaced - out of stock";
            this.buyer.Basket.remove(item);
        }


        return status;
    }

    private void AskForStartAgain() {
        if (this.PromptIfDone("Would you like to shop again?"))
            this.Reset();
        else
            this.Stop();
    }

    public void Stop(){
        this.isRunning = false;
    }
}