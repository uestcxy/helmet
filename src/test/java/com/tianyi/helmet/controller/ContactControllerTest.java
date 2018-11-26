package com.tianyi.helmet.controller;

import org.junit.Test;

import java.util.Collections;

/**
 * Created by liuhanc on 2018/7/3.
 */
public class ContactControllerTest extends HelmetInterfaceControllerTest {

    @Test
    public void testGetMyContacts() {
        imei = "imei_liuhan_test";
        String resp = executePost("prod", "contact/mycontact", Collections.emptyMap());
        System.out.println("我的联系人列表=" + resp);
    }
}
