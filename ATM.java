

import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        Scanner sn = new Scanner(System.in);


        /*
            user1
        */

        //initialize Bank class
        Bank theBank = new Bank("the bank of Japan");



        //create new customer. this creates saving account only
        Customer customer1 = theBank.addCustomer("Naoto", "Tokoyoda", "4321");

        //create checking account for customer
        Account newAccount1 = new Account("Checking", customer1, theBank);
        customer1.addAccount(newAccount1);
        theBank.addAccount(newAccount1);

        /*
            customer pin code is encrypted
            System.out.printf("%s", customer1.getPinCode());

        */



        System.out.println();

        /*
            user2
        */

        //create new customer. this creates saving account only
        Customer customer2 = theBank.addCustomer("somchai", "sonbun", "1234");

        //create checking account for customer
        Account newAccount2 = new Account("Checking", customer2, theBank);
        customer2.addAccount(newAccount2);
        theBank.addAccount(newAccount2);

        /*
            customer pin code is encrypted
            System.out.printf("%s", customer2.getPinCode());
        */





        authentication(theBank, sn);

    }



    //authentication with theBank and scanner
    public static void authentication(Bank theBank, Scanner sn){
        //user try to login
        Customer userTry;

        while(true) {

            //show start menu with bank name
            userTry = ATM.startMenu(theBank, sn);

            //show customer menu
            ATM.userMenu(userTry, theBank, sn);

        }
    }



    //start menu
    public static Customer startMenu(Bank theBank, Scanner sn) {

        //initialize
        String customerID;
        String pinCode;
        int  counter = 0;
        Customer customerAuthentication;

        //show login and pin number for customer
        do {

            System.out.println();
            System.out.println("Welcome to " + theBank.getTheBankName() + " service" );
            //get customer ID
            System.out.print("Enter customer ID: ");
            customerID = sn.next();

            //get pin code
            System.out.print("Enter PIN code: ");
            pinCode = sn.next();

            //this goes to Bank class and check the customer ID and pin number exist or  not
            customerAuthentication = theBank.loginSystem(customerID, pinCode);

            if(customerAuthentication == null){
                System.out.println("Customer ID or pin number does not match. Please try it again.");
            }

            counter++;
            if(counter >= 3){
                System.out.println("You have exceeded the number of attempts");
                System.exit(0);
            }

        } while(customerAuthentication == null);

        return customerAuthentication;
    }//startMenu


    public static void userMenu(Customer customer,Bank theBank, Scanner sn ){

        System.out.println();
        //show customer's account
        customer.sumOfAccount();

        //initialize
        int decision = 0;

        String menus[] = {"Deposit", "Withdraw", "Transfer", "Transaction History", "Logout", "Cancel"};

        do {
            //show customer menu
            System.out.println("Hello, " + customer.getFirstName() + "! How can I help for you?");

            //print menu
            for(int i = 0; i < menus.length; i++){
                System.out.println( i + ". " + menus[i]);
            }

            System.out.println();
            System.out.println("Enter index number: ");
            String input = sn.next();

            //integer input is only accepted
            try {
                decision = Integer.valueOf(input);
            } catch (NumberFormatException ne) {
                System.out.println("Text is detected. Please try it again");
                userMenu(customer, theBank, sn);
            }


            switch(decision){
                case 0:
                    System.out.println("Deposit");
                    ATM.depositTransaction(customer,theBank, sn);
                    break;
                case 1:
                    System.out.println("Withdraw");
                    ATM.withdrawTransaction(customer,theBank, sn);
                    break;
                case 2:
                    System.out.println("Transfer");
                    ATM.transferTransaction(customer,theBank,sn);
                    break;
                case 3:
                    System.out.println("Transaction History");
                    ATM.showTransactionHistory(customer, sn);
                    break;
                case 4:
                    System.out.println("logout");
                    ATM.authentication(theBank, sn);
                    break;
                case 5:
                    System.out.println("Transaction canceled");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid number. Please try it again.");
                    ATM.userMenu(customer,theBank,sn);
                    break;
            }

        } while (decision >= 0 || decision <= 5 );


    }//userMenu


    //deposit transaction
    public static void depositTransaction(Customer customer,Bank theBank, Scanner sn){
        //initialize
        int toAccount = 0;
        String note;
        double accountBalance;
        double amount = 0;

        //show account
        customer.sumOfAccount();

        //get the account customer want to deposit
        do {
            System.out.println("Which account would you like to deposit? Enter the index number: ");
            String input = sn.next();

            //integer input is only accepted
            try {
                toAccount = Integer.parseInt(input);
            } catch (NumberFormatException ne) {
                System.out.println("Text is detected. Please try it again");
                depositTransaction(customer, theBank, sn);
            }

            //if toAccount is less than 0 or more than customer's account
            if(toAccount < 0 || toAccount >= customer.numberOfAccounts()){
                System.out.println("Invalid number. Please try it again.");
            }

        } while(toAccount < 0 || toAccount >= customer.numberOfAccounts());

        //show customer's account balance
        accountBalance = customer.getAccountBalance(toAccount);

        //get the amount customer have in the bank account
        do{
            System.out.println("Your account has $" + accountBalance);
            System.out.println("How much you want to deposit? : ");
            String input = sn.next();

            //double(int) input is only accepted
            try {
                amount = Double.parseDouble(input);
            } catch (NumberFormatException ne) {
                System.out.println("Text is detected. Please try it again.");
                depositTransaction(customer, theBank, sn);
            }


            if(amount < 0){
                System.out.println("Invalid number is entered.");
            }

            //create a note
            sn.nextLine();
            System.out.println("Type what you want for note: ");
            note = sn.nextLine();

        } while(amount < 0);

        //store deposit transaction
        customer.addAccountTransaction(toAccount, amount, note);

        //back to userMenu
        ATM.userMenu(customer,theBank,sn);
    }//depositTransaction

    //show transaction history
    public static void showTransactionHistory(Customer customer, Scanner sn){

        //initialize
        int decision = 0;

        do {

            customer.sumOfAccount();
            System.out.println("Which account you want to check?");
            System.out.println("Enter index number : ");
            String input = sn.next();

            //integer input is only accepted
            try {
                decision = Integer.parseInt(input);
            } catch (NumberFormatException ne) {
                System.out.println("Text is detected. Please try it again.");
                showTransactionHistory(customer, sn);
            }


            //if customer type number is less than 0 or more than customer's account
            if(decision < 0 || decision >= customer.numberOfAccounts()){
                System.out.println("Invalid number. Please try it again");
            }


        } while (decision < 0 || decision >= customer.numberOfAccounts());

        //show transaction history that customer wants to see from account
        customer.printTransactionHistory(decision);
        System.out.println();

    }//showTransactionHistory


    //transfer transaction
    public static void transferTransaction(Customer customer, Bank theBank, Scanner sn){
        //initialize
        int toAccount = 0;
        int fromAccount = 0;
        double accountBalance;
        double amount = 0;


        //show user accounts
        customer.sumOfAccount();

        //get the account customer want to transfer from
        do {
            System.out.println("Enter the index number of the account you want to transfer from: ");
            String input = sn.next();

            //integer input is only accepted
            try {
                fromAccount = Integer.parseInt(input);
            } catch (NumberFormatException ne) {
                System.out.println("Text is detected. Please try it again");
                transferTransaction(customer, theBank, sn);
            }


            if(fromAccount < 0 || fromAccount >= customer.numberOfAccounts()){
                System.out.println("Invalid number. Please try it again");
            }

        } while(fromAccount < 0 || fromAccount >= customer.numberOfAccounts());

        accountBalance = customer.getAccountBalance(fromAccount);

        //show all bank account except user selected account
        theBank.getAllAccountID(fromAccount);

        //get the account customer want to transfer to
        do {
            System.out.println("Enter the index number of the account you want to transfer to: ");
            String input = sn.next();

            //integer input is only accepted
            try {
                toAccount = Integer.parseInt(input);
            } catch (NumberFormatException ne) {
                System.out.println("Text is detected. Please try it again");
                transferTransaction(customer, theBank, sn);
            }

            //if toAccount is less than 0 or greater than all account exist
            if(toAccount < 0 || toAccount >= theBank.numberOfAccounts()){
                System.out.println("Invalid number. Please try it again");
            }

        } while(toAccount < 0 || toAccount >= theBank.numberOfAccounts());


        //get the amount customer have in the bank account
        do{
            System.out.println("Your account has $" + accountBalance);
            System.out.println("How much you want to transfer? : ");

            String input = sn.next();

            //double(int) input is only accepted
            try {
                amount = Double.parseDouble(input);
            } catch (NumberFormatException ne) {
                System.out.println("Text is detected. Please try it again.");
                depositTransaction(customer, theBank, sn);
            }

            //check amount customer types is valid or not
            if(amount < 0){
                System.out.println("Invalid number is  entered.");
            } else if(amount > accountBalance){
                System.out.println("Invalid number is entered");
            }

        } while(amount < 0 || amount > accountBalance);

        //decrease money for sender
        customer.addAccountTransaction(fromAccount, -1 * amount, String.format("Transfer to " + theBank.getFirstName(toAccount) + "'s account: " + theBank.getAccountID(toAccount)));

        //increase money for receiver
        theBank.addAccountTransaction(toAccount, amount, String.format("Transfer from account: " + customer.getAccountID(fromAccount) + " by " + customer.getFirstName()));

    }//Transfer transaction


    //withdraw transaction
    public static void withdrawTransaction(Customer customer,Bank theBank,Scanner sn){
        //initialize
        int fromAccount = 0;
        String note;
        double accountBalance;
        double amount = 0;

        //show account
        customer.sumOfAccount();
        //get the account customer want to transfer from
        do {
            System.out.println("Enter the index number of the account you want to withdraw: ");

            String input = sn.next();

            //integer input is only accepted
            try {
                fromAccount = Integer.parseInt(input);
            } catch (NumberFormatException ne) {
                System.out.println("Text is detected. Please try it again");
                withdrawTransaction(customer, theBank, sn);
            }

            //if fromAccount is less than 0 or greater than all account exist
            if(fromAccount < 0 || fromAccount >= customer.numberOfAccounts()){
                System.out.println("Invalid number. Please try it again");
            }

        } while(fromAccount < 0 || fromAccount >= customer.numberOfAccounts());

        accountBalance = customer.getAccountBalance(fromAccount);

        //get the amount customer have in the bank account
        do{
            System.out.println("Your account has $" + accountBalance);
            System.out.println("How much you want to withdraw? : ");

            String input = sn.next();

            //double(int) input is only accepted
            try {
                amount = Double.parseDouble(input);
            } catch (NumberFormatException ne) {
                System.out.println("Text is detected. Please try it again.");
                depositTransaction(customer, theBank, sn);
            }

           //check amount customer types is valid or not
            if(amount < 0){
                System.out.println("Invalid number is  entered.");
            } else if(amount > accountBalance){
                System.out.println("Invalid number is entered");
            }

        } while(amount < 0 || amount > accountBalance);

        //create a note
        sn.nextLine();
        System.out.println("Type what you want for note: ");
        note = sn.nextLine();

        System.out.println();
        //create withdraw function
        customer.addAccountTransaction(fromAccount, -amount, note);
        ATM.userMenu(customer,theBank,sn);
    }
}//ATM
