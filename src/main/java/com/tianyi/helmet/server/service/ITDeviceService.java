package com.tianyi.helmet.server.service;
import com.tianyi.helmet.server.entity.TDevice;
import java.util.List;
/**
* 描述：设备 服务实现层接口
* @author xiayuan
* @date 2018/10/30
*/
public interface ITDeviceService{

    List<TDevice> listBy(TDevice tDevice);

    List<TDevice> listByNoPage(TDevice tDevice);

    int add(TDevice tDevice);

    int deleteBy(TDevice tDevice);

    int updateBy(TDevice tDevice);

}