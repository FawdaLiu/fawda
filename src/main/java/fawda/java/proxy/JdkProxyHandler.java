package fawda.java.proxy;

import java.lang.reflect.Proxy;

/**
 * 动态代理处理类
 *
 * @author shengwu ni
 * @date 2018-12-08
 */

interface Star {
    void sing();
}

class RealStar implements Star {

    @Override
    public void sing() {
        System.out.println("歌星本人唱歌。。。");
    }
}

public class JdkProxyHandler {

    /**
     * 用来接收真实明星对象
     */
    private Object realStar;

    /**
     * 通过构造方法传进来真实的明星对象
     *
     * @param star star
     */
    public JdkProxyHandler(Star star) {
        super();
        this.realStar = star;
    }

    /**
     * 给真实对象生成一个代理对象实例
     *
     * @return Object
     */
    public Object getProxyInstance() {
        return Proxy.newProxyInstance(realStar.getClass().getClassLoader(),
                realStar.getClass().getInterfaces(), (proxy, method, args) -> {

                    System.out.println("代理先进行谈判……");
                    // 唱歌需要明星自己来唱
                    Object object = method.invoke(realStar, args);
                    System.out.println("演出完代理去收钱……");

                    return object;
                });
    }

    public static void main(String[] args) {
        Star realStar = new RealStar();
        // 创建一个代理对象实例
        Star proxy = (Star) new JdkProxyHandler(realStar).getProxyInstance();
        proxy.sing();
    }
}