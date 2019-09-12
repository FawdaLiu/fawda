package fawda.design.singletonpattern.lazy;

/**
 * <b>时间:</b> <i>2019-03-17 13:52</i> 修订原因:初始化创建.详细说明:<br>
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
public class LazySimpleSingleton {

    private static LazySimpleSingleton lazy = null;

    private LazySimpleSingleton() {
    }

    //ｊｄｋ１．６优化之后依然存在性能问题
    public static synchronized LazySimpleSingleton getInstance() {
        if (lazy == null) {
            lazy = new LazySimpleSingleton();
        }
        return lazy;
    }
}
