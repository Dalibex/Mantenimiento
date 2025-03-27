package bank;

public class BankAccount {
    private int balance = 0;
    public BankAccount(int startingBalance) {
        this.balance = startingBalance;
    }
    public boolean withdraw(int amount) {
        if(balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }
    public int deposit(int amount) {
        if (amount <0)
            throw new IllegalArgumentException("Amount cannot be negative");
        balance += amount;
        return balance;
    }
    public int getBalance() {
        return balance;
    }

    // Calculate the payment per month for a loan
    public double payment(double total_amount, double interest, int npayments){
        return total_amount*(interest*Math.pow((1+interest), npayments)/(Math.pow((1+interest), npayments)-1));
    }

    // Calculate the pending amount for a loan in a month
    public double pending (double amount, double inte, int npayments, int month){
        double res;
        if(month==0){
            res=amount;
        }else{
            double ant=pending(amount, inte, npayments, month-1);
            res = ant - (payment(amount,inte,npayments) - inte*ant);
        }
        return res;
    }

    public static void main(String args[]){
        BankAccount bank =new BankAccount(10);
        double total_amount =10000; 
        double interes = 0.001;
        int months = 12;

        System.out.printf("Load payment of amount %f, with interes %f and in %d months is: %f\n", total_amount, 
            interes, months, bank.payment(total_amount, interes, months));

        System.out.printf("Load pending payment of amount %f, with interes %f, %d months, in month %d is: %f\n", total_amount, 
            interes, months, 2, bank.pending(total_amount, interes, months, 2));
    }
}
