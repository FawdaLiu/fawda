package fawda.java.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <b>@Date:</b> <i>2018/11/29 21:16</i> 修订原因:初始化创建.详细说明:<br>
 * <b>@Desoription:</b> 既然动态代理不需要我们去创建代理类，那我们只需要编写一个动态处理器就可以了。
 * 真正的代理对象由 JDK 在运行时为我们动态的来创建。<br>
 *
 * <b>@Auther:</b> <i>Administrator</i>
 */
public class JDKDynamicProxy implements InvocationHandler {

    private Object target;

    public JDKDynamicProxy(Object target) {
        this.target = target;
    }

    public <T> T getProxy() {
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object invoke = method.invoke(target, args);
        after();
        return invoke;
    }

    private void after() {
        System.out.println("After");
    }

    private void before() {
        System.out.println("Before");
    }
}
