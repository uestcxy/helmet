package com.tianyi.helmet.service.support;

import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.service.support.ConfigService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liuhanc on 2017/12/1.
 */
public class ConfigServiceTest extends BaseServiceTest {
    @Autowired
    private ConfigService configService;
    @Test
    public void testValue() {
        System.out.println(configService.getFastdfsServerUrl()+",retryTimes="+configService.getFastdfsRetryTimes());
    }
}
