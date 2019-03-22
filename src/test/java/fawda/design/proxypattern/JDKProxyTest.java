package fawda.design.proxypattern;

import fawda.design.proxypattern.dynamicproxy.jdkproxy.Girl;
import fawda.design.proxypattern.dynamicproxy.jdkproxy.JDKMeipo;
import fawda.design.proxypattern.staticproxy.Person;
import org.junit.Test;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;

/**
 * <b>时间:</b> <i>2019-03-17 23:44</i> 修订原因:初始化创建.详细说明:<br>
 * <br>
 * Action类{@linkplain }使用<br>
 *
 * <b>时间:</b> *年*月*日 *时*分*秒 修订原因:比如BUG修复或业务变更.详细说明:<br>
 *
 * @author liuyl@hzrisencn.com
 **/
public class JDKProxyTest {

    @Test
    public void jdkProxyTest() {
        try {
            Person person = (Person) new JDKMeipo().getInstance(new Girl());
            person.findLove();

            byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{Person.class});
            FileOutputStream fos = new FileOutputStream("$Proxy0.class");
            fos.write(bytes);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
