package com.tianyi.helmet.service.support;

import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.service.support.ShortMessageService;
import com.tianyi.helmet.server.service.support.ShortMessageWebService;
import com.tianyi.helmet.server.vo.ResponseVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liuhanc on 2017/12/8.
 */
public class ShortMessageServiceTest2 extends BaseServiceTest{
    @Autowired
    private ShortMessageWebService shortMessageWebService;
    @Autowired
    private ShortMessageService shortMessageService;

    @Test
    public void test2() {
        String postJson = "{\"UserName\":\"tykj\",\"UserPwd\":\"tykj20171207\",\"SimNo\":\"17091087800\",\"Message\":\"2234,5\"}";
        String respJson = shortMessageWebService.SendMessage(postJson);
        System.out.println(respJson);
    }

    @Test
    public void test3() {
        ResponseVo vo = shortMessageService.sendVerifyCode("xyzb","15313594936");
        System.out.println(vo.isSuccess()+","+vo.getMessage());
    }
}
