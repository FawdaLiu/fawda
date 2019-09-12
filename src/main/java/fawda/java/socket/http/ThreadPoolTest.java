package fawda.java.socket.http;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 线程池测试
 *
 * @author 五月的仓颉https://www.cnblogs.com/xrq730/p/10963689.html
 */
public class ThreadPoolTest {

    private static final AtomicInteger FINISH_COUNT = new AtomicInteger(0);
    
    private static final AtomicLong COST = new AtomicLong(0);
    
    private static final Integer INCREASE_COUNT = 1000000;
    
    private static final Integer TASK_COUNT = 1000;
    
    @Test
    public void testRunWithoutThreadPool() {
        List<Thread> tList = new ArrayList<Thread>(TASK_COUNT);
        
        for (int i = 0; i < TASK_COUNT; i++) {
            tList.add(new Thread(new IncreaseThread()));
        }
        
        for (Thread t : tList) {
            t.start();
        }
        
        for (;;);
    }
    
    /**
     * <b>修订原因:</b> 初始化创建.详细说明:<br>
     *    减少线程创建、销毁的开销
     *    控制线程的数量，避免来一个任务创建一个线程，最终内存的暴增甚至耗尽
     *
     * <b>时间:</b> <i>2019年08月11日 上午11:55</i>
     *
     * @author Fawda: liuyl
     */
    @Test
    public void testRunWithThreadPool() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 100, 0, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        
        for (int i = 0; i < TASK_COUNT; i++) {
            executor.submit(new IncreaseThread());
        }
        
        for (;;);
    }
    
    private class IncreaseThread implements Runnable {
        
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            
            AtomicInteger counter = new AtomicInteger(0);
            for (int i = 0; i < INCREASE_COUNT; i++) {
                counter.incrementAndGet();
            }
            // 累加执行时间
            COST.addAndGet(System.currentTimeMillis() - startTime);
            if (FINISH_COUNT.incrementAndGet() == TASK_COUNT) {
                System.out.println("cost: " + COST.get() + "ms");
            }
        }
        
    }
    
}