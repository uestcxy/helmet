package com.tianyi.helmet.service.tianyuan;

import com.alibaba.fastjson.JSON;
import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.entity.svc.po.SvcRestPage;
import com.tianyi.helmet.server.service.svc.SvcIdRestService;
import com.tianyi.helmet.server.service.svc.SvcTaskService;
import com.tianyi.helmet.server.vo.PageListVo;
import com.tianyi.svc.rest.entity.SvcAttendance;
import com.tianyi.svc.rest.entity.SvcTask;
import com.tianyi.svc.sdk.basic.PageList;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhanc on 2018/3/8.
 */
public class SvcRestServiceTest extends BaseServiceTest {

    @Autowired
    private SvcTaskService svcTaskService;
    @Autowired
    private SvcIdRestService svcRestService;

    @Test
    public void testQueryCount() {
        String operatorId = "1000002269";
        LocalDate today = LocalDate.now();
        Map<String, Integer> map = svcTaskService.countTask(operatorId, today);
        System.out.println(JSON.toJSONString(map));
    }

    @Test
    public void testQueryAttendence() {
        SvcRestPage page = new SvcRestPage(1, 2, "operID");
        PageList<SvcAttendance> list = svcRestService.listPage(SvcAttendance.class, page);
        System.out.println("数据量=" + list.getTotalElements() + "," + list.getSize());
        List<SvcAttendance> attendanceList = list.getList();
        attendanceList.stream().forEach(attendance -> {
            System.out.println("数据=" + attendance.getOperID() + "," + attendance.getOperName() + "," + attendance.getTime());
        });
    }

    @Test
    public void testQueryTask() {
//        svcRestService.queryTodoTask(1000002053);
        Date time1 = new Date(2013, 3, 14);
        Date time2 = new Date(2018, 3, 14);
        SvcRestPage page = new SvcRestPage(0, 12, null);

        PageListVo<SvcTask> vo = svcTaskService.listTask("1000002053", "1", time1, time2, page);
        List<SvcTask> taskList1 = vo.getList();
        System.out.println("查询结果=" + vo.getTotal() + "," + vo.getPageSize() + ",本页数据量=" + taskList1.size());
        taskList1.stream().forEach(task -> {
            System.out.println(task.getRwh() + "," + task.getJh());
        });
    }

    @Test
    public void testQueryByRwh() {
        SvcTask task = svcTaskService.getSvcTask("RW20131210018", "oprtId");
        System.out.println("任务信息:" + task.getId() + "," + task.getBrand() + "," + task.getRwh() + "," + task.getOprtId() + "," + task.getOprtName() + "," + task.getJh() + ",服务类别:" + task.getFwlb());
    }
}
