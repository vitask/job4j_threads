package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int size;
    private final Object monitor = this;

    public SimpleBlockingQueue(int capacity) {
        this.size = capacity;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == size) {
            this.wait();
        }
        queue.add(value);
        monitor.notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T result = queue.poll();
        monitor.notifyAll();
        return result;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}