package clase1;

import clase1.BankAccount;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Integer> numeros = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Function<Integer, Integer> cuadrado = x -> x * x;
        Function<Integer, Integer> doble = x -> x * 2;

        // Composición de funciones el compose ejecuta la función que se le pasa como argumento y luego la función que lo llama
        Function <Integer, Integer> dobleCuadrado = cuadrado.compose(doble);
        System.out.println(dobleCuadrado.apply(3)); // 36
        // Composición de funciones el andThen ejecuta la función que lo llama y luego la función que se le pasa como argumento
        Function<Integer, Integer> cuadradoDoble = cuadrado.andThen(doble);
        System.out.println(cuadradoDoble.apply(3)); // 18

        var list = numeros.stream().map(cuadrado).map(doble).collect(Collectors.toList());

        System.out.println(list);

        Boolean cicle = true;
        BankAccount bankAccount = new BankAccount();
        while(cicle){
            System.out.println("operaciones bancarias");
            System.out.println("1. Depositar");
            System.out.println("2. Ver balace");
            System.out.println("3. Retirar");
            System.out.println("4. Ver depositos");
            System.out.println("5. Ver balance funcional");
            System.out.println("6. Salir");
            Scanner scanner = new Scanner(System.in);
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    System.out.println("Ingrese el monto a depositar");
                    double amount = scanner.nextDouble();
                    bankAccount.addTransaction(new Transaction(amount, "deposit", new Date()));
                    break;
                case 2:
                    System.out.println("El balance total es: " + bankAccount.getTotalBalance());
                    break;
                case 3:
                    System.out.println("Ingrese el monto a retirar");
                    double amountWithdraw = scanner.nextDouble();
                    bankAccount.addTransaction(new Transaction(amountWithdraw, "withdrawal", new Date()));
                    break;
                case 4:
                    System.out.println("Los depositos son: " + bankAccount.getDeposits());
                    break;
                case 5:
                    System.out.println("El balance total funcional es: " + bankAccount.getTotalBalanceFunctional());
                    break;
                case 6:
                    System.out.println("Saliendo...");
                    cicle = false;
                    break;
                default:
                    System.out.println("Opción no valida");
                    break;
            }

        }
    }
}
