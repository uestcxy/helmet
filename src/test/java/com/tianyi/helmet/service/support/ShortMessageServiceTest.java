package com.tianyi.helmet.service.support;

import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.service.support.ShortMessageService;
import com.tianyi.helmet.server.service.support.ShortMessageWebService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import java.net.URL;

/**
 * Created by liuhanc on 2017/12/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:sms-interface.xml"})
public class ShortMessageServiceTest extends BaseServiceTest{
    @Autowired
    private ShortMessageService shortMessageService;

//    @Test
//    public void test2() {
//        String postJson = "{\"UserName\":\"tykj\",\"UserPwd\":\"tykj20171207\",\"SimNo\":\"17091087800\",\"Message\":\"1234,5\"}";
//        String respJson = shortMessageWebService.SendMessage(postJson);
//        System.out.println(respJson);
//    }

    @Test
    public void test() throws Exception {
        String address = "http://sms.tygps.com/QingmayunSMSService/IQmyService";
        URL wsdlUrl = new URL(address + "?wsdl");
        Service s = Service.create(wsdlUrl, new QName("http://tempuri.org/", "QmyService"));
        ShortMessageWebService port = s.getPort(new QName("http://tempuri.org/", "BasicHttpBinding_IQmyService"), ShortMessageWebService.class);
        ((BindingProvider) port).getRequestContext().put(
                BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);

        String postJson = "{\"UserName\":\"tykj\",\"UserPwd\":\"tykj20171207\",\"SimNo\":\"17091087800\",\"Message\":\"1234,5\"}";
        String ret = port.SendMessage(postJson);
        System.out.println("sendMsg return:" + ret);
    }

    @Test
    public void test2(){
        shortMessageService.sendVerifyCode("hehe","17091087800");
    }
}
