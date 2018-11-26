//package com.tianyi.helmet.service.kmx;
//
//import com.alibaba.fastjson.JSON;
//import com.tianyi.helmet.BaseServiceTest;
//import com.tianyi.helmet.server.service.kmx.PointWithVclid;
//import com.tianyi.helmet.server.service.kmx.TsingHuaKmxService;
//import com.tianyi.helmet.server.util.Dates;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import tsinghua.thss.sdk.bean.common.Device;
//import tsinghua.thss.sdk.bean.common.DeviceType;
//import tsinghua.thss.sdk.bean.common.Sensor;
//
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by liuhanc on 2018/7/26.
// */
//public class TsingHuaKmxServiceTest extends BaseServiceTest {
//
//    @Autowired
//    private TsingHuaKmxService tsingHuaKmxService;
//
//    @Test
//    public void testGetAllDeviceType(){
//        List<DeviceType> list = tsingHuaKmxService.getAllDeviceType();
//        list.stream().forEach(deviceType -> {
//            System.out.println(deviceType.getId()+":"+deviceType.getDescription());
//        });
//        /** 实测结果：
//         * A:测试一个设备注册多个系列
//         B:测试一个设备注册多个系列
//         DT_DS_IMEI:30段测试IMEI系列
//         DS_DT:
//         KMX_Tools_Test:测试KMXTools的设备系列
//         KMX_Tools_Test2:测试KMXTools的设备系列2
//         DS_Test:测试设备系列
//         DS20180412114447771:
//         CTY_TEST:30段测试CTY物流车系列
//         KMX_SERVICE_TEST_DEVICETYPE001:测试设备类型01
//         chetianceshi:车天二层统计测试系列
//         CTY_TEST2:30段测试CTY物流车系列
//         CTY_TEST1:30段测试CTY物流车系列
//         */
////        System.out.println("设备类型列表="+JSON.toJSONString(list));
//    }
//
//    @Test
//    public void testGetAllDevice(){
//        List<Device> list = tsingHuaKmxService.getAllDevice();
//        list.stream().forEach(device -> {
//            //CTY_TEST1:1001808918:null
//            //DT_DS_IMEI:8000865473039626695:null
//            //DS_DT:10301426925:丰田测试
//            System.out.println(device.getDeviceTypeId()+":"+device.getId()+":"+device.getDescription());
//        });
////        System.out.println("设备列表="+JSON.toJSONString(list));
//    }
//
//
//    @Test
//    public void testGetSensorList() throws Exception {
//        List<Sensor> sensorList = tsingHuaKmxService.getSensorsByDeviceId("10021427740");
//        System.out.println("传感器数量="+sensorList.size());
//        sensorList.stream().forEach(sensor->{
//            System.out.println(JSON.toJSONString(sensor));
//        });
//    }
//
//    @Test
//    public void testQueryRange() throws Exception {
//        Date date1 = Dates.toDate(LocalDateTime.of(2018, 07, 27, 10, 0, 8));
//        Date date2 = Dates.toDate(LocalDateTime.of(2018, 07, 27, 12, 05, 59));
//        System.out.println("查询时间范围："+date1+"-->"+date2);
//        Map<String, List<PointWithVclid>> map = tsingHuaKmxService.getRange("10021427740", date1.getTime(), date2.getTime());
//        System.out.println("结果传感器数据数量="+map.size());
//        map.keySet().stream().forEach(sensorId->{
//            System.out.println("数据量：" + sensorId + ":" + map.get(sensorId).size());
//        });
//    }
//
//}
