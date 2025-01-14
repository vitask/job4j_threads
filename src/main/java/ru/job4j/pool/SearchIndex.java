package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SearchIndex<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final T index;
    private final int from;
    private final int to;

    public SearchIndex(T[] array, T index, int from, int to) {
        this.array = array;
        this.index = index;
        this.from = from;
        this.to = to;
    }

    public int search() {
        int result = -1;
        for (int i = from; i <= to; i++) {
            if (array[i] == index) {
                result = i;
                break;
            }
        }
        return result;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return search();
        }
        int middle = (from + to) / 2;
        SearchIndex<T> leftSearch = new SearchIndex<>(array, index, from, middle);
        SearchIndex<T> rightSearch = new SearchIndex<>(array, index, middle + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        int left = leftSearch.join();
        int right = rightSearch.join();
        return Math.max(left, right);
    }

    public static <T> int find(T[] array, T value) {
        ForkJoinPool joinPool = new ForkJoinPool();
        return joinPool.invoke(new SearchIndex<>(array, value, 0, array.length - 1));
    }
}
