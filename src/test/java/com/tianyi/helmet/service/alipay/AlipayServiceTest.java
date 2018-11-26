package com.tianyi.helmet.service.alipay;

import com.alibaba.fastjson.JSON;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.demo.trade.model.result.AlipayF2FPayResult;
import com.alipay.demo.trade.model.result.AlipayF2FQueryResult;
import com.alipay.demo.trade.model.result.AlipayF2FRefundResult;
import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.service.alipay.AlipayService;
import com.tianyi.helmet.server.service.support.ConfigService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;

/**
 * Created by liuhanc on 2018/2/26.
 */
public class AlipayServiceTest extends BaseServiceTest {

    @Autowired
    private AlipayService alipayService;
    @Autowired
    private ConfigService configService;

    private String outTradeNo = null;

    @Test
    public void testReadProperties() {
        String env = configService.getSystemEnv();
        InputStream is = AlipayService.class.getClassLoader().getResourceAsStream(env + "/alipay.properties");
        System.out.println("alipay configfile inputstream = " + is);
    }

    @Test
    public void testPay() {
        String authCode = "280671315454367184";
        AlipayF2FPayResult result = alipayService.tradePay(authCode, "123.45", "测试条码收款1", "该测试由程序发起", "ty_trade_no_123", "liuhan-operator", "android-idea-dev-1");
        System.out.println(result);
    }

    @Test
    public void testQuery() {
        outTradeNo = "RCV_android-idea-dev-1_20180226092710472";
        AlipayF2FQueryResult result = alipayService.queryTradeResult(outTradeNo);
        System.out.println("查询结果." + JSON.toJSON(result));
    }

    @Test
    public void testRefund() {
        outTradeNo = "RCV_android-idea-dev-1_20180226092710472";
        AlipayF2FRefundResult result = alipayService.tradeRefund(outTradeNo, outTradeNo, "23.45", "测试退款", "liuhan-operator", "android-idea-dev-1");
        System.out.println("退款结果." + JSON.toJSON(result));
    }

    @Test
    public void testCalcel() {
        outTradeNo = "RCV_android-idea-dev-1_20180226092710472";
        AlipayTradeCancelResponse resp = alipayService.tradeCancel(outTradeNo);
        System.out.println("取消结果." + JSON.toJSON(resp));
    }
}
