package com.tianyi.helmet.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Created by liuhanc on 2018/3/19.
 */
public class OperaLogControllerTest extends HelmetInterfaceControllerTest {

	@Test
    public static void testList() throws Exception {
        String uri = "operalog/list";
        Map<String, String> params = new HashMap<>(5);
        params.put("page", "1");
        params.put("clientId", "yujiawei1");
        executePost("local",uri,params);
    }


}
