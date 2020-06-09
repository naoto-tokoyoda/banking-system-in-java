import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Customer {

    //customer first name
    private String firstName;

    //customer last name
    private String lastName;

    //create customer ID.
    private String customerID;

    //pin code
    private byte pinCode[];

    //create the list of account for storing
    private ArrayList<Account> account;



    //constructor that creating new user
    public Customer(String firstName, String lastName, String pinCode, Bank theBank){

        this.firstName = firstName;
        this.lastName = lastName;

        //change pin number to MD5 (Message digest algorithm) for security reason
        //MessageDigest belongs to the package java.security.
        //
        //it could be anything inside of the string
        try {

            //Create a MessageDigest object
            MessageDigest md = MessageDigest.getInstance("MD5");

            //"this.pinCode" will change the pin number with message digest algorithm as encrypted
            this.pinCode = md.digest(pinCode.getBytes());

        } catch (NoSuchAlgorithmException e) {
            //error here
            System.out.println("No Such Algorithm Exception");
            e.printStackTrace();

            //exit(0) : Generally used to indicate successful termination.
            //exit(1) or exit(-1) or any other non-zero value – Generally indicates unsuccessful termination.
            System.exit(-1);
        }

        //create new customer ID for user
        this.customerID = theBank.getNewCustomerID();

        //create new account
        this.account = new ArrayList<Account>();


        //print information
        System.out.println("First name : " + firstName);
        System.out.println("Last name : " + lastName);

        //to show customer. Add " - " after 5th digit
        StringBuffer customerID = new StringBuffer(this.customerID);
        customerID.insert(5, "-");
        System.out.println("Customer ID : " + customerID);


    }

    //store the created account from Account class to account array list
    //check constructor above
    public void addAccount(Account anAccount){
        this.account.add(anAccount);
    }

    public String getCustomerID(){
        return this.customerID;
    }

    public boolean authPinCode(String pin){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            return MessageDigest.isEqual( md.digest ( pin.getBytes() ), this.pinCode);

        } catch (NoSuchAlgorithmException e) {

            //error here
            System.out.println("No Such Algorithm Exception");
            e.printStackTrace();

            //exit(0) : Generally used to indicate successful termination.
            //exit(1) or exit(-1) or any other non-zero value – Generally indicates unsuccessful termination.
            System.exit(-1);
        }//try and catch

        return false;
    }//authPinNum

    public String getFirstName(){
        return firstName;
    }
    public String getLastName() { return lastName;}

    public void sumOfAccount(){
        System.out.println( "----- " + this.firstName + "'s account -----");

        for(int i =0; i < this.account.size(); i++){
            System.out.printf("%d. %s\n", i, this.account.get(i).getSummaryOfAccount());
        }
        System.out.println();
    }

    public int numberOfAccounts(){
        return this.account.size();
    }

    //show the specific account index
    public void printTransactionHistory(int accountIndex){

        this.account.get(accountIndex).printTransactionHistory();
    }

    //get the information about account balance
    public double getAccountBalance(int accountIndex){
        return this.account.get(accountIndex).getBalance();
    }

    //get accountID
    public String getAccountID(int accountIndex){
        return this.account.get(accountIndex).getAccountID();
    }



    public void addAccountTransaction(int accountIndex, double amount, String note){

        this.account.get(accountIndex).addTransaction(amount, note);
    }

    public byte[] getPinCode() {
        return pinCode;
    }


}//User
