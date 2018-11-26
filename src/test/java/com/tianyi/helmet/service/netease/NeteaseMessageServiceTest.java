package com.tianyi.helmet.service.netease;

import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.entity.netease.NeteaseMessage;
import com.tianyi.helmet.server.service.netease.NeteaseMessageService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liuhanc on 2017/11/6.
 */
public class NeteaseMessageServiceTest extends BaseServiceTest {

    @Autowired
    private NeteaseMessageService neteaseMessageService;

    @Test
    public void testInsert(){
        NeteaseMessage msg = new NeteaseMessage();
        msg.setEventType("6");
        neteaseMessageService.insert(msg);
    }
}
