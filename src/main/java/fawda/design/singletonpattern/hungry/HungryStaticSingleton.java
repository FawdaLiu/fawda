package fawda.design.singletonpattern.hungry;

/**
 * <b>时间:</b> <i>2019-03-17 13:37</i> 修订原因:初始化创建.详细说明:<br>
 *  饿汉式：<br>
 *  1、构造私有<br>
 *  2、提供全局访问点<br>
 *  <br>
 * Action类{@linkplain }使用<br>
 *
 * <b>时间:</b> *年*月*日 *时*分*秒 修订原因:比如BUG修复或业务变更.详细说明:<br>
 *
 * @author liuyl@hzrisencn.com
 **/
public class HungryStaticSingleton {

    private static final HungryStaticSingleton hungrystaticSingleton;

    static {
        hungrystaticSingleton = new HungryStaticSingleton();
    }

    private HungryStaticSingleton() {

    }

    public static HungryStaticSingleton getInstance() {
        return hungrystaticSingleton;
    }
}
