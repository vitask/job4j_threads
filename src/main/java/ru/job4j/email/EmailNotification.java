package ru.job4j.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        pool.submit(new Thread(() -> {
            String subject = String.format("Notification %s to email %s", user.username(), user.email());
            String body = String.format("Add a new event to %s", user.username());
            send(subject, body, user.email());
            System.out.println(subject);
            System.out.println(body);
        }));
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {
    }

    public static void main(String[] args) {
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.emailTo(new User("Oleg", "test@test.ru"));
        emailNotification.close();
    }
}
