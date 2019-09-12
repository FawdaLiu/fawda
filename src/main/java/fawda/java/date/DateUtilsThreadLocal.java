package fawda.java.date;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * <b>修订原因:</b> 初始化创建.详细说明:<br>
 * 可以使用 ThreadLocal 解决SimpleDateFormat非线程安全。<br>
 * <b>时间:</b> <i>2019年08月05日 下午6:56</i>
 *
 * @author Fawda: liuyl
 */
public final class DateUtilsThreadLocal {
    public static final ThreadLocal<SimpleDateFormat> SIMPLE_DATE_FORMAT = ThreadLocal
            .withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
    public DateUtilsThreadLocal() {}
    public static Date parse(String target) {
        try {
            return SIMPLE_DATE_FORMAT.get().parse(target);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String format(Date target) {
        return SIMPLE_DATE_FORMAT.get().format(target);
    }

    @Test
    public void testDateUtilsThreadLocalThreads() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        final String source = "2019-01-11";
        System.out.println(":: parsing date string ::");
        IntStream.rangeClosed(0, 20)
                .forEach((i) -> executorService.submit(() -> System.out.println(DateUtilsThreadLocal.parse(source))));
        executorService.shutdown();
    }
}