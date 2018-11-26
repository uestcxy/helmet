package com.tianyi.helmet.service.client;

import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.entity.client.TianYuanUser;
import com.tianyi.helmet.server.entity.device.EquipmentLedger;
import com.tianyi.helmet.server.service.client.HelmetComponent;
import com.tianyi.helmet.server.service.client.TianYuanUserService;
import com.tianyi.helmet.server.service.device.EquipmentService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liuhanc on 2017/12/8.
 */
public class HelmetServiceTest extends BaseServiceTest {

    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private HelmetComponent helmetComponent;
    @Autowired
    private TianYuanUserService tianYuanUserService;


    @Test
    public void testUpdateHelmet() {
        EquipmentLedger helmet = equipmentService.selectByUUID("imei_liuhan_test");
        TianYuanUser tianYuanUser = tianYuanUserService.selectByUsername("gaoxuzhao");
//        helmetComponent.updateHelmetTianYuanUser(helmet, tianYuanUser);
    }

}
