package com.tianyi.helmet.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuhanc on 2018/3/19.
 */
public class AlipayControllerTest extends HelmetInterfaceControllerTest {


    public static void testPay() throws Exception {
        String uri = "alipay/pay";
        Map<String, String> params = new HashMap<>(5);
        params.put("authCode", "12346019236410234");
        params.put("amount", "123.45");
        params.put("payType", "partSellOrder");
        params.put("subject", "测试收款");
        params.put("description", "本次收款的详细内容描述");

        executePost("prod","alipay/pay",params);
    }

    public static void main(String[] a) throws Exception {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(new Date()));//2018-03-28T14:24:07.327
        testPay();
    }

}
