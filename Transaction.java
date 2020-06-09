import java.util.Date;

public class Transaction {

    //transaction that has amount in this account
    private double amount;

    //this shows time when customer transact
    private Date stamp;

    //this is note when customer transact
    private String note;

    //the account in this transaction
    private Account inAccount;

    //constructor without note
    public Transaction(double amount, Account inAccount){
        this.amount = amount;
        this.inAccount = inAccount;
        this.stamp = new Date();
        this.note = "";
    }

    //constructor with note
    public Transaction(double amount, String note, Account inAccount){
        this(amount, inAccount);
        this.note = note;
    }

     public double getBalance(){
        return this.amount;
     }

     //this shows summary of transaction
     public String sumOfTransaction(){

        if(this.amount >= 0){
            return String.format("Date: %s | Amount: $%.02f | Note: %s", this.stamp.toString(), this.amount, this.note);
        } else {
            return String.format("Date: %s | Amount: $(%.02f) | Note: %s", this.stamp.toString(), this.amount, this.note);
        }
     }

}
