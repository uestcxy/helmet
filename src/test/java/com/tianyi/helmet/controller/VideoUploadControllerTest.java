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
public class VideoUploadControllerTest extends HelmetInterfaceControllerTest {

    @Before
    public void setup() {
        setSignHeader = true;
        imei="imei_liuhan_test";
    }

    @Test
    public void testUpload() throws Exception {
        Map<String, String> params = new HashMap<>(3);
        params.put("createTime", "20180711175700");
        params.put("machineCode", "");
        params.put("tag", "故障,维修");
        params.put("description", "工单视频");
        params.put("orderNo", "20180703004");

        File file = new File("D:\\补传文件\\mobile\\20180723171820.mp4");
        executeUpload("local", "video/upload", params, "videofile", file);
    }

}

