package com.easv.gringofy.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Debounce {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> currentFuture;
    private final long debounceDelayMillis;

    public Debounce(long debounceDelayMillis) {
        this.debounceDelayMillis = debounceDelayMillis;
    }

    public synchronized void debounce(Runnable task) {
        if (currentFuture != null && !currentFuture.isDone()) {
            currentFuture.cancel(false);
        }
        currentFuture = scheduler.schedule(() -> {
            try {
                task.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, debounceDelayMillis, TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}
