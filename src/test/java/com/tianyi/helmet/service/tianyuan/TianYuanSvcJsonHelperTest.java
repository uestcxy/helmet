package com.tianyi.helmet.service.tianyuan;

import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.service.tianyuan.TianYuanSvcJsonHelper;
import com.tianyi.svc.rest.entity.SvcTask;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liuhanc on 2018/3/29.
 */
public class TianYuanSvcJsonHelperTest extends BaseServiceTest {

    @Autowired
    private TianYuanSvcJsonHelper tianYuanSvcJsonHelper;

    @Test
    public void testToJson(){
        SvcTask task = new SvcTask();
        task.setId(123);
        task.setRwh("RWH123456");
        task.setJh("JH111");
        task.setBrand("小松");
        task.setRwzt("2");//0待填写，1已填写未完成,2填写完成未上传,3已上传待审核,4已上传被打回,5已上传审核通过
        String json = tianYuanSvcJsonHelper.toJson(task);
        System.out.println("json="+json);

        SvcTask task2 = new SvcTask();
        tianYuanSvcJsonHelper.parse(json,task2);
        System.out.println("task2="+task2.getId()+","+task2.getBrand());
    }
}
