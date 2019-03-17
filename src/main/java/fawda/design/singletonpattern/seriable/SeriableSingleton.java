package fawda.design.singletonpattern.seriable;

import java.io.Serializable;

/**
 * <b>时间:</b> <i>2019-03-17 15:01</i> 修订原因:初始化创建.详细说明:<br>
 * <br>
 * Action类{@linkplain }使用<br>
 *
 * <b>时间:</b> *年*月*日 *时*分*秒 修订原因:比如BUG修复或业务变更.详细说明:<br>
 *
 * @author liuyl@hzrisencn.com
 **/
public class SeriableSingleton implements Serializable {
    public static final SeriableSingleton INSTANCE = new SeriableSingleton();

    private SeriableSingleton() {
    }

    public static SeriableSingleton getInstance() {
        return INSTANCE;
    }

    //重写readResolve方法，只不过是覆盖了反序列化出来的对象
    //还是创建了两次，发生在ＪＶＭ层面，相对来说比较安全
    //之前反序列化出来的对象会被ＧＣ回收
    private Object readResolve() {
        return INSTANCE;
    }


}
