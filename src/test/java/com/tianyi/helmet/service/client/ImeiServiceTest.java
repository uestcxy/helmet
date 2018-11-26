package com.tianyi.helmet.service.client;

import com.alibaba.fastjson.JSON;
import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.service.client.TyBoxImeiService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * Created by liuhanc on 2017/12/8.
 */
public class ImeiServiceTest extends BaseServiceTest {
    @Autowired
    private TyBoxImeiService imeiService;

    @Test
    public void testInsert(){
        Set<String> oldSet =  imeiService.listBy();
        System.out.println("新增前imei："+ JSON.toJSONString(oldSet));
        for(int i=0;i<2;i++){
            for(int j=0;j<10;j++){
                imeiService.addImei("test_imei_"+i);
            }
        }
        Set<String> newSet =  imeiService.listBy();
        System.out.println("新增后imei："+ JSON.toJSONString(newSet));
    }
}
