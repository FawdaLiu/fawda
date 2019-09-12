package fawda.java.date;

import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class DateUtilsJava8 {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public DateUtilsJava8() {}
    public static LocalDate parse(String target) {
        return LocalDate.parse(target, DATE_TIME_FORMATTER);
    }
    public static String format(LocalDate target) {
        return target.format(DATE_TIME_FORMATTER);
    }

    @Test
    public void testDateUtilsThreadLocalThreads() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        final String source = "2019-01-11";
        System.out.println(":: parsing date string ::");
        IntStream.rangeClosed(0, 10)
                .forEach((i) -> executorService.submit(() -> System.out.println(DateUtilsJava8.parse(source))));
        executorService.shutdown();
    }
}