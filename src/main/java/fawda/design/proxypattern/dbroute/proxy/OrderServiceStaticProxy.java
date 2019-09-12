package fawda.design.proxypattern.dbroute.proxy;

import fawda.design.proxypattern.dbroute.IOrderService;
import fawda.design.proxypattern.dbroute.Order;
import fawda.design.proxypattern.dbroute.db.DynameicDataSourceEntity;
import sun.awt.DesktopBrowse;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <b>时间:</b> <i>2019-03-17 23:11</i> 修订原因:初始化创建.详细说明:<br>
 * <br>
 * Action类{@linkplain }使用<br>
 *
 * <b>时间:</b> *年*月*日 *时*分*秒 修订原因:比如BUG修复或业务变更.详细说明:<br>
 *
 * @author liuyl@hzrisencn.com
 **/
public class OrderServiceStaticProxy implements IOrderService {

    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

    private IOrderService orderService;

    public OrderServiceStaticProxy(IOrderService orderService) {
        this.orderService = orderService;
    }

    public int createOrder(Order order) {
        Long time = order.getCreateTime();
        Integer dbRouter = Integer.valueOf(yearFormat.format(new Date(time)));
        System.out.println("静态代理类自动分配到【DB_" + dbRouter + "】数据源处理数据");
        DynameicDataSourceEntity.set(dbRouter);

        this.orderService.createOrder(order);

        DynameicDataSourceEntity.restore();
        return 0;
    }
}
