package fawda.design.singletonpattern;

import fawda.design.singletonpattern.lazy.LazyInnerClassSingleton;
import fawda.design.singletonpattern.lazy.LazySimpleSingleton;
import fawda.design.singletonpattern.register.ContainerSingleton;
import fawda.design.singletonpattern.register.EnumSingleton;
import fawda.design.singletonpattern.seriable.SeriableSingleton;
import fawda.design.singletonpattern.threadlocal.ThreadLocalSingleton;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;

/**
 * <b>时间:</b> <i>2019-03-17 14:00</i> 修订原因:初始化创建.详细说明:<br>
 * <br>
 * Action类{@linkplain }使用<br>
 *
 * <b>时间:</b> *年*月*日 *时*分*秒 修订原因:比如BUG修复或业务变更.详细说明:<br>
 *
 * @author liuyl@hzrisencn.com
 **/
public class SingLetonTest {

    @Test
    public void lazySingletonTest() {
        LazySimpleSingleton.getInstance();
    }

    @Test
    public void exectorThreadTest() {
        Thread t1 = new Thread(new ExectorThread());
        Thread t2 = new Thread(new ExectorThread());
        t1.start();
        t2.start();
        System.out.println("Exector End");
    }

    @Test
    public void lazyInnerClassSingletonTest() {
        LazyInnerClassSingleton.getInstance();
    }


    //静态内部类单例反射破坏测试
    @Test
    public void lazyInnerClassSingletonClassTest() {
        try {
            Class<?> clazz = LazyInnerClassSingleton.class;
            Constructor c = clazz.getDeclaredConstructor(null);
            c.setAccessible(true);
            Object o1 = c.newInstance();
            Object o2 = LazyInnerClassSingleton.getInstance();
            System.out.println(o1 == o2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //序列化破坏单例测试
    @Test
    public void seriableSingletonTest() {
        SeriableSingleton s1 = null;
        SeriableSingleton s2 = SeriableSingleton.getInstance();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("SeriableSingleton.obj");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(s2);
            oos.flush();
            oos.close();

            FileInputStream fis = new FileInputStream("SeriableSingleton.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            s1 = (SeriableSingleton)ois.readObject();

            System.out.println(s1);
            System.out.println(s2);
            System.out.println(s1 == s2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //枚举式单例防止序列化破坏测试
    @Test
    public void enumSingletonTest() {
        EnumSingleton s1 = null;
        EnumSingleton s2 = EnumSingleton.getInstance();
        s2.setData(new Object());

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("EnumSingleton.obj");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(s2);
            oos.flush();
            oos.close();

            FileInputStream fis = new FileInputStream("EnumSingleton.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            s1 = (EnumSingleton)ois.readObject();

            System.out.println(s1.getData());
            System.out.println(s2.getData());
            System.out.println(s1.getData() == s2.getData());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //枚举式单例防止反射破坏测试
    @Test
    public void enumSingletonTest2() {
        try {
            Class clazz = EnumSingleton.class;
            Constructor c = clazz.getDeclaredConstructor(String.class, int.class);
            c.setAccessible(true);
            EnumSingleton o = (EnumSingleton)c.newInstance("111", 2222);
            System.out.println(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //容器式单例测试
    @Test
    public void containerSingletonTest2() {

        try {
            ConcurrentExecutor.execute(new ConcurrentExecutor.RunHandler() {
                public void handler() {
                    Object obj = ContainerSingleton.getBean("fawda.design.singletonpattern.Pojo");
                    System.out.println(System.currentTimeMillis()+":"+obj);
                }
            }, 10, 6);//10个线程，６次并发


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //ThreadLocal单例测试
    @Test
    public void threadLocalSingletonTest() {
        System.out.println(Thread.currentThread());

        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());

        Thread t1 = new Thread(new ExectorThread());
        Thread t2 = new Thread(new ExectorThread());

        t1.start();
        t2.start();

        System.out.println("End");

    }

}
