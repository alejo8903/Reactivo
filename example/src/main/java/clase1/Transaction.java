package clase1;

import java.util.Date;

public class Transaction {
    private Double amount;
    private String type;
    private Date date;

    public Transaction() {
    }

    public Transaction(Double amount, String typeTransaction, Date date) {
        this.amount = amount;
        this.type = typeTransaction;
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String typeTransaction) {
        this.type = typeTransaction;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
