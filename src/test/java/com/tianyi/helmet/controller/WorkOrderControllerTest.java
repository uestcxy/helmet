package com.tianyi.helmet.controller;

import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuhanc on 2018/7/3.
 */
public class WorkOrderControllerTest extends HelmetInterfaceControllerTest {

    @Test
    public void testWelcome() {
        imei = "imei_liuhan_test";
        String resp = executePost("local", "workorder/welcome", Collections.emptyMap());
        System.out.println("反馈="+resp);
    }

    @Test
    public void testGetOrder(){
        imei = "imei_liuhan_test";
        Map<String,String> map = new HashMap<>();
        map.put("orderNo","20180703004");
        String resp = executePost("local","workorder/getWorkOrder",map);
        System.out.println("getWorkOrder反馈="+resp);
    }

    @Test
    public void testOrderStart(){
        imei = "imei_liuhan_test";
        Map<String,String> map = new HashMap<>();
        map.put("orderNo","20180703003");
        String resp = executePost("local","workorder/start",map);
        System.out.println("start反馈="+resp);
    }

    @Test
    public void testOrderEnd(){
        imei = "imei_liuhan_test";
        Map<String,String> map = new HashMap<>();
        map.put("orderNo","20180703003");
        String resp = executePost("local","workorder/end",map);
        System.out.println("end="+resp);
    }

    @Test
    public void testOrderAddCollaborate(){
        imei = "imei_liuhan_test";
        Map<String,String> map = new HashMap<>();
        map.put("orderNo","20180703003");
        String resp = executePost("local","/workorder/addCollaborate",map);
        System.out.println("addCollaborate="+resp);
    }


}
