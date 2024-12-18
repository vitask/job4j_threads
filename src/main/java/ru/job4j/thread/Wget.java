package ru.job4j.thread;

import java.io.*;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String outputFile;


    public Wget(String url, int speed, String outputFile) {
        this.url = url;
        this.speed = speed;
        this.outputFile = outputFile;
    }

    @Override
    public void run() {
        try (InputStream input = new URL(url).openStream();
             FileOutputStream output = new FileOutputStream(outputFile)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            long startTime = System.currentTimeMillis();
            int totalBytesRead = 0;

            while ((bytesRead = input.read(buffer, 0, buffer.length)) != -1) {
                output.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;

                if (totalBytesRead >= speed) {
                    long pastTime = System.currentTimeMillis() - startTime;

                    if (pastTime < 1000) {
                        Thread.sleep(1000 - pastTime);
                    }

                    totalBytesRead = 0;
                    startTime = System.currentTimeMillis();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3) {
            throw new IllegalArgumentException("Illegal argument error");
        }

        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String outputFile = args[2];
        Thread wget = new Thread(new Wget(url, speed, outputFile));
        wget.start();
        wget.join();
    }
}
