package com.tianyi.helmet.service.baidu;

import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.service.baidu.BaiduMapService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liuhanc on 2018/6/30.
 */
public class BaiduMapServiceTest extends BaseServiceTest {

    @Autowired
    private BaiduMapService baiduMapService;

    @Test
    public void testaddressToLatLng(){
        String ret = baiduMapService.addressToLatLng("天安门");
        System.out.println(ret);
    }

    @Test
    public void testLatLngToAddress(){
        String ret = baiduMapService.latLngToAddress(39.915446357113886,116.40384918664363);
        System.out.println(ret);
    }

}
