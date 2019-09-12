package fawda.java.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.annotation.PostConstruct;

import cn.hutool.http.HttpUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.im4java.process.ProcessStarter;


//@Service
public class apiTest {

    /**
     * 定义并发线程数量
     */
    public static final int THREAD_NUM = 100;

    /**
     * 开始时间
     */
    private static long startTime = 0L;

    @PostConstruct
    public void init() {
        try {

            startTime = System.currentTimeMillis();
            System.out.println("CountDownLatch started at: " + startTime);

            // 初始化计数器为1
            CountDownLatch countDownLatch = new CountDownLatch(1);

            for (int i = 0; i < THREAD_NUM; i ++) {
                new Thread(new Run(countDownLatch)).start();
            }

            // 启动多个线程
            countDownLatch.countDown();

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    /**
     * 线程类
     */
    private class Run implements Runnable {
        private final CountDownLatch startLatch;

        public Run(CountDownLatch startLatch) {
            this.startLatch = startLatch;
        }

        @Override
        public void run() {
            try {
                // 线程等待
                startLatch.await();

                // 执行操作
                /**
					这里调用你要测试的接口
				*/

                // HttpClientUtil httpClientUtil = new HttpClientUtil();
                // List<NameValuePair> formParams = new ArrayList<>();
                // formParams.add(new BasicNameValuePair("edit","thumbnailImg"));
                // formParams.add(new BasicNameValuePair("editType","center"));
                // formParams.add(new BasicNameValuePair("width","400"));
                // formParams.add(new BasicNameValuePair("height","400"));
                // httpClientUtil.doPost("http://localhost:8080/test.action",formParams);
                HashMap<String, Object> dataMap = new HashMap<>();
                dataMap.put("edit","thumbnailImg");
                dataMap.put("editType","center");
                dataMap.put("width","400");
                dataMap.put("height","400");
                HttpUtil.post("http://localhost:8080/test.action", dataMap);
                long endTime = System.currentTimeMillis();
                System.out.println(Thread.currentThread().getName() + " ended at: " + endTime + ", cost: " + (endTime - startTime) + " ms.");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        apiTest apiTest = new apiTest();
        apiTest.init();
    }
}
