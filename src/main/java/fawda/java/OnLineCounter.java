package fawda.java;

public class OnLineCounter {
    private static long onLineCounter = 0;
    
    public static void addCounter() {
        onLineCounter ++;
    }
    
    public static void reduceCounter() {
        onLineCounter --;
    }
    
    public static long getCounter() {
        return onLineCounter;
    }
}
