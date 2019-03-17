package fawda.design.singletonpattern.lazy;

/**
 * <b>时间:</b> <i>2019-03-17 13:52</i> 修订原因:初始化创建.详细说明:<br>
 * 懒汉式：双重锁<br>
 *
 * CPU执行时候会转换成ＪＶＭ指令执行
 * 指令重排序问题，加volatile
 * 1、分配内存给这个对象
 * 2、初始化对象
 * 3、将初始化好的对象和内存地址建立关联，赋值
 * 4、用户初次访问
 * 懒汉式：<br>
 * 懒汉式：<br>
 *  <br>
 * <br>
 * <br>
 * Action类{@linkplain }使用<br>
 *
 * <b>时间:</b> *年*月*日 *时*分*秒 修订原因:比如BUG修复或业务变更.详细说明:<br>
 *
 * @author liuyl@hzrisencn.com
 **/
public class LazyDoubleCheckSingleton {

    private static LazyDoubleCheckSingleton lazy = null;

    private LazyDoubleCheckSingleton() {
    }

    public static LazyDoubleCheckSingleton getInstance() {
        if (lazy == null) {
            synchronized (LazyDoubleCheckSingleton.class) {
                if (lazy == null) {
                    lazy = new LazyDoubleCheckSingleton();
                }
            }
        }
        return lazy;
    }
}
