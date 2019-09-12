package fawda.design.proxypattern;

import com.sun.org.apache.xpath.internal.operations.Or;
import fawda.design.proxypattern.dbroute.IOrderService;
import fawda.design.proxypattern.dbroute.Order;
import fawda.design.proxypattern.dbroute.OrderService;
import fawda.design.proxypattern.dbroute.proxy.OrderServiceDynamicProxy;
import fawda.design.proxypattern.dbroute.proxy.OrderServiceStaticProxy;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <b>时间:</b> <i>2019-03-17 23:17</i> 修订原因:初始化创建.详细说明:<br>
 * <br>
 * Action类{@linkplain }使用<br>
 *
 * <b>时间:</b> *年*月*日 *时*分*秒 修订原因:比如BUG修复或业务变更.详细说明:<br>
 *
 * @author liuyl@hzrisencn.com
 **/
public class DbRouterStaticAndDynamicProxyTest {

    @Test
    public void dbRouterProxyTest() throws ParseException {
        Order order = new Order();
        // order.setCreateTime(new Date().getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = sdf.parse("2017/02/02");
        order.setCreateTime(date.getTime());

        IOrderService orderService = new OrderServiceStaticProxy(new OrderService());
        orderService.createOrder(order);
    }

    @Test
    public void dynamicProxyTest() throws ParseException {
        Order order = new Order();
        // order.setCreateTime(new Date().getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = sdf.parse("2017/02/02");
        order.setCreateTime(date.getTime());

        IOrderService orderService = (IOrderService)new OrderServiceDynamicProxy().getInstance(new OrderService());

        orderService.createOrder(order);
    }
}
