package com.tianyi.helmet.server.service.data;

import com.tianyi.helmet.server.dao.data.HelmetMindWaverDao;
import com.tianyi.helmet.server.entity.data.HelmetMindWaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wenxinyan on 2018/9/20.
 */
@Service
public class HelmetMindWaverService {
    @Autowired
    private HelmetMindWaverDao helmetMindWaverDao;

    public void insert(HelmetMindWaver helmetMindWaver) {
        helmetMindWaverDao.insert(helmetMindWaver);
    }
}
