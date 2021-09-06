package com.isport.blelibrary.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadSinglePoolUtils {
    private ExecutorService service;

    private ThreadSinglePoolUtils() {
        int num = Runtime.getRuntime().availableProcessors();
        service = Executors.newSingleThreadExecutor();
    }

    private static ThreadSinglePoolUtils manager = new ThreadSinglePoolUtils();

    public static ThreadSinglePoolUtils getInstance() {
        return manager;
    }

    public void addTask(Runnable runnable) {
        if (!service.isShutdown()) {
            service.execute(runnable);
        }
    }

    public void onTerminate() {
        if (service != null) {
            service.shutdown();

        }
    }
}
