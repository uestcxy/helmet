package com.tianyi.helmet.service.netease;

import com.alibaba.fastjson.JSONObject;
import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.service.netease.NeteaseApiService;
import com.tianyi.helmet.server.util.Dates;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

/**
 * Created by liuhanc on 2017/10/23.
 */
public class NeteaseApiServiceTest extends BaseServiceTest {

    @Autowired
    NeteaseApiService apiService;

    @Test
    public void testQuerySession(){
        String from="";
        String to="";
        String beginTime = ""+Dates.toDate(LocalDate.of(2017,10,10)).getTime();
        String endTime = ""+Dates.toDate(LocalDate.of(2017,10,20)).getTime();

        apiService.querySessionMsg(from,to,beginTime,endTime,100,1,this::processMessage);
    }

    private void processMessage(JSONObject jo){
        System.out.println("网易历史消息接口反馈："+jo.toJSONString());
    }

//    @Test
//    public void testDeleteVideo(){
//        apiService.deleteVideo(29996743);
//    }

    @Test
    public void testRefreshToken(){
        String token = apiService.userRefreshToken("helmet1002");
        System.out.println("token="+token);
    }

    @Test
    public void testUserCreate(){
        apiService.userCreate("","");
    }
}
