package Mailbox;

public class Consumer implements Runnable {
    private final Mailbox mailbox;
    private int id ;
    private int i;

    public Consumer(Mailbox mailbox, int id) {
        this.mailbox = mailbox;
        this.id = id;
        i = 0;
    }

    @Override
    public void run() {
        synchronized (mailbox) {
            while (i < Provider.MAX_SEND_TIMES) {
                while (mailbox.getContent() == null) {
                    try {
                        mailbox.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(id + ": " + mailbox.getContent());
                mailbox.setContent(null);
                i++;
                mailbox.notify();
            }
        }
    }
}
