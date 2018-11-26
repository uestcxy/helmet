package com.tianyi.helmet.controller;

import com.alibaba.fastjson.JSONObject;
import com.tianyi.helmet.server.entity.svc.SvcAudioTypeEnum;
import com.tianyi.helmet.server.entity.svc.SvcImageTypeEnum;
import com.tianyi.helmet.server.entity.svc.SvcVideoTypeEnum;
import com.tianyi.helmet.server.service.svc.SvcMetaDataHelper;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuhanc on 2018/3/19.
 */
public class TyDataControllerTest extends HelmetInterfaceControllerTest {

    @Before
    public void setup() {
        imei = "864231039086760";
    }

    @Test
    public void testGetTons(){
        JSONObject json = new JSONObject();
        json.put("brand","小松");
        executePostJson("dev","/ty/data/GetTons",json);
    }

}