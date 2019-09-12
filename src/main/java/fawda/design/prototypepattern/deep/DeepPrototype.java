package fawda.design.prototypepattern.deep;

import fawda.design.prototypepattern.simple.Prototype;

import java.io.*;
import java.lang.reflect.Method;
import java.util.List;

/**
 * <b>时间:</b> <i>2019-03-17 17:57</i> 修订原因:初始化创建.详细说明:<br>
 * <br>
 * Action类{@linkplain }使用<br>
 *
 * <b>时间:</b> *年*月*日 *时*分*秒 修订原因:比如BUG修复或业务变更.详细说明:<br>
 *
 * @author liuyl@hzrisencn.com
 **/
public class DeepPrototype implements Prototype, Cloneable, Serializable {

    private int age;
    private String name;
    private List hobbies;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getHobbies() {
        return hobbies;
    }

    public void setHobbies(List hobbies) {
        this.hobbies = hobbies;
    }

    public Prototype clone() {
        return this.deepClone();
    }

    public Prototype deepClone() {
        DeepPrototype result = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);

            ObjectInputStream ooi = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
            result = (DeepPrototype)ooi.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public Prototype simpleClone() {
        DeepPrototype deepPrototype = new DeepPrototype();
        deepPrototype.setAge(this.age);
        deepPrototype.setName(this.name);
        deepPrototype.setHobbies(this.hobbies);
        return deepPrototype;
    }
}
