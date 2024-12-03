package com.easv.gringofy.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Debounce {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private Runnable currentTask;
    private final long debounceDelayMillis;

    public Debounce(long debounceDelayMillis) {
        this.debounceDelayMillis = debounceDelayMillis;
    }

    public void debounce(Runnable task) {
        if (currentTask != null) {
            scheduler.shutdownNow(); // Cancel the previous task
        }
        currentTask = () -> {
            try {
                task.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        scheduler.schedule(currentTask, debounceDelayMillis, TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}
