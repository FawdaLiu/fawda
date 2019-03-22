package fawda.design.proxypattern.dbroute.db;

/**
 * <b>时间:</b> <i>2019-03-17 23:05</i> 修订原因:初始化创建.详细说明:<br>
 * <br>
 * Action类{@linkplain }使用<br>
 *
 * <b>时间:</b> *年*月*日 *时*分*秒 修订原因:比如BUG修复或业务变更.详细说明:<br>
 *
 * @author liuyl@hzrisencn.com
 **/
public class DynameicDataSourceEntity {


    private final static String DEFAULE_SOURCE = null;
    private final static ThreadLocal<String> local = new ThreadLocal<String>();

    private DynameicDataSourceEntity() {
    }

    private static String get() {
        return local.get();
    }

    public static void restore() {
        local.set(DEFAULE_SOURCE);
    }

    public static void set(String source) {
        local.set(source);
    }

    public static void set(int year) {
        local.set("DB_" + year);
    }
}
