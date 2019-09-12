package fawda.java.proxy;

import org.springframework.aop.framework.ProxyFactory;

/**
 * <b>@Date:</b> <i>2018/11/29 21:13</i> 修订原因:初始化创建.详细说明:<br>
 * <b>@Desoription:</b> <br>
 *
 * <b>@Auther:</b> <i>Administrator</i>
 */
public class Client {
    public static void main(String[] args) {
//        自造的代理
        Greeting greetingProxy = new GreetingProxy(new GreetingImpl());
        greetingProxy.sayHello("Jack proxy");
//
          //jdk中的动态代理
//        Greeting proxy = new JDKDynamicProxy(new GreetingImpl()).getProxy();
//        proxy.sayHello("Jack2 JDK");
//
          //spring中封装的动态代理
//        Greeting greeting = CGLibDynamicProxy.getInstance().getProxy(GreetingImpl.class);
//        greeting.sayHello("Jack CGLib");

        //spring中封装的动态代理类
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new GreetingImpl());
        proxyFactory.addAdvice(new GreetingBeforeAndAfterAdvice());
        Greeting greeting1 = (Greeting)proxyFactory.getProxy();
        greeting1.sayHello("Jack AOP");
    }
}
