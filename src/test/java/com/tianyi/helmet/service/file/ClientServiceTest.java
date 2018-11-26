//package com.tianyi.helmet.service.file;
//
//import com.tianyi.helmet.BaseServiceTest;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * 设备服务测试
// *
// * Created by liuhanc on 2017/10/13.
// */
//public class ClientServiceTest extends BaseServiceTest {
//
//    @Autowired
//    HelmetService helmetService;
//    @Test
//    public void testGetClientByClientId(){
//        Helmet helmet = helmetService.getHelmetByImei("helmet0007");
//        System.out.println("1:"+helmet);
//        System.out.println(helmet.getCustomerId()+","+helmet.getFactoryTime());
//        Helmet client2 = helmetService.getHelmetByImei("helmet0007");
//        System.out.println("2:"+client2);
//    }
//
//    @Test
//    public void testCache(){
//        helmetService.cacheWillTime("helmet0010");
//        boolean online = helmetService.isOnLine("helmet0010");
//        System.out.println("online:"+online+",2="+helmetService.isOnLine("helmet0099"));
//    }
//}
