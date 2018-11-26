package com.tianyi.helmet.service.scene;

import com.alibaba.fastjson.JSON;
import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.entity.scene.svc.Weather;
import com.tianyi.helmet.server.service.scene.WeatherService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liuhanc on 2018/6/30.
 */
public class WeatherServiceTest extends BaseServiceTest {

    @Autowired
    private WeatherService weatherService;

    @Test
    public void testGetWeather() {
        Weather weather = weatherService.getWeather(39.915446357113886, 116.40384918664363);
        System.out.println(JSON.toJSONString(weather));
    }
}
