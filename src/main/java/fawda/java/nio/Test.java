package fawda.java.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Test {
    static private final int start = 0;
    static private final int size = 1024;

    public static void main(String[] args) {
        RandomAccessFile raf;
        try {
            raf = new RandomAccessFile("usemappedfile.txt", "rw");
            FileChannel fc = raf.getChannel();

            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, start, size);

            mbb.put(0, (byte) 97);
            mbb.put(1023, (byte) 122);
            for (int i = start; i < size; i++) {
                System.out.print((char) mbb.get(i));
            }
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
