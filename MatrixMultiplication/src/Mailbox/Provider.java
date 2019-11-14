package Mailbox;

public class Provider implements Runnable {
    public static final int MAX_SEND_TIMES = 2;
    final Mailbox mailbox;
    private int i;
    private final Object consumerCond;
    private final Object prividerCond;

    public Provider(Mailbox mailbox, Object consumerCond, Object prividerCond) {
        this.mailbox = mailbox;
        i = 0;
        this.consumerCond = consumerCond;
        this.prividerCond = prividerCond;
    }

    @Override
    public void run() {
        synchronized (prividerCond) {
            synchronized (consumerCond) {
                while (i < MAX_SEND_TIMES) {
                    if (mailbox.getContent() != null) {
                        try {
                            prividerCond.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mailbox.setContent("Message " + i++);
                    consumerCond.notifyAll();
                }
            }

        }
    }
}
