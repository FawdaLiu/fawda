package fawda.java.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * <b>@Date:</b> <i>2018/11/29 22:16</i> 修订原因:初始化创建.详细说明:<br>
 * <b>@Desoription:</b> CGLIB 采用了非常底层的字节码技术，其原理是通过字节码技术为一个类创建子类，
 * 并在子类中采用方法拦截的技术拦截所有父类方法的调用，顺势织入横切逻辑。
 * 但因为采用的是继承，所以不能对final修饰的类进行代理。我们来写一个 CBLIB 代理类。
 *
 * CGLIB 创建的动态代理对象比 JDK 创建的动态代理对象的性能更高，但是 CGLIB 创建代理对象时所花费的时间却比 JDK 多得多。
 * 所以对于单例的对象，因为无需频繁创建对象，用 CGLIB 合适，反之使用JDK方式要更为合适一些。
 * 同时由于 CGLIB 由于是采用动态创建子类的方法，对于final修饰的方法无法进行代理。
 *
 *
 * 当然了，不管是哪种动态代理技术，在上面的代码里，要代理的类中可能不止一种方法，
 * 有时候我们需要对特定的方法进行增强处理，所以可以对传入的 method 参数进行方法名的判断，再做相应的处理。
 * <br>
 *
 * <b>@Auther:</b> <i>Administrator</i>
 */
public class CGLibDynamicProxy implements MethodInterceptor {
    private static CGLibDynamicProxy instance = new CGLibDynamicProxy();

    private CGLibDynamicProxy() {
    }

    public static CGLibDynamicProxy getInstance() {
        return instance;
    }

    public <T> T getProxy(Class<T> cls) {
        return (T) Enhancer.create(cls, this);
    }

    @Override
    public Object intercept(Object target, Method method, Object[]args, MethodProxy proxy) throws Throwable {
        before();
        proxy.invokeSuper(target, args);
        after();
        return null;
    }

    private void after() {
        System.out.println("After");
    }

    private void before() {
        System.out.println("Before");
    }
}
