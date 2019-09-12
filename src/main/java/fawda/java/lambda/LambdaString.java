package fawda.java.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LambdaString {

    /**
     * 过滤是Java开发者在大规模集合上的一个常用操作，而现在使用lambda表达式和流API过滤大规模数据集合是惊人的简单。
     * 流提供了一个 filter() 方法，接受一个 Predicate 对象，即可以传入一个lambda表达式作为过滤逻辑。
     * 下面的例子是用lambda表达式过滤Java集合，将帮助理解。
     * 另外，关于 filter() 方法有个常见误解。在现实生活中，做过滤的时候，通常会丢弃部分，
     * 但使用filter()方法则是获得一个新的列表，且其每个元素符合过滤原则。
     */
    public static void main(String[] args) {
        // 创建一个字符串列表，每个字符串长度大于2
        List<String> strList = Arrays.asList("abc", "", "bcd", "", "defg", "jk");
        List<String> filtered = strList.stream().filter(x -> x.length()> 2).collect(Collectors.toList());
        System.out.printf("Original List : %s, filtered list : %s %n", strList, filtered);
    }

}
