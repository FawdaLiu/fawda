package fawda.java.enumtest;

import java.util.Arrays;

/**
 * <b>@Date:</b> <i>2018/8/26 11:27</i> 修订原因:初始化创建.详细说明:<br>
 * <b>@Desoription:</b> <br>
 *
 * <b>@Auther:</b> <i>Liuyl</i>
 */
public class EnumDemo {
    public static void main(String[] args) {
        Day[] days = new Day[]{Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY, Day.SATURDAY, Day.SUNDAY};
        for (int i = 0; i < days.length; i++) {
            System.out.println("day["+i+"].ordinal():"+days[i].ordinal());
        }
        System.out.println("-------------------------------------");
        //通过compareTo方法比较,实际上其内部是通过ordinal()值比较的
        System.out.println("days[0].compareTo(days[1]):"+days[0].compareTo(days[1]));
        System.out.println("days[0].compareTo(days[1]):"+days[0].compareTo(days[2]));
        //获取该枚举对象的Class对象引用,当然也可以通过getClass方法
        Class<?> clazz = days[0].getDeclaringClass();
        System.out.println("clazz:"+clazz);
        System.out.println("-------------------------------------");
        //name()
        System.out.println("days[0].name():"+days[0].name());
        System.out.println("days[1].name():"+days[1].name());
        System.out.println("days[2].name():"+days[2].name());
        System.out.println("days[3].name():"+days[3].name());

        System.out.println("-------------------------------------");
        System.out.println("days[0].toString():"+days[0].toString());
        System.out.println("days[1].toString():"+days[1].toString());
        System.out.println("days[2].toString():"+days[2].toString());
        System.out.println("days[3].toString():"+days[3].toString());

        System.out.println("-------------------------------------");

        Day d=Enum.valueOf(Day.class, days[0].name());
        Day d2=Day.valueOf(Day.class, days[0].name());
        System.out.println("d:"+d);
        System.out.println("d2:"+d2);
        //正常使用
        Day[] ds=Day.values();
        //向上转型Enum
        Enum e = Day.MONDAY;
        //无法调用,没有此方法
        //e.values();
        //获取class对象引用
        Class<?> clasz = e.getDeclaringClass();
        if(clasz.isEnum()) {
            Day[] dsz = (Day[]) clasz.getEnumConstants();
            System.out.println("dsz:"+ Arrays.toString(dsz));
        }
    }

    enum Day{
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
}
