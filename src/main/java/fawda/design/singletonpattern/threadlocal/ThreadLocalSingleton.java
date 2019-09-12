package fawda.design.singletonpattern.threadlocal;

/**
 * <b>时间:</b> <i>2019-03-17 16:16</i> 修订原因:初始化创建.详细说明:<br>
 * ThreadLocal单例:线程间的安全<br>
 *     使用ThreadLocal动态实现多数据源动态切换
 * Action类{@linkplain }使用<br>
 *
 * <b>时间:</b> *年*月*日 *时*分*秒 修订原因:比如BUG修复或业务变更.详细说明:<br>
 *
 * @author liuyl@hzrisencn.com
 **/
public class ThreadLocalSingleton {

    private ThreadLocalSingleton() {

    }

    private static final ThreadLocal<ThreadLocalSingleton> threadLOcalInstance =
            new ThreadLocal<ThreadLocalSingleton>(){
                @Override
                protected ThreadLocalSingleton initialValue() {
                    return new ThreadLocalSingleton();
                }
            };

    public static ThreadLocalSingleton getInstance() {
        return threadLOcalInstance.get();
    }
}
