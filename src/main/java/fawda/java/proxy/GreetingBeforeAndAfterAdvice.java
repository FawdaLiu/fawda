package fawda.java.proxy;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * <b>@Date:</b> <i>2018/11/29 22:48</i> 修订原因:初始化创建.详细说明:<br>
 * <b>@Desoription:</b> <br>
 *
 * <b>@Auther:</b> <i>Administrator</i>
 */
public class GreetingBeforeAndAfterAdvice implements MethodBeforeAdvice, AfterReturningAdvice {
    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        System.out.println("After");
    }

    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println("Before");
    }
}
