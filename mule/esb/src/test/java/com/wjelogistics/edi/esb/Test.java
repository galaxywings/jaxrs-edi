package com.wjelogistics.edi.esb;

public class Test {

    @org.junit.Test
    public void test() {
        String src = "step1,step2";
        System.out.println(src.split(",", 2)[0]);
        System.out.println(src.split(",", 2)[1]);
    }
}
