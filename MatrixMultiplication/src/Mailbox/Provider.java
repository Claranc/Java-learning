package Mailbox;

public class Provider implements Runnable {
    public static final int MAX_SEND_TIMES = 10;
    final Mailbox mailbox;
    private int i;

    public Provider(Mailbox mailbox) {
        this.mailbox = mailbox;
        i = 0;
    }

    @Override
    public void run() {
        synchronized (mailbox) {
            while (i < MAX_SEND_TIMES) {
                while (mailbox.getContent() != null) {
                    try {
                        mailbox.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mailbox.setContent("Message " + i++);
                mailbox.notifyAll();
            }
        }
    }
}
