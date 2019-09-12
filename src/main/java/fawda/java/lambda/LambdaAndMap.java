package fawda.java.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LambdaAndMap {

    public static void main(String[] args) {
//        LambdaAndMap.disMap();
        LambdaAndMap.distinct();
    }
    
    /**
     * 我们通常需要对列表的每个元素使用某个函数，例如逐一乘以某个数、除以某个数或者做其它操作。 这些操作都很适合用 map()
     * 方法，可以将转换逻辑以lambda表达式的形式放在 map() 方法里， 就可以对集合的各个元素进行转换了，如下所示。
     */
    private static void disMap(){
        // 将字符串换成大写并用逗号链接起来
        List<String> G7 = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.", "Canada");
        String G7Countries = G7.stream().map(x -> x.toUpperCase()).collect(Collectors.joining(", "));
        System.out.println(G7Countries);
    }

    /**
     * 本例展示了如何利用流的 distinct() 方法来对集合进行去重。
     */
    private static void distinct() {
        // 用所有不同的数字创建一个正方形列表
        List<Integer> numbers = Arrays.asList(9, 10, 3, 4, 7, 3, 4);
        List<Integer> distinct = numbers.stream().map(i -> i * i).distinct().collect(Collectors.toList());
        System.out.printf("Original List : %s,  Square Without duplicates : %s %n", numbers, distinct);
    }
}
