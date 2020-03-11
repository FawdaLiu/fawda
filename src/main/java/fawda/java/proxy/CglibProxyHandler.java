package fawda.java.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
* cglib代理处理类
* @author shengwu ni
* @date 2018-12-08
*/
public class CglibProxyHandler implements MethodInterceptor {

   /**
    * 维护目标对象
    */
   private Object target;

   public Object getProxyInstance(final Object target) {
       this.target = target;
       // Enhancer类是CGLIB中的一个字节码增强器，它可以方便的对你想要处理的类进行扩展
       Enhancer enhancer = new Enhancer();
       // 将被代理的对象设置成父类
       enhancer.setSuperclass(this.target.getClass());
       // 回调方法，设置拦截器
       enhancer.setCallback(this);
       // 动态创建一个代理类
       return enhancer.create();
   }

   @Override
   public Object intercept(Object object, Method method, Object[] args,
                           MethodProxy methodProxy) throws Throwable {

       System.out.println("代理先进行谈判……");
       // 唱歌需要明星自己来唱
       Object result = methodProxy.invokeSuper(object, args);
       System.out.println("演出完代理去收钱……");
       return result;
   }

    public static void main(String[] args) {
        Star realStar = new RealStar();
        Star proxy = (Star) new CglibProxyHandler().getProxyInstance(realStar);

        proxy.sing();
    }
}