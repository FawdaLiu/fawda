package fawda.java.enumtest;

import java.util.Arrays;

/**
 * <b>@Date:</b> <i>2018/8/26 11:47</i> 修订原因:初始化创建.详细说明:<br>
 * <b>@Desoription:</b> <br>
 *
 * <b>@Auther:</b> <i>Liuyl</i>
 */
public enum Day2 {
    MONDAY("星期一", "1", 1),
    TUESDAY("星期二", "2", 2),
    WEDNESDAY("星期三", "3", 3),
    THURSDAY("星期四", "4", 4),
    FRIDAY("星期五", "5", 5),
    SATURDAY("星期六", "6", 6),
    SUNDAY("星期日", "7", 7);//记住要用分号结束
    private String desc;//中文描述
    private String a;
    private int b;
    /**
     * 私有构造,防止被外部调用
     * @param desc
     */
    private Day2(String desc, String a, int b){
        this.desc=desc;
        this.a=a;
        this.b=b;
    }

    /**
     * 定义方法,返回描述,跟常规类的定义没区别
     * @return
     */
    public String getDesc(){
        return desc;
    }

    public String getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public static void main(String[] args) {
        Day2[] values = Day2.values();
        System.out.println(Arrays.toString(values));
        for (Day2 day:Day2.values()) {
            System.out.println("name:"+day.name()+
                    ",desc:"+day.getDesc()+",a:"+day.getA()+",b:"+day.getB());
        }
    }
}
