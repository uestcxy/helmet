package com.tianyi.helmet.service.baidu;

import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.service.baidu.BaiduSpeechService;
import com.tianyi.helmet.server.service.support.FfmpegService;
import org.apache.log4j.lf5.util.StreamUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by liuhanc on 2018/4/3.
 */
public class BaiduSpeechServiceTest extends BaseServiceTest {
    @Autowired
    private BaiduSpeechService baiduSpeechService;
    @Autowired
    FfmpegService ffmpegService;

    @Test
    public void testRead() throws Exception {
        String dir = "D:\\服务日志资源测试\\";
//        String mp4 = "百度好.mp4";
        String mp3 = "百度好.mp3";
        String wav = "百度好.wav";
//        ffmpegService.extractMp3FromVideo(dir + mp4, dir + mp3);
//        ffmpegService.audioTranscode(dir + mp3, dir + wav, 1, 16000,  "pcm_s16le", "wav");

        File music = new File(dir + wav);
        ByteArrayOutputStream byos = new ByteArrayOutputStream();
        StreamUtils.copy(new FileInputStream(music), byos);
        byte[] bytes = byos.toByteArray();
        /**
         * {"result":["百度百度百度语音识别效果好百度语音识别比科大讯飞好百度厉害百度浏百度好，"],"err_msg":"success.","sn":"812511399091527231049","corpus_no":"6559407410141679025","err_no":0}
         */
        String result = baiduSpeechService.asrAudioToText(bytes, "wav");
        System.out.println("百度识别结果="+result);
    }
}
