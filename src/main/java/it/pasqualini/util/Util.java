package it.pasqualini.util;

import java.text.DecimalFormat;

public class Util {

    public static void wait(int duration){
        try {
            Thread.sleep(duration);
        } catch (InterruptedException ignored) {

        }
    }

    public static int asInt(Double val){
        return (int)Math.round(val);
    }

    public static void println(Object o){
        System.out.println(o.toString());
    }



}
