package Mailbox;

public class Consumer implements Runnable {
    private final Mailbox mailbox;
    private int id ;
    private int i;
    private final Object consumerCond;
    private final Object prividerCond;

    public Consumer(Mailbox mailbox, int id, Object consumerCond, Object prividerCond) {
        this.mailbox = mailbox;
        this.id = id;
        i = 0;
        this.consumerCond = consumerCond;
        this.prividerCond = prividerCond;
    }

    @Override
    public void run() {
        synchronized (consumerCond) {
            synchronized (prividerCond) {
                while (i < Provider.MAX_SEND_TIMES) {
                    if (mailbox.getContent() == null) {
                        try {
                            consumerCond.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(id + ": " + mailbox.getContent());
                    mailbox.setContent(null);
                    i++;
                    prividerCond.notify();
                }
            }
        }
    }
}
