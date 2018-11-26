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
public class VideoAudioUploadControllerTest extends HelmetInterfaceControllerTest {

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
        params.put("tag", "测试");
        params.put("description","测试视频音频合并");
        params.put("orderNo", "20180703004");

        Map<String, File> uploadFiles = new HashMap<>(1);
        uploadFiles.put("videofile", new File("d://201807222354.h264"));
        uploadFiles.put("audiofile", new File("d://201807222354.aac"));
        executeMultipleUpload("vpn", "videoaudio/upload", params,uploadFiles);
    }

}
