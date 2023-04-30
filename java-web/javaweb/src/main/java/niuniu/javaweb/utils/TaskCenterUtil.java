package niuniu.javaweb.utils;

import java.util.concurrent.*;

public class TaskCenterUtil {

    /**
     * 核心线程数量
     */
    public static final int CORE_THREAD_NUM = 10;

    /**
     * 最大线程数量
     */
    public static final int MAX_THREAD_NUM = 15;

    /**
     * 非核心线程存活时间
     */
    public static final long KEEP_ALIVE_TIME_SECONDS = 10L;

    /**
     * 任务队列长度
     */
    public static final int QUEUE_LENGTH = 20;

    /**
     * 线程超时时间
     */
    public static final long TIME_OUT = 70;

    private ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
            CORE_THREAD_NUM
            , MAX_THREAD_NUM,
            KEEP_ALIVE_TIME_SECONDS,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(QUEUE_LENGTH), Executors.defaultThreadFactory()
            , new ThreadPoolExecutor.CallerRunsPolicy());


    private TaskCenterUtil() {
    }

    private static TaskCenterUtil taskCenterUtil = new TaskCenterUtil();

    public static TaskCenterUtil getTaskCenterUtil() {
        return taskCenterUtil;
    }

    public void submitTask(Callable task) {
        poolExecutor.submit(task);
    }

}

