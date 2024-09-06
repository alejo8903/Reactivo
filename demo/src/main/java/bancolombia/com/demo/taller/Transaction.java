package bancolombia.com.demo.taller;

import reactor.core.publisher.Mono;

public class Transaction {
    private int amount;

    public Transaction(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    private static Mono<String> getTransactionDetails(Transaction tx) {
        // Supongamos que esto hace una llamada asíncrona para obtener detalles
        return Mono.just("Details for transaction: " + tx.getAmount());
    }

    private static String getCustomerInfo(int customerId) {
        // Supongamos que esto hace una llamada para obtener información del cliente
        return "Customer info for ID: " + customerId;
    }
}
