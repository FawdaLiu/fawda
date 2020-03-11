package fawda.java.string;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class Base64Test {
    public static void main(String[] args) {
        try {
            //win
            String userQues = "win";
            Decoder decoder = Base64.getDecoder();
            Encoder encoder = Base64.getEncoder();
            System.out.println(new String(userQues.getBytes("GBK")));
            System.out.println(new String(decoder.decode(userQues.getBytes("GBK"))));
            System.out.println(encoder.encodeToString(new String(decoder.decode(userQues.getBytes("GBK"))).getBytes("GBK")));
            
            //linux
            userQues = "linux";
            System.out.println(new String(userQues.getBytes("GBK")));
            System.out.println(new String(decoder.decode(userQues.getBytes("GBK")), "GBK"));
            System.out.println(encoder.encodeToString(new String(decoder.decode(userQues.getBytes("GBK")), "GBK").getBytes("GBK")));
            //在linux出现了换行符，导致解析异常
            String bTemp = encoder.encodeToString("123".getBytes());  
            bTemp = bTemp.replaceAll("[\\s*\t\n\r]", "");  
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
