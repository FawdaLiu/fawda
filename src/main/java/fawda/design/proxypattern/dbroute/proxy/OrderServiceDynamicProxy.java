package fawda.design.proxypattern.dbroute.proxy;

import fawda.design.proxypattern.dbroute.IOrderService;
import fawda.design.proxypattern.dbroute.Order;
import fawda.design.proxypattern.dbroute.db.DynameicDataSourceEntity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <b>时间:</b> <i>2019-03-17 23:11</i> 修订原因:初始化创建.详细说明:<br>
 * <br>
 * Action类{@linkplain }使用<br>
 *
 * <b>时间:</b> *年*月*日 *时*分*秒 修订原因:比如BUG修复或业务变更.详细说明:<br>
 *
 * @author liuyl@hzrisencn.com
 **/
public class OrderServiceDynamicProxy implements InvocationHandler {

    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    private Object proxyObj;

    public Object getInstance(Object proxyObj) {
        this.proxyObj = proxyObj;
        Class<?> aClass = proxyObj.getClass();
        return Proxy.newProxyInstance(aClass.getClassLoader(), aClass.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before(args[0]);
        Object invoke = method.invoke(proxyObj, args);
        after();
        return invoke;
    }

    private void after() {
        System.out.println("Proxy after method");
        //还原城默认的数据源
        DynameicDataSourceEntity.restore();
    }

    //target 订单对象
    private void before(Object target) {
        System.out.println("Proxy before method");
        //进行数据源的切换
        //利用反射，约定优于配置
        try {
            Long time = (Long)target.getClass().getMethod("getCreateTime").invoke(target);
            Integer dbRouter = Integer.valueOf(yearFormat.format(new Date(time)));
            System.out.println("静态代理类自动分配到【DB_" + dbRouter + "】数据源处理数据");
            DynameicDataSourceEntity.set(dbRouter);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}
