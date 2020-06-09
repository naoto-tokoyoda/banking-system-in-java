import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private String name;
    private ArrayList<Customer> customer;
    private ArrayList<Account> account;


    //constructor with only name. there is empty customer and account.
    public Bank(String name){
        this.name = name;
        this.customer = new ArrayList<Customer>();
        this.account = new ArrayList<Account>();
    }

    //create UUID for customer
    public String getNewCustomerID(){

        //initialize customerID
        String customerId;
        Random rd = new Random();

        int idLength = 10;
        boolean notUnique = false;

        //loop for generating customerID
        do {
            //create ID
            customerId = "";
            for(int i = 0; i < idLength; i++){
                //integer range is 0 to 10
                customerId +=  ((Integer)rd.nextInt(10)).toString();
            }

            //check if the customerID is unique or not in Customer Class one by one
            //this is for avoiding duplication of customerID
            for(Customer customer : this.customer ){
                if(customerId.compareTo(customer.getCustomerID()) == 0){
                    notUnique = true;
                    break;
                }
            }
        } while(notUnique);//if notUnique is true, its loop is out.

        return customerId;
    }

    public String getNewAccountID(){

        //initialize ID
        String ID;
        Random rd = new Random();

        int IDlength = 10;
        boolean notUnique = false;

        //looping until UUID is got
        do {
            //create UUID
            ID = "";
            for(int i = 0; i < IDlength; i++){
                ID +=  ((Integer)rd.nextInt(10)).toString();
            }

            //ID is unique or not?
            //check account in Account class one by one
            for(Account account : this.account ){
                if(ID.compareTo(account.getAccountID()) == 0){
                    notUnique = true;
                    break;
                }
            }
        } while(notUnique);

        return ID;
    }

    public void addAccount(Account anAccount){
        this.account.add(anAccount);
    }



    //create new customer for the bank
    public Customer addCustomer(String firstName, String secondName, String pinCode){

        //create new customer and store it to Customer array list
        Customer newUser = new Customer(firstName,secondName,pinCode,this);
        this.customer.add(newUser);

        //create new account and store it to Account array list.
        //I set savings for first argument.
        Account newAccount = new Account("Savings", newUser, this);

        newUser.addAccount(newAccount);
        this.account.add(newAccount);

        return newUser;
    }

    public Customer loginSystem(String customerID, String pinCode){

        //check customer trying to login from the existed customer in Customer class
        for(Customer customer: this.customer){
            //if trying customer ID same as
            //compareTO() means if both the strings are equal then this method returns 0 else it returns positive or negative value.
            if(customer.getCustomerID().compareTo(customerID) == 0 && customer.authPinCode(pinCode)){
                return customer;
            }
        }
        //if userID and pin number dose not match, return null.
        return null;
    }

    //to get the bank name
    public String getTheBankName(){
        return this.name;
    }




    //get all account ID, not showing users original account
    public void getAllAccountID(int fromAccount){
        String customerAccountID =  getAccountID(fromAccount);

        for(int i = 0; i < account.size(); i++){

            //if logged in customer accountID match, it will skip.
            if( customerAccountID.equals( account.get(i).getAccountID() ) ){
                continue;
            }
            System.out.printf("%d. Name: %s | Account ID: %s \n", i, account.get(i).getFirstName(),account.get(i).getAccountID());
        }


    }

    //show how many accounts there are
    public int numberOfAccounts(){
        return this.account.size();
    }


    public void addAccountTransaction(int accountIndex, double amount, String note){

        this.account.get(accountIndex).addTransaction(amount, note);
    }

    //get account ID
    public String getAccountID(int accountIndex){
        return this.account.get(accountIndex).getAccountID();
    }

    //get first name
    public String getFirstName(int accountIndex){
        return this.account.get(accountIndex).getFirstName();
    }


}
