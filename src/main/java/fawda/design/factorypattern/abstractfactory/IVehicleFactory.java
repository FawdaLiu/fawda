package fawda.design.factorypattern.abstractfactory;

import com.gupaoedu.fawda.design.factorypattern.IVehicle;

/**
 * <b>时间:</b> <i>2019-03-07 23:41</i> 修订原因:初始化创建.详细说明:<br>
 * 抽象工厂<br>
 * 产品族， 产品等级<br>
 * Action类{@linkplain }使用<br>
 *
 * <b>时间:</b> *年*月*日 *时*分*秒 修订原因:比如BUG修复或业务变更.详细说明:<br>
 *
 * @author liuyl@hzrisencn.com
 **/
public interface IVehicleFactory {
    IVehicle create();
    IFrame provide();
    IMachine assemble();
}
