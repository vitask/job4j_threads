package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CopyOnWriteArrayList;
import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    void whenAddAndPoll() throws InterruptedException {
        var simpleBlockingQueue = new SimpleBlockingQueue<Integer>(2);
        var producer = new Thread(() -> {
            try {
                simpleBlockingQueue.offer(4);
                simpleBlockingQueue.offer(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        var consumer = new Thread(() -> {
            try {
                simpleBlockingQueue.poll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(simpleBlockingQueue.poll()).isEqualTo(5);
    }

    @Test
    void whenFetchAllThenGetIn() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).containsExactly(0, 1, 2, 3, 4);
    }
}