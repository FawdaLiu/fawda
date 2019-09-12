package fawda.design.prototypepattern;

import fawda.design.prototypepattern.deep.DeepPrototype;
import fawda.design.prototypepattern.deep.QiTianDaSheng;
import fawda.design.prototypepattern.simple.Client;
import fawda.design.prototypepattern.simple.ConcreatePrototypeA;
import fawda.design.prototypepattern.simple.Prototype;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>时间:</b> <i>2019-03-17 18:03</i> 修订原因:初始化创建.详细说明:<br>
 * <br>
 * Action类{@linkplain }使用<br>
 *
 * <b>时间:</b> *年*月*日 *时*分*秒 修订原因:比如BUG修复或业务变更.详细说明:<br>
 *
 * @author liuyl@hzrisencn.com
 **/
public class PrototypeTest {

    @Test
    public void simplePrototypeTeset() {
        ConcreatePrototypeA concreatePrototype = new ConcreatePrototypeA();
        concreatePrototype.setAge(18);
        concreatePrototype.setName("TOM");
        List hobbies = new ArrayList<String>();
        hobbies.add("学习");
        concreatePrototype.setHobbies(hobbies);

        Client client = new Client();
        ConcreatePrototypeA copy = (ConcreatePrototypeA)client.startClone(concreatePrototype);

        System.out.println(copy);

        System.out.println("克隆对象引用类型地址的值：" + copy.getHobbies());
        System.out.println("原对象中的引用类型地址的值：" + concreatePrototype.getHobbies());

        System.out.println("对象地址比较："+ (copy.getHobbies() == concreatePrototype.getHobbies()));
    }

    @Test
    public void deepCloneTest() {
        QiTianDaSheng qiTianDaSheng = new QiTianDaSheng();
        try {
            QiTianDaSheng clone = (QiTianDaSheng)qiTianDaSheng.clone();
            System.out.println("深克隆：" + (qiTianDaSheng.jinGuBang == clone.jinGuBang));
        } catch (Exception e) {
            e.printStackTrace();
        }

        QiTianDaSheng q = new QiTianDaSheng();
        QiTianDaSheng n = q.shallowClone(q);
        System.out.println("浅克隆：" + (q.jinGuBang == n.jinGuBang));
    }


    @Test
    public void deepPrototypeTest() {
        DeepPrototype dp = new DeepPrototype();
        dp.setAge(16);
        dp.setName("Fawda");
        List hobbies = new ArrayList<String>();
        hobbies.add("readding");
        hobbies.add("playing tennies");
        dp.setHobbies(hobbies);

        DeepPrototype deepClone = (DeepPrototype)dp.clone();

        System.out.println(dp.getHobbies() == deepClone.getHobbies());

        DeepPrototype simpleClone = (DeepPrototype)dp.simpleClone();
        System.out.println(dp.getHobbies() == simpleClone.getHobbies());

    }

}
