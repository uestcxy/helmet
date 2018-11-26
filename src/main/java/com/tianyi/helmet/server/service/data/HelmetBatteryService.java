package com.tianyi.helmet.server.service.data;

import com.tianyi.helmet.server.dao.data.HelmetBatteryDao;
import com.tianyi.helmet.server.entity.data.HelmetBattery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wenxinyan on 2018/9/20.
 */
@Service
public class HelmetBatteryService {
    @Autowired
    private HelmetBatteryDao helmetBatteryDao;

    public void insert(HelmetBattery helmetBattery) {
        helmetBatteryDao.insert(helmetBattery);
    }
}
