package com.example.concurrent_file_processor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class ThreadPoolConfig {
    @Bean(destroyMethod = "shutdown")
    public ThreadPoolExecutor fileProcessingExecutor(){
        int corePoolSize = 4;
        int maxPoolSize = 4;
        int queueCapacity = 10;
        long keepAliveTime = 0;

        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(queueCapacity);

        AtomicInteger counter = new AtomicInteger(1);

        ThreadFactory threadFactory = task ->
                new Thread(task,
                        "csv-worker-"+ counter.getAndIncrement());

        return new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                workQueue,
                threadFactory,
                new ThreadPoolExecutor.AbortPolicy()
        );

    }
}