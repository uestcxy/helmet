package com.tianyi.helmet.controller;

import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuhanc on 2018/6/19.
 */
public class ImageControllerTest extends HelmetInterfaceControllerTest {

    @Test
    public void testUploadWhiteBoardImage() {
        imei = "liuhan";
        Map<String, String> params = new HashMap<>(3);
        params.put("termCode", "liuhan");
        params.put("createTime", "20180528194000");
        params.put("machineCode", "");
        params.put("tag", "白板");
        params.put("description", "测试白板图片");
        File file = new File("C:\\Users\\liuhanc\\Pictures\\output1.jpg");
        executeUpload("prod", "image/upload", params, "imagefile", file);
    }

}
