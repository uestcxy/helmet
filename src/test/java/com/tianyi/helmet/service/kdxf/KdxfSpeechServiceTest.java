package com.tianyi.helmet.service.kdxf;

import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.service.kdxf.KdxfSpeechService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * Created by liuhanc on 2018/5/24.
 */
public class KdxfSpeechServiceTest extends BaseServiceTest {
    @Autowired
    private KdxfSpeechService kdxfSpeechService;

    @Test
    public void testSpeech(){
        String fileName = "百度好.wav";
        File music = new File("D:\\服务日志资源测试\\"+fileName);
        kdxfSpeechService.asrAudioToText(music,20,200);
    }
}
