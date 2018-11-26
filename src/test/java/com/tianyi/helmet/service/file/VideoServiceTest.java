package com.tianyi.helmet.service.file;

import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.entity.file.Video;
import com.tianyi.helmet.server.entity.file.VideoStateEnum;
import com.tianyi.helmet.server.service.file.VideoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class VideoServiceTest extends BaseServiceTest {

    @Autowired
    private VideoService videoService;

    @Test
    public void updateStage(){
        videoService.updateState(7, VideoStateEnum.PROCING);
    }


    @Test
    public void addVideo(){
        Video v = new Video();
        v.setFileName("dev");
        v.setClientId("helmet0020");
        v.setUploadTime(LocalDateTime.now());
        System.out.println("before="+v.getId());
        videoService.insert(v);
        System.out.println("after="+v.getId());
    }

    @Test
    public void increateViewCount(){
        videoService.increaseViewCount(18);
    }
}
