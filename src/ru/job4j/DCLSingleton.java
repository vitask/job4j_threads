package ru.job4j;

public class DCLSingleton {

    public DCLSingleton() {
    }

    private static volatile DCLSingleton instance;

    public static DCLSingleton getInstance() {
        if (instance == null) {
            synchronized (DCLSingleton.class) {
                if (instance == null) {
                    instance = new DCLSingleton();
                }
            }
        }
        return instance;
    }
}
