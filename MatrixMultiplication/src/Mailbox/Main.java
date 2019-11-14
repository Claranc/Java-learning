package Mailbox;

import java.sql.Time;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Object consumerCond = new Object();
        Object prividerCond = new Object();
        Mailbox mailbox = new Mailbox();
        Provider provider = new Provider(mailbox, consumerCond, prividerCond);
        Thread tProvider = new Thread(provider);
        tProvider.start();
        Thread.sleep(1000);

        Thread tConsumers[] = new Thread[1];
        for (int i = 0; i < tConsumers.length; i++) {
            Consumer consumer = new Consumer(mailbox, i, consumerCond, prividerCond);
            tConsumers[i] = new Thread(consumer);
            tConsumers[i].start();
        }

    }
}
