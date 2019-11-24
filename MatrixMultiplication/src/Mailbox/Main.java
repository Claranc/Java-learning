package Mailbox;

import java.sql.Time;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Mailbox mailbox = new Mailbox();
        Provider provider = new Provider(mailbox);
        Thread tProvider = new Thread(provider);
        tProvider.start();

        Thread[] tConsumers = new Thread[5];
        for (int i = 0; i < tConsumers.length; i++) {
            Consumer consumer = new Consumer(mailbox, i);
            tConsumers[i] = new Thread(consumer);
            tConsumers[i].start();
        }

    }
}
