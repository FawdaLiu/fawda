package fawda.java.util.function;

import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class FunctionDemo {
    /**
     * BiFunction<T,U,R> BinaryOperator<T> DoubleBinaryOperator DoubleFunction
     * <R> DoubleToIntFunction DoubleToLongFunction DoubleUnaryOperator Function
     * <T,R> IntBinaryOperator IntFunction<R> IntToDoubleFunction
     * IntToLongFunction IntUnaryOperator LongBinaryOperator LongFunction
     * <R> LongToDoubleFunction LongToIntFunction LongUnaryOperator
     * ToDoubleBiFunction<T,U> ToDoubleFunction<T> ToIntBiFunction
     * <T,U> ToIntFunction<T> ToLongBiFunction<T,U> ToLongFunction
     * <T> UnaryOperator<T>
     * 
     * @param args
     */
    public static void main(String[] args) {
        BiFunction<T, U, R> biFunction = (T t, U u) -> {
            System.out.println(String.format("biConsumer receive-->%s+%s", t, u));
            return new R();
        };
        biFunction.apply(new T(), new U());

        Function<R, W> function = (R r) -> {
            System.out.println(String.format("function receive-->%s", r));
            return new W();
        };

        W w = biFunction.andThen(function).apply(new T(), new U());
        System.out.println(w);

        BinaryOperator<T> binaryOperator = (T t1, T t2) -> {
            System.out.println(String.format("binaryOperator receive-->%s+%s", t1, t2));
            return new Random().nextInt(10) >= 5 ? t2 : t1;
        };

        T tr = binaryOperator.apply(new T(), new T());
        System.out.println(tr);
    }

    static class T {
        @Override
        public String toString() {
            return "T";
        }
    }

    static class U {
        @Override
        public String toString() {
            return "U";
        }
    }

    static class W {
        @Override
        public String toString() {
            return "W";
        }
    }

    static class R {
        @Override
        public String toString() {
            return "R";
        }
    }
}