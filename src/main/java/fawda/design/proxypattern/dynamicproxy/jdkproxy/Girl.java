package fawda.design.proxypattern.dynamicproxy.jdkproxy;

import fawda.design.proxypattern.staticproxy.Person;

import java.lang.reflect.ParameterizedType;

/**
 * <b>时间:</b> <i>2019-03-17 23:39</i> 修订原因:初始化创建.详细说明:<br>
 * <br>
 * Action类{@linkplain }使用<br>
 *
 * <b>时间:</b> *年*月*日 *时*分*秒 修订原因:比如BUG修复或业务变更.详细说明:<br>
 *
 * @author liuyl@hzrisencn.com
 **/
public class Girl implements Person {

    public void findLove() {
        System.out.println("高富帅");
        System.out.println("身高180cm");
        System.out.println("6块腹肌");
    }

    @Override
    public void study() {
        System.out.println("学习");
    }
}
