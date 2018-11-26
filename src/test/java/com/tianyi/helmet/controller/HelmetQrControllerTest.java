package com.tianyi.helmet.controller;

import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuhanc on 2018/6/29.
 */
public class HelmetQrControllerTest  extends HelmetInterfaceControllerTest {

    @Test
    public void testQr(){
        imei = "16d80fb1-5592-451d-a8e3-164560e0cd74";
        String uri = "helmetqr/code/127";
        executePost("prod", uri, Collections.emptyMap());
    }
}
