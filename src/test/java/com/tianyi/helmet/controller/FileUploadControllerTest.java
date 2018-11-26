package com.tianyi.helmet.controller;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuhanc on 2018/3/20.
 */
public class FileUploadControllerTest extends HelmetInterfaceControllerTest {

    @Before
    public void setup() {
        setSignHeader = true;
        imei = "16d80fb1-5592-451d-a8e3-164560e0cd74";
    }

    @Test
    public void testUpload() throws Exception {
        Map<String, String> params = new HashMap<>(3);
//        params.put("termCode", "liuhan");
//        params.put("tag", "二手机");
        params.put("description", "helmet1037的蓝牙工况由java-junit代为上传");

        File file = new File("D:\\补传文件\\gps");
        Arrays.stream(file.listFiles()).forEach(gpsFile->{
            System.out.println("上传gps文件:"+gpsFile.getAbsolutePath());
            String name = gpsFile.getName();
            String time = name.substring(0,name.length()-3);
            params.put("createTime", time);
            try{
                executeUpload("prod", "file/upload", params, "file", gpsFile);
            }catch(Exception e){
                System.out.println("上传gps文件失败."+gpsFile.getAbsolutePath());
                e.printStackTrace();
            }
        });
    }

}
