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
public class CollaborateControllerTest extends HelmetInterfaceControllerTest {

    @Test
    public void testGetWhiteBoardImage() throws IOException {
        imei = "helmet1047";
        byte[] bytes = executeDownFile("prod", "/collaborate/whiteboard/image/helmet1047", Collections.emptyMap());
        System.out.println("下载白板图片，大小=" + (bytes == null ? 0 : bytes.length));
        Files.write(bytes, new File("C:\\Users\\liuhanc\\Pictures\\output1-白板helmet1047.jpg"));
    }
}
