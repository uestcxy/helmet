package com.tianyi.helmet.service.helmet;

import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.entity.data.HelmetGps;
import com.tianyi.helmet.server.service.data.HelmetGpsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * Created by liuhanc on 2017/11/3.
 */
public class HelmetGpsServiceTest extends BaseServiceTest{

    @Autowired
    HelmetGpsService helmetGpsService;

    @Test
    public void test(){
        HelmetGps gps = new HelmetGps();
        gps.setClientId("dev");
        gps.setCreateTime(LocalDateTime.now());
        gps.setLat(new Float(1234));
        gps.setLon(new Float(12));
        helmetGpsService.insert(gps);
        System.out.println(gps.getId());
        helmetGpsService.selectByClientId("helmet1001",System.currentTimeMillis()-100*1000,System.currentTimeMillis());
    }
}
