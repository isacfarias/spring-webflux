package com.course.webfluxdemo.util;

public class SleepUtil {


    public static void sleepSeconds(int seconds) {
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
