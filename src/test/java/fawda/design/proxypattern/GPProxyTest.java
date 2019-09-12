package fawda.design.proxypattern;

import fawda.design.proxypattern.dynamicproxy.gpproxy.GPMeipo;
import fawda.design.proxypattern.dynamicproxy.jdkproxy.Girl;
import fawda.design.proxypattern.staticproxy.Person;
import org.junit.Test;

/**
 * <b>时间:</b> <i>2019-03-18 00:23</i> 修订原因:初始化创建.详细说明:<br>
 * <br>
 * Action类{@linkplain }使用<br>
 *
 * <b>时间:</b> *年*月*日 *时*分*秒 修订原因:比如BUG修复或业务变更.详细说明:<br>
 *
 * @author liuyl@hzrisencn.com
 **/
public class GPProxyTest {

    @Test
    public void gpProxyTest() {
        Person obj = (Person)new GPMeipo().getInstance(new Girl());
        obj.findLove();

        obj.study();


    }
}
