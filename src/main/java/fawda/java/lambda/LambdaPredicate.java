package fawda.java.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Administrator
 *
 */
public class LambdaPredicate {

    public static void main(String[] args) {
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");
//        System.out.println("Languages which starts with J :");
//        filter(languages, (s) -> s.startsWith("J"));
//        System.out.println("Languages which ends with a ");
//        filter(languages, (str)->str.endsWith("a"));
        
//        System.out.println("Print all languages :");
//        filter(languages, (str)->true);
//        
//        System.out.println("Print no language : ");
//        filter(languages, (str)->false);
        
        System.out.println("Print language whose length greater than 4:");
        filter2(languages);
    }

    
    /**
     * 除了在语言层面支持函数式编程风格，Java 8也添加了一个包，叫做 java.util.function。它包含了很多类，用来支持Java的函数式编程。
     * 其中一个便是Predicate，使用 java.util.function.Predicate 函数式接口以及lambda表达式，可以向API方法添加逻辑，
     * 用更少的代码支持更多的动态行为。下面是Java 8 Predicate 的例子，展示了过滤集合数据的多种常用方法。Predicate接口非常适用于做过滤。
     */
    private static void filter(List<String> names, Predicate<String> predicate) {
        for (String name : names) {
            if (predicate.test(name)) {
                System.out.println(name + "    ");
            }
        }
    }
    
    /**
     * 更好的办法
     * 除了在语言层面支持函数式编程风格，Java 8也添加了一个包，叫做 java.util.function。它包含了很多类，用来支持Java的函数式编程。
     * 其中一个便是Predicate，使用 java.util.function.Predicate 函数式接口以及lambda表达式，可以向API方法添加逻辑，
     * 用更少的代码支持更多的动态行为。下面是Java 8 Predicate 的例子，展示了过滤集合数据的多种常用方法。Predicate接口非常适用于做过滤。
     * 
     * Stream API的过滤方法也接受一个Predicate，这意味着可以将我们定制的 filter() 方法替换成写在里面的内联代码，这就是lambda表达式的魔力。
     */
    private static void filter1(List<String> names, Predicate<String> condition) {
        names.stream().filter((name) -> (condition.test(name))).forEach((name) -> {
            System.out.println(name + " ");
        });
    }
    
    
    /**
     * 
     * Predicate接口也允许进行多重条件的测试，
     * 上个例子说到，java.util.function.Predicate 允许将两个或更多的 Predicate 合成一个。它提供类似于逻辑操作符AND和OR的方法，名字叫做and()、or()和xor()，
     * 用于将传入 filter() 方法的条件合并起来。例如，要得到所有以J开始，长度为四个字母的语言，可以定义两个独立的 Predicate 示例分别表示每一个条件，
     * 然后用 Predicate.and() 方法将它们合并起来，如下所示：
     * 
     * 类似地，也可以使用 or() 和 xor() 方法。本例着重介绍了如下要点：可按需要将 Predicate 作为单独条件然后将其合并起来使用。简而言之，你可以以传统Java命令方式使用 Predicate 接口，
     * 也可以充分利用lambda表达式达到事半功倍的效果。
     */
    private static void filter2 (List<String> names){
        Predicate<String> startsWithJ = (n) -> n.startsWith("J");
        Predicate<String> fourLetterLong = (n) -> n.length() == 4;
        names.stream().filter(startsWithJ.and(fourLetterLong)).forEach((n) -> System.out.print("nName, which starts with 'J' and four letter long is : " + n));
    }
    
    
}
