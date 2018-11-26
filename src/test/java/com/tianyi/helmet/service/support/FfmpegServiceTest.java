package com.tianyi.helmet.service.support;

import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.service.baidu.BaiduSpeechService;
import com.tianyi.helmet.server.service.support.FfmpegService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * Created by liuhanc on 2017/10/16.
 */
public class FfmpegServiceTest extends BaseServiceTest {
    @Autowired
    FfmpegService ffmpegService;
    @Autowired
    private BaiduSpeechService baiduSpeechService;

    @Test
    public void testVideoTrans() {
        //D:/work/tool/ffmpeg-win64-static/bin/ffmpeg -y -i "C:\Users\liuhanc\Downloads\test.mp4" -vcodec libx264 -b:v 500000 -r 25 -acodec libmp3lame -b:a 64000 -ar 22050 -vf scale=w=360:h=640:force_original_aspect_ratio=decrease,pad=w=360:h=640[aa] "C:\Users\liuhanc\Downloads\test.mp4.small.mp4"
        java.io.File videoFile = new File("C:\\Users\\liuhanc\\Videos\\52852000-ff5e-4f49-b153-36c127fce8b5.mp4");
        File destFile = new File(videoFile.getAbsolutePath() + ".mp4");
        ffmpegService.videoTransCode(videoFile, destFile, null);
    }

    @Test
    public void testPreviewImage() {
        //D:/work/tool/ffmpeg-win64-static/bin/ffmpeg -y -i "C:\Users\liuhanc\Downloads\test.mp4" -ss 5 -s 400x300 -f image2 -vframes 1 "C:\Users\liuhanc\Downloads\test.mp4.jpg"
        java.io.File videoFile = new File("C:\\Users\\liuhanc\\Downloads\\test.mp4");
        java.io.File imageFile = new File("C:\\Users\\liuhanc\\Downloads\\test.mp4.jpg");
        ffmpegService.previewImage(videoFile, imageFile, 3);
    }

    @Test
    public void testGetVideoTime() {
        File f = new File("C:\\Users\\liuhanc\\Videos\\1分钟视频3.59M.mp4");
        int time = ffmpegService.getVideoTime(f);
        System.out.println(time);
    }

    @Test
    public void testExtractAudio() {
        ffmpegService.extractMp3FromVideo("D:\\服务日志资源测试\\科大讯飞识别效果特别好.mp4","D:\\服务日志资源测试\\科大讯飞识别效果特别好.mp3");

        //pcm_u8，pcm_s16le ，pcm_s16be，pcm_u16le，pcm_u16be
    }

    @Test
    public void testMp3ToWav(){
        ffmpegService.audioTranscode("C:\\Users\\liuhanc\\Downloads\\fe51e459-6b6b-435d-98fe-b83fdb34c424.0.mp3","C:\\Users\\liuhanc\\Downloads\\fe51e459-6b6b-435d-98fe-b83fdb34c424.0.wav",1, "pcm_s16le", "wav");
    }

    @Test
    public void testSpeechArsByBaidu(){
        String ret = baiduSpeechService.asrAudioToText(new File("C:\\Users\\liuhanc\\Downloads\\fe51e459-6b6b-435d-98fe-b83fdb34c424.0.wav"),"wav");
        System.out.println("百度识别结果="+ret);
    }

    @Test
    public void testSplitAudioAndAsrByBaidu() {
//        ffmpegService.audioTranscode("C:\\Users\\liuhanc\\Documents\\WeChat Files\\hb-baiwuliaolai\\Files\\nearend.wav","C:\\Users\\liuhanc\\Documents\\WeChat Files\\hb-baiwuliaolai\\Files\\nearend2.wav",1, "pcm_s16le", "s16le");
        String[] files = ffmpegService.audioSplitToMp3("D:\\FFOutput\\双mic降噪效果.mp3", "D:\\FFOutput", 60, 41);
        for (String file : files) {
            System.out.println("split file=" + file);
            ffmpegService.audioTranscode(file,file+".pcm",1, "pcm_s16le", "s16le");
            String ret = baiduSpeechService.asrAudioToText(new File(file+".pcm"),"pcm");
            System.out.println("百度识别结果="+ret);
        }
    }

    public static void main(String[] a) {
        File f = new File("C:\\Users\\liuhanc\\Videos\\1分钟视频3.59M.mp4");
        FfmpegService ffmpegService = new FfmpegService();
        int time = ffmpegService.getVideoTime(f);
    }
}
