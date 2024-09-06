package bancolombia.com.demo.taller;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.List;

public class Main {
    private static Mono<String> getTransactionDetails(Transaction tx) {
        return Mono.just("Details for transaction: " + tx.getAmount());
    }

    private static String getCustomerInfo(int customerId) {
        return "Customer info for ID: " + customerId;
    }

    public static void main(String[] args) {


        Flux<Transaction> transactions = Flux.just(new Transaction(100), new Transaction(200));
        Flux<Integer> rewards = transactions.map(tx -> tx.getAmount() * 2);
        rewards.subscribe(System.out::println);

        Flux<Transaction> transactions2 = Flux.just(new Transaction(50), new Transaction(150));
        Flux<Transaction> largeTransactions = transactions2.filter(tx -> tx.getAmount() > 100);
        largeTransactions.subscribe(tx -> System.out.println(tx.getAmount()));


        Flux<Transaction> transactions3 = Flux.just(new Transaction(100), new Transaction(200));
        Flux<String> transactionDetails = transactions3.flatMap(tx -> getTransactionDetails(tx));
        transactionDetails.subscribe(System.out::println);

        Flux<Transaction> transactions4 = Flux.just(new Transaction(100), new Transaction(200));
        Flux<User> users = Flux.just(new User("Alice"), new User("Bob"));
        Flux<String> transactionUserDetails = Flux.zip(transactions4, users, (tx, user) -> user.getName() + " made a transaction of " + tx.getAmount());
        transactionUserDetails.subscribe(System.out::println);

        Flux<Transaction> account1Transactions = Flux.just(new Transaction(100), new Transaction(200));
        Flux<Transaction> account2Transactions = Flux.just(new Transaction(300), new Transaction(400));
        Flux<Transaction> allTransactions = Flux.merge(account1Transactions, account2Transactions);
        allTransactions.subscribe(tx -> System.out.println(tx.getAmount()));

        Flux<Transaction> transactions5 = Flux.just(new Transaction(100), new Transaction(200), new Transaction(300));
        Mono<List<Transaction>> transactionList = transactions5.collectList();
        transactionList.subscribe(list -> System.out.println("Collected " + list.size() + " transactions"));

        Flux<Transaction> transactions6 = Flux.just(new Transaction(100), new Transaction(200), new Transaction(300));
        Mono<Integer> totalAmount = transactions6.map(Transaction::getAmount).reduce(0, Integer::sum);
        totalAmount.subscribe(System.out::println);

        Flux<String> account1Notifications = Flux.just("Tx1: $100", "Tx2: $200");
        Flux<String> account2Notifications = Flux.just("Tx3: $300", "Tx4: $400");
        Flux<String> allNotifications = account1Notifications.mergeWith(account2Notifications);
        allNotifications.subscribe(System.out::println);

        Flux<Transaction> day1Transactions = Flux.just(new Transaction(100), new Transaction(200));
        Flux<Transaction> day2Transactions = Flux.just(new Transaction(300), new Transaction(400));
        Flux<Transaction> allTransactions2 = day1Transactions.concatWith(day2Transactions);
        allTransactions2.subscribe(tx -> System.out.println(tx.getAmount()));

        Flux<Transaction> transactions7 = Flux.empty();
        Flux<Transaction> transactionsWithDefault = transactions7.switchIfEmpty(Flux.just(new Transaction(0)));
        transactionsWithDefault.subscribe(tx -> System.out.println(tx.getAmount()));

        Flux<Transaction> transactions8 = Flux.just(new Transaction(100), new Transaction(200), new Transaction(300), new Transaction(400), new Transaction(500));
        Flux<Transaction> firstThreeTransactions = transactions8.take(3);
        firstThreeTransactions.subscribe(tx -> System.out.println(tx.getAmount()));

        Flux<Transaction> transactions9 = Flux.just(new Transaction(100), new Transaction(200), new Transaction(300), new Transaction(400), new Transaction(500));
        Flux<Transaction> lastTwoTransactions = transactions9.takeLast(2);
        lastTwoTransactions.subscribe(tx -> System.out.println(tx.getAmount()));

        Flux<Transaction> transactions10 = Flux.just(new Transaction(100), new Transaction(200), new Transaction(300), new Transaction(400), new Transaction(500));
        Flux<Transaction> remainingTransactions = transactions10.skip(3);
        remainingTransactions.subscribe(tx -> System.out.println(tx.getAmount()));

        Flux<Transaction> transactions11 = Flux.just(new Transaction(100), new Transaction(200), new Transaction(300), new Transaction(400), new Transaction(500));
        Flux<Transaction> initialTransactions = transactions11.skipLast(2);
        initialTransactions.subscribe(tx -> System.out.println(tx.getAmount()));

        Mono<String> customerInfo = Mono.fromCallable(() -> getCustomerInfo(12345));
        customerInfo.subscribe(System.out::println);


    }

}
