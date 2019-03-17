package fawda.design.factorypattern;

/**
 * <b>时间:</b> <i>2019-03-07 22:52</i> 修订原因:初始化创建.详细说明:<br>
 * 电动车汽车<br>
 * Action类{@linkplain }使用<br>
 *
 * <b>时间:</b> *年*月*日 *时*分*秒 修订原因:比如BUG修复或业务变更.详细说明:<br>
 *
 * @author liuyl@hzrisencn.com
 **/
public class ElectricIVehicle implements IVehicle {
    @Override
    public void create() {
        System.out.println("New electricVehicle!");
    }
}
