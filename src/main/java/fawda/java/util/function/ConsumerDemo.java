package fawda.java.util.function;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;

public class ConsumerDemo {
    /*BiConsumer<T,U>
    Consumer<T>
    DoubleConsumer
    IntConsumer
    LongConsumer
    ObjDoubleConsumer<T>
    ObjIntConsumer<T>
    ObjLongConsumer<T>
*/

    public static void main(String[] args) throws Exception {
        System.out.println("------show biConsumer------");
        BiConsumer<T, U> biConsumer = (T t, U u)->{System.out.println(String.format("biConsumer receive-->%s+%s", t,u));};
        BiConsumer<T, U> biConsumer2 = (T t, U u)->{System.out.println(String.format("biConsumer2 receive-->%s+%s", t,u));};

        biConsumer.andThen(biConsumer2).accept(new T(), new U());


        System.out.println("------show consumer------");
        Consumer<T> consumer = (T t)->{System.out.println(String.format("consumer receive-->%s", t));};
        Consumer<T> consumer2 = (T t)->{System.out.println(String.format("consumer2 receive-->%s", t));};
        consumer.andThen(consumer2).accept(new T());

        System.out.println("------show doubleConsumer------");
        DoubleConsumer doubleConsumer = (d)->{System.out.println(String.format("doubleConsumer receive-->%s", d));};
        doubleConsumer.accept(100_111.111_001d);

        System.out.println("------show intConsumer------");
        IntConsumer intConsumer = (i)->{System.out.println(String.format("doubleConsumer receive-->%s", i));};
        intConsumer.accept(1_111);

        System.out.println("------show longConsumer------");
        LongConsumer longConsumer = (l)->{System.out.println(String.format("longConsumer receive-->%s", l));};
        longConsumer.accept(111_111_111_111L);

        System.out.println("------show longConsumer------");
        ObjDoubleConsumer<T> objDoubleConsumer = (T t, double d)->{System.out.println(String.format("objDoubleConsumer receive-->%s+%s", t,d));};
        objDoubleConsumer.accept(new T(), 100_111.111_001d);

        System.out.println("------show objIntConsumer------");
        ObjIntConsumer<T> objIntConsumer = (T t, int i)->{System.out.println(String.format("objIntConsumer receive-->%s+%s", t,i));};
        objIntConsumer.accept(new T(), 1_111);

        System.out.println("------show objLongConsumer------");
        ObjLongConsumer<T> objLongConsumer = (T t, long l)->{System.out.println(String.format("objLongConsumer receive-->%s+%s", t,l));};
        objLongConsumer.accept(new T(), 111_111_111_111L);


        System.out.println("------show biConsumer------");
        ThiConsumer<T, U, W> thiConsumer = (T t, U u, W w)->{System.out.println(String.format("thiConsumer receive-->%s+%s+%s", t,u, w));};
        ThiConsumer<T, U, W> thiConsumer2 = (T t, U u, W w)->{System.out.println(String.format("thiConsumer2 receive-->%s+%s+%s", t,u, w));};

        thiConsumer.andThen(thiConsumer2).accept(new T(), new U(), new W());
    }

    @FunctionalInterface
    static interface ThiConsumer<T,U,W>{
        void accept(T t, U u, W w);

        default ThiConsumer<T,U,W> andThen(ThiConsumer<? super T,? super U,? super W> consumer){
            return (t, u, w)->{
                accept(t, u, w);
                consumer.accept(t, u, w);
            };
        }
    }

    static class T{
        @Override
        public String toString() {
            return "T";
        }
    }
    static class U{
        @Override
        public String toString() {
            return "U";
        }
    }
    static class W{
        @Override
        public String toString() {
            return "W";
        }
    }
    static class R{
        @Override
        public String toString() {
            return "R";
        }
    }


}