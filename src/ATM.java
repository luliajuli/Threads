public class ATM {
    private int balance;

    public ATM(int initialBalance) {
        this.balance = initialBalance;
    }

    public void withdraw(String name, int amount) {
        System.out.println(name + " подошел к банкомату");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (balance >= amount) {
            balance -= amount;
            System.out.println(name + " вывел " + amount + " рублей. В банкомате осталось " + balance + " рублей.");
        } else {
            System.out.println("В банкомате недостаточно денег для " + name);
        }
    }
}

class ClientThread extends Thread {
    private ATM atm;
    private String name;
    private int amount;

    public ClientThread(ATM atm, String name, int amount) {
        this.atm = atm;
        this.name = name;
        this.amount = amount;
    }

    @Override
    public void run() {
        atm.withdraw(name, amount);
    }
}

class Main {
    public static void main(String[] args) {
        ATM atm = new ATM(100);

        // Клиенты пытаются снять деньги
        ClientThread client1 = new ClientThread(atm, "Иван", 60);
        ClientThread client2 = new ClientThread(atm, "Ольга", 70);
        ClientThread client3 = new ClientThread(atm, "Анна", 40);

        try {
            client1.start();
            client1.join();  // Ожидаем завершения client1 перед запуском client2

            client2.start();
            client2.join();  // Ожидаем завершения client2 перед запуском client3

            client3.start();
            client3.join();  // Ожидаем завершения client3
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}