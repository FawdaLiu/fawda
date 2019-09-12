package fawda.design.proxypattern;

import fawda.design.proxypattern.dynamicproxy.cglibproxy.CGlibMeiPo;
import fawda.design.proxypattern.dynamicproxy.cglibproxy.Customer;
import net.sf.cglib.core.DebuggingClassWriter;
import org.junit.Test;

/**
 * <b>时间:</b> <i>2019-03-22 23:36</i> 修订原因:初始化创建.详细说明:<br>
 * <br>
 * Action类{@linkplain }使用<br>
 *
 * <b>时间:</b> *年*月*日 *时*分*秒 修订原因:比如BUG修复或业务变更.详细说明:<br>
 *
 * @author liuyl@hzrisencn.com
 **/
public class CGlibTest {


    @Test
    public void CglibTest() throws Exception {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "cglib_proxy_classes");

        Customer cGlibMeiPo = (Customer)new CGlibMeiPo().getInstance(Customer.class);
        System.out.println(cGlibMeiPo);
        cGlibMeiPo.findLove();


    }
}
