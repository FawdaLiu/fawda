package fawda.design.singletonpattern;

import fawda.design.singletonpattern.lazy.LazyDoubleCheckSingleton;
import fawda.design.singletonpattern.lazy.LazySimpleSingleton;
import fawda.design.singletonpattern.threadlocal.ThreadLocalSingleton;

/**
 * <b>时间:</b> <i>2019-03-17 14:17</i> 修订原因:初始化创建.详细说明:<br>
 * 使用两个线程演示<br>
 * Action类{@linkplain }使用<br>
 *
 * <b>时间:</b> *年*月*日 *时*分*秒 修订原因:比如BUG修复或业务变更.详细说明:<br>
 *
 * @author liuyl@hzrisencn.com
 **/
public class ExectorThread implements Runnable {


    public void run() {
        // LazySimpleSingleton singleton = LazySimpleSingleton.getInstance();
        // LazyDoubleCheckSingleton singleton = LazyDoubleCheckSingleton.getInstance();

        System.out.println(Thread.currentThread().getName() + ":" + ThreadLocalSingleton.getInstance());
        System.out.println(Thread.currentThread().getName() + ":" + ThreadLocalSingleton.getInstance());
        System.out.println(Thread.currentThread().getName() + ":" + ThreadLocalSingleton.getInstance());
        System.out.println(Thread.currentThread().getName() + ":" + ThreadLocalSingleton.getInstance());
        System.out.println(Thread.currentThread().getName() + ":" + ThreadLocalSingleton.getInstance());
        System.out.println(Thread.currentThread().getName() + ":" + ThreadLocalSingleton.getInstance());
        // System.out.println(Thread.currentThread().getName() + ":" + singleton);
    }
}
