package com.ktnet.fta.common.scheduler;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public abstract class DynamicAbstractScheduler {
    private ThreadPoolTaskScheduler scheduler;
    
    public void stopScheduler() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }
    
    public void startScheduler() {
        scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
        scheduler.schedule(getRunnable(), getTrigger());
    }
    
    private Runnable getRunnable() { return this::runner; }
    
    public abstract void runner();
    
    public abstract Trigger getTrigger();
}