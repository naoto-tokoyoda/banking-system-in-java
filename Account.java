import java.util.ArrayList;

public class Account {

    //the name of this account such as saving or checking account.
    private String name;

    //the accountID of this account.
    private String accountID;

    //holder who owns by user with this account
    private Customer holder;

    //store transaction history here
    private ArrayList<Transaction> transaction;

    //constructor that create new account
    public Account(String name, Customer holder, Bank theBank){
        this.name = name;
        this.holder = holder;

        //get new account UUID
        this.accountID = theBank.getNewAccountID();

        //initialize transaction
        this.transaction = new ArrayList<Transaction>();
    }

    public String getAccountID(){
        return this.accountID;
    }



    //show information accountID, balance and name
    public String getSummaryOfAccount(){

        double balance = this.getBalance();
        StringBuffer accountID = new StringBuffer(this.accountID);
        accountID.insert(5, "-");

        if(balance <= 0){
            return String.format("%s account | Account number: %s | Balance: $%.02f", this.name, accountID, balance);
        } else {
            return String.format("%s account | Account number: %s | Balance: $(%.02f)", this.name, accountID, balance);
        }
    }

    public double getBalance(){
        double balance = 0;

        //add amount into the account
        for(Transaction t : this.transaction){
            balance = balance + t.getBalance();
        }
        return balance;
    }

    //show transaction of the account
    public void printTransactionHistory(){
        System.out.printf("----- %s's transaction history with %s account-----", holder.getFirstName(), this.name );
        System.out.println();

        if(this.transaction.size() == 0){
            System.out.println("No transaction history");
        }

        for(int i = this.transaction.size()-1; i >= 0;  i-- ){
            System.out.println( this.transaction.get(i).sumOfTransaction() );
        }
    }

    //add transaction
     public void addTransaction(double amount, String note){
        Transaction newTransaction = new Transaction(amount, note, this);
        this.transaction.add(newTransaction);
     }

     //get first name
     public String getFirstName(){
        return holder.getFirstName();
     }

}
