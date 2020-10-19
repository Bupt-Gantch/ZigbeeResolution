package com.bupt.ZigbeeResolution.service;

import com.sun.jmx.snmp.tasks.ThreadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.relation.RoleUnresolved;
import java.util.concurrent.*;

public class CacheQueueService {
    private static final int CORE_POOL_SIZE = 10;
    private static final int MAX_POOL_SIZE = 100;
    private static final int QUEUE_CAPACITY = 200;
    private static final int KEEP_ALIVE_TIME = 500;
    
    private static Logger log = LoggerFactory.getLogger(CacheQueueService.class);
    
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            KEEP_ALIVE_TIME,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(QUEUE_CAPACITY),
            new ThreadPoolExecutor.DiscardOldestPolicy()
    );
    
    static {
        printThreadPoolStatus(executor);
    }
    
    public static void printThreadPoolStatus(ThreadPoolExecutor threadPool) {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            log.info("=========================");
            log.info("ThreadPool Size: [{}]", threadPool.getPoolSize());
            log.info("Active Threads: {}", threadPool.getActiveCount());
            log.info("Number of Tasks : {}", threadPool.getCompletedTaskCount());
            log.info("Number of Tasks in Queue: {}", threadPool.getQueue().size());
            log.info("=========================");
            if(executor.isShutdown()){
                scheduledExecutorService.shutdown();
            }
        }, 0, 10, TimeUnit.SECONDS);
    }
    
    public static void execute(Runnable runnable){
        executor.execute(runnable);
    }
    
}
