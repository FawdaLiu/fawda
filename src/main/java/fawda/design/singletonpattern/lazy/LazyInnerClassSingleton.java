package fawda.design.singletonpattern.lazy;

/**
 * <b>时间:</b> <i>2019-03-17 14:45</i> 修订原因:初始化创建.详细说明:<br>
 * 静态内部类实现单例：ＪＶＭ底层的执行逻辑，完美避免了线程安全问题，　线程最优的方式<br>
 *     类似懒汉式的方式
 * Action类{@linkplain }使用<br>
 *
 * <b>时间:</b> *年*月*日 *时*分*秒 修订原因:比如BUG修复或业务变更.详细说明:<br>
 *
 * @author liuyl@hzrisencn.com
 **/
public class LazyInnerClassSingleton {
    private LazyInnerClassSingleton() {
        if (LazyHolder.LAZY != null) {//避免反射破坏单例
            throw new RuntimeException("不允许构造多个实例");
        }
    }

    public static final LazyInnerClassSingleton getInstance() {
        return LazyHolder.LAZY;
    }

    private static class LazyHolder {
        private static final LazyInnerClassSingleton LAZY = new LazyInnerClassSingleton();
    }
}
