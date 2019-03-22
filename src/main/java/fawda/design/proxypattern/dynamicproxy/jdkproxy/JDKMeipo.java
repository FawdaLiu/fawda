package fawda.design.proxypattern.dynamicproxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <b>时间:</b> <i>2019-03-17 23:41</i> 修订原因:初始化创建.详细说明:<br>
 * <br>
 * Action类{@linkplain }使用<br>
 *
 * <b>时间:</b> *年*月*日 *时*分*秒 修订原因:比如BUG修复或业务变更.详细说明:<br>
 *
 * @author liuyl@hzrisencn.com
 **/
public class JDKMeipo implements InvocationHandler {

    private Object person;

    public Object getInstance(Object person) {
        this.person = person;
        Class<?> clazz = person.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        this.before();
        Object obj = method.invoke(this.person, args);
        this.after();
        return obj;
    }

    private void before() {//增强代理
        System.out.println("我是媒婆，我要给你找对象，现在已经确认你的需求");
        System.out.println("开始物色");
    }


    private void after() {
        System.out.println("ok的话，准备办事");
    }
}
