package clase3;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class Transaction {
    private double amount;
    private String type; // "deposit" o "withdrawal"
    private String date;

    public Transaction(double amount, String type, String date) {
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }
}

