package com.tianyi.helmet.service.client;

import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.service.client.HelmetBindLogService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * Created by liuhanc on 2017/12/8.
 */
public class HelmetBindLogServiceTest extends BaseServiceTest {

    @Autowired
    private HelmetBindLogService helmetBindLogService;
    @Test
    public void testGetBindUser(){
        helmetBindLogService.getBindUserPhoneByHelmetIdAndTime(60, LocalDateTime.now());
    }
}
