package fawda.design.singletonpattern.register;

/**
 * <b>时间:</b> <i>2019-03-17 15:24</i> 修订原因:初始化创建.详细说明:<br>
 * 注册式单例：　枚举式单例<br>
 *     从ｊｄｋ层面就为枚举不被序列化和反序列化破坏,详见Constructor
 * Action类{@linkplain }使用<br>
 *
 * <b>时间:</b> *年*月*日 *时*分*秒 修订原因:比如BUG修复或业务变更.详细说明:<br>
 *
 * @author liuyl@hzrisencn.com
 **/
public enum EnumSingleton {
    INSTANCE;

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static EnumSingleton getInstance() {
        return INSTANCE;
    }
}
