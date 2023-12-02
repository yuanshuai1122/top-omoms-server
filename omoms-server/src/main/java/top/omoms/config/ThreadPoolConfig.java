package top.omoms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;


/**
 * 本地线程池配置类
 */
@Configuration
public class ThreadPoolConfig {

    /**
     * 写日志线程池
     */
    @Bean
    public ThreadPoolTaskExecutor writeLogThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数
        executor.setCorePoolSize(0);
        //最大线程数
        executor.setMaxPoolSize(1);
        //缓冲队列大小
        executor.setQueueCapacity(10000);
        //线程池名前缀
        executor.setThreadNamePrefix("async-");
        // 设置线程保持活跃的时间（默认：60）
        executor.setKeepAliveSeconds(300);
        // 当任务完成后，长时间无待处理任务时，销毁线程池
        executor.setWaitForTasksToCompleteOnShutdown(false);
        //设置线程池关闭时等待所有任务完成的最长时间（只对调用shutdown()方法后才提交的任务生效）
        executor.setAwaitTerminationSeconds(180);
        // 设置任务拒绝策略
        /*
         * 4种
         * ThreadPoolExecutor类有几个内部实现类来处理这类情况：
         - AbortPolicy 丢弃任务，抛RejectedExecutionException
         - CallerRunsPolicy 由该线程调用线程运行。直接调用Runnable的run方法运行。
         - DiscardPolicy  抛弃策略，直接丢弃这个新提交的任务
         - DiscardOldestPolicy 抛弃旧任务策略，从队列中踢出最先进入队列（最后一个执行）的任务
         * 实现RejectedExecutionHandler接口，可自定义处理器
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }

}