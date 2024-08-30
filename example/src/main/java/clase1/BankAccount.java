package clase1;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {

    public List<Transaction> transactions;
    public BankAccount() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public double getTotalBalance() {
        double totalBalance = 0;
        for (Transaction transaction : transactions) {
            if (transaction.getType().equals("deposit")) {
                totalBalance += transaction.getAmount();
            } else if (transaction.getType().equals("withdrawal")) {
                totalBalance -= transaction.getAmount();
            }
        }
        return totalBalance;
    }

    public List<Transaction> getDeposits() {
        List<Transaction> deposits = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getType().equals("deposit")) {
                deposits.add(transaction);
            }
        }
        return deposits;
    }

    public Double getTotalBalanceFunctional() {
        return transactions.stream()
                .map(transaction -> transaction.getType()
                        .equals("deposit") ? transaction.getAmount() : -transaction.getAmount())
                .reduce(0.0, Double::sum);
    }
}
