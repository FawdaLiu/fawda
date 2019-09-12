package fawda.java.lambda;

import java.util.Arrays;
import java.util.List;

public class LambdaForEach {

    public static void main(String[] args) {
        List<String> features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
//        for(String n : features){
//            System.out.println(n);
//        }
//        使用Java 8的方法引用更方便，方法引用由::双冒号操作符标示，
//        features.forEach(n -> {System.out.println(n);});
//        展示了如何在Java 8中使用方法引用（method reference）。
//        你可以看到C++里面的双冒号、范围解析操作符现在在Java 8中用来表示方法引用
        features.forEach(System.out::println);
    }

}
