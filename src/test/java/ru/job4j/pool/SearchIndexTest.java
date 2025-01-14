package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SearchIndexTest {

    @Test
    void whenFindIndex() {
        Integer[] array = {12, 23, 34, 45, 56};
        assertThat(SearchIndex.find(array, 34)).isEqualTo(2);
    }

    @Test
    void whenNotFind() {
        Integer[] array = {92, 12, 34, 75, 1};
        assertThat(SearchIndex.find(array, 20)).isEqualTo(-1);
    }

    @Test
    void whenDifferentType() {
        Object[] array = {14, "str", 214, 554, "a", "test"};
        assertThat(SearchIndex.find(array, "test")).isEqualTo(5);
    }

    @Test
    void whenOnlyStringType() {
        String[] array = {"A", "B", "C", "D"};
        assertThat(SearchIndex.find(array, "B")).isEqualTo(1);
    }

    @Test
    void whenLineSearch() {
        Integer[] array = {1, 14, 21, 2, 99, 59, 321, 15, 73, 3};
        assertThat(SearchIndex.find(array, 15)).isEqualTo(7);
    }

    @Test
    void whenRecursiveSearch() {
        Integer[] array = new Integer[]{24, 1, 63, 99, 74, 39, 82, 43, 15};
        assertThat(SearchIndex.find(array, 63)).isEqualTo(2);
    }
}