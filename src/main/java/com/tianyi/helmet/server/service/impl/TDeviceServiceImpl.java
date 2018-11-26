package com.tianyi.helmet.server.service.impl;
import com.tianyi.helmet.server.entity.TDevice;
import com.tianyi.helmet.server.service.ITDeviceService;
import com.tianyi.helmet.server.dao.TDeviceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 描述：设备 服务实现层
* @author xiayuan
* @date 2018/10/30
*/
@Service
public class TDeviceServiceImpl implements ITDeviceService {

    @Autowired
    private TDeviceDao tDeviceDao;

    @Override
    public List<TDevice> listBy(TDevice tDevice){
        List<TDevice> tDeviceList = tDeviceDao.listBy(tDevice);
        return tDeviceList;
    }

    @Override
    public List<TDevice> listByNoPage(TDevice tDevice){
        List<TDevice> tDeviceList = tDeviceDao.listByNoPage(tDevice);
        return tDeviceList;
    }

    @Override
    public int add(TDevice tDevice){
        int rs = tDeviceDao.add(tDevice);
        return rs;
    }
    @Override
    public int deleteBy(TDevice tDevice){
        int rs = tDeviceDao.deleteBy(tDevice);
        return rs;
    }

    @Override
    public int updateBy(TDevice tDevice){
        int rs = tDeviceDao.updateBy(tDevice);
        return rs;
    }
}
