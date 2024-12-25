package ru.job4j;

import org.junit.jupiter.api.Test;

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
}