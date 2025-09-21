public class BankAccount{
    private double balance;
    private String AccHolderName;
    private int AccNo;
    public BankAccount(int AccNo,String AccHolderName,double balance){
        this.AccNo = AccNo;
        this.AccHolderName = AccHolderName;
        this.balance = balance;
    }
    public void deposit(double amount){
        balance += amount;
    }
    public void withdraw(double amount) throws Exception{
        if(amount<=balance){
            balance -= amount;
        }
        else{
            throw new Exception("Insufficient Balance \n");
        }
    }
    public double getBalance(){
        return balance;
    }
    public int getAccNo(){
        return AccNo;
    }
    public void displayAccInfo(){
        System.out.println("Acc No : " + AccNo);
        System.out.println("Acc Holder Name : " + AccHolderName);
        System.out.println("Balance : " + balance);
    }
    public String toFileString() {
        return AccNo + "," + AccHolderName + "," + balance;
    }

    public static BankAccount fromFileString(String line) {
        String[] parts = line.split(",");
        int accNumber = Integer.parseInt(parts[0]);
        String name = parts[1];
        double balance = Double.parseDouble(parts[2]);
        return new BankAccount(accNumber, name, balance);
    }

}
