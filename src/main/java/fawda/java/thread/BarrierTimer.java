// package fawda.java.thread;
//
// public class BarrierTimer implements Runnable{
//
//     private boolean started ;
//
//     private long startTime , endTime;
//
//     public synchronized void run() {
//
//         long t = System.nanoTime() ;
//
//         if (!started) {
//
//             started = true;
//
//             startTime = t;
//
//         } else
//
//             endTime = t;
//
//     }
//
//     public synchronized void clear() {
//
//         started = false;
//
//     }
//
//     public synchronized long getTime() {
//
//         return endTime - startTime;
//
//     }
//
//     public static void main(String[] args) throws Exception {
//
//         int tpt = 100000 ; // trials per thread
//
//         for (int cap = 1; cap <= 1000; cap *= 10) {
//
//             System.out.println( "Capacity: " + cap);
//
//             for (int pairs = 1; pairs <= 128 ; pairs *= 2) {
//
//                 TimedPutTakeTest t = new TimedPutTakeTest(cap, pairs, tpt);
//
//                 System.out.print("Pairs: " + pairs + "\t") ;
//
//                 t.test();
//
//                 System.out.print("\t ");
//
//                 Thread.sleep(1000);
//
//                 t.test();
//
//                 System.out.println();
//
//                 Thread.sleep(1000);
//
//             }
//
//         }
//
//         PutTakeTest.pool.shutdown() ;
//
//     }
//
//
//
// }
//
