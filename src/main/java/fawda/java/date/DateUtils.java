package fawda.java.date;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * <b>时间:</b> <i>2019-08-05 16:58</i> 修订原因:初始化创建.详细说明:<br>
 *
 * 日期格式是非同步的。建议为每个线程创建单独的日期格式化实例。如果多个线程并发访问某个格式化实例，则必须保证外部调用同步性。 <br>
 * 提示：使用实例变量时，应该每次检查这个类是不是线程安全。<br>
 * Action类{@linkplain fawda.java.date}使用<br>
 *
 * <b>时间:</b> *年*月*日 *时*分*秒 修订原因:比如BUG修复或业务变更.详细说明:<br>
 *
 * @author Fawda liuyl @since 1.0
 **/
public final class DateUtils {
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public DateUtils() {}
    public static Date parse(String target) {
        try {
            return SIMPLE_DATE_FORMAT.parse(target);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String format(Date target) {
        return SIMPLE_DATE_FORMAT.format(target);
    }

    @Test
    public void testSimpleDateFormatInSingleThread() {
        final String source = "2019-01-11";
        System.out.println(DateUtils.parse(source));
    }

    //测试SimpleDateFormat线程非安全，如果多个线程并发访问某个格式化实例，则必须保证外部调用同步性。
    @Test
    public void testSimpleDateFormatWithThreads() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        final String source = "2019-01-11";
        System.out.println(":: parsing date string ::");
        IntStream.rangeClosed(0, 20)
                .forEach((i) -> executorService.submit(() -> System.out.println(DateUtils.parse(source))));
        executorService.shutdown();
    }
}
