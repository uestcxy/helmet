package com.tianyi.helmet.util;

import java.math.BigDecimal;

/**
 * Created by liuhanc on 2018/3/1.
 */
public class NumberFormatTest {
    public static void main(String[] a){
        double dd = 12;
        System.out.println(String.format("%.3f",dd));
        System.out.println(new BigDecimal(dd).setScale(3,BigDecimal.ROUND_HALF_UP).floatValue());

        double dd1 = 12.2344;
        System.out.println(String.format("%.3f",dd1));
        System.out.println(new BigDecimal(dd1).setScale(3,BigDecimal.ROUND_HALF_UP));

        double dd2 = 12.2347;
        System.out.println(String.format("%.3f",dd2));
        System.out.println(new BigDecimal(dd2).setScale(3,BigDecimal.ROUND_HALF_UP));

    }
}
