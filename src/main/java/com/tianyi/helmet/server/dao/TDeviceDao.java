package com.tianyi.helmet.server.dao;
import com.tianyi.helmet.server.entity.TDevice;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 描述：设备 Repository接口
* @author xiayuan
* @date 2018/10/30
*/
@Repository
public interface TDeviceDao{

    List<TDevice> listBy(TDevice tDevice);

    List<TDevice> listByNoPage(TDevice tDevice);

    int add(TDevice tDevice);

    int deleteBy(TDevice tDevice);

    int updateBy(TDevice tDevice);


}