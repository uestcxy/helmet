package com.tianyi.helmet.util;

/**
 * Created by liuhanc on 2017/12/21.
 */
public class StringTest {

    public static void main(String [] a){
        java.io.File f= new java.io.File("D:\\test\\163.srt");
        String path = f.getAbsolutePath();
        System.out.println(path);
        System.out.println(path.replaceAll("\\\\","\\\\\\\\").replace(":","\\:"));
    }
}
