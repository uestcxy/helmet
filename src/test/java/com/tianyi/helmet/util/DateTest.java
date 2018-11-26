package com.tianyi.helmet.util;

import java.util.Date;

/**
 * Created by liuhanc on 2017/11/1.
 */
public class DateTest {

    public static void main(String[] a){
        System.out.println(new Date(1514944800632l));
        System.out.println(new Date(1514963650632l));
//        LocalDateTime ldt1 = LocalDateTime.of(2017,1,20,23,59,59);
//        LocalDateTime ldt2 = LocalDateTime.of(2017,1,21,0,00,0);
//        System.out.println(Dates.duration(ldt1,ldt2));// 1000
//        System.out.println(Dates.duration(ldt2,ldt1));// -1000
//        int result = ldt1.get(MILLI_OF_DAY);
//        System.out.println(result);
//        long baseTime = Dates.toDate(LocalDateTime.of(2017,12,20,17,6,0)).getTime();
//        long time = System.currentTimeMillis();
//        long duration = time - baseTime;
//        System.out.println( Dates.formatMillieseconds(duration,'.'));
//        System.out.println(time/Dates.ONE_HOUR_MILLIES);
//        System.out.println(time/(60*60*1000));
    }
}
