package fawda.model;

import com.risen.base.encry.DesHexPwdCoder;

public class Demo {
    public static void main(String[] args) {
        DesHexPwdCoder desHexPwdCoder = new DesHexPwdCoder();
        System.out.println(desHexPwdCoder.decode("c4b8a4d0682337a3431d918c3d47fee3"));
    }
}
