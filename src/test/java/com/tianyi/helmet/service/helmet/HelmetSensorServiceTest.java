package com.tianyi.helmet.service.helmet;

import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.service.data.HelmetSensorService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liuhanc on 2017/11/3.
 */
public class HelmetSensorServiceTest extends BaseServiceTest{

    @Autowired
    HelmetSensorService helmetSensorService;

    @Test
    public void test(){
        helmetSensorService.selectByClientId("helmet1001",System.currentTimeMillis()-100*1000,System.currentTimeMillis());
    }
}
