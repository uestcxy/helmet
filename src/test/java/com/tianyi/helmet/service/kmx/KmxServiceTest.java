//package com.tianyi.helmet.service.kmx;
//
//import com.sagittarius.bean.result.IntPoint;
//import com.tianyi.helmet.BaseServiceTest;
//import com.tianyi.helmet.server.service.kmx.*;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import tsinghua.thss.sdk.bean.common.Device;
//import tsinghua.thss.sdk.bean.common.DeviceType;
//import tsinghua.thss.sdk.bean.common.Sensor;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by liuhanc on 2018/1/4.
// */
//public class KmxServiceTest extends BaseServiceTest {
//    @Autowired
//    private KmxService kmxService;
//    @Autowired
//    private DeviceTypeService deviceTypeService;
//    @Autowired
//    private DeviceService deviceService;
//    @Autowired
//    private MetaDataInitService metaDataInitService;
//
//    @Test
//    public void testInitAllDeviceType(){
//        metaDataInitService.init();
//    }
//
//    @Test
//    public void deleteAllDeviceType() throws Exception {
//        deviceTypeService.deleteAllDeviceTypeAndSensors();
//    }
//
//    @Test
//    public void deleteAllDevice() throws Exception {
//        deviceService.deleteAllDeviceAndSensors();
//    }
//
//    @Test
//    public void testGetAllDeviceType() throws Exception {
//        List<DeviceType> list = deviceTypeService.getAllDeviceType();
//        if(list == null){
//            System.out.println("无数据");
//            return;
//        }
//        System.out.println("设备类型数:" + list.size());
//        for (DeviceType dt : list) {
//            System.out.println(dt.getId() + "," + dt.getDescription());
//            List<Sensor> slist = dt.getSensors();
//            for (Sensor s : slist) {
//                System.out.println("\tsensor:" + s.getId() + "," + s.getDescription());
//            }
//        }
//    }
//
//    @Test
//    public void testGetAllDevice() throws Exception {
//        List<Device> list = deviceService.getAllDevice();
//        if(list == null){
//            System.out.println("无数据");
//            return;
//        }
//
//        System.out.println("设备数:" + list.size());
//        for (Device dt : list) {
//            System.out.println(dt.getDeviceTypeId() + "," + dt.getId() + "," + dt.getDescription());
//            Map<String, String> tags = dt.getTags();
//            if (tags != null) {
//                tags.keySet().stream().forEach(key -> {
//                    System.out.println("\ttag==>" + key + ":" + tags.get(key));
//                });
//            }
//            List<Sensor> slist = kmxService.getReader().getSensorsByDeviceId(dt.getId());
//            for (Sensor s : slist) {
//                System.out.println("\tsensor:" + s.getId() + "," + s.getDescription());
//            }
//        }
//    }
//
//    @Test
//    public void testInsertAndReadData(){
//        long time = System.currentTimeMillis();
//        String deviceId = "helmet1005";
//        String sensorId = "battery";
//
////        kmxService.insert(deviceId,sensorId,time,time+1,78);
////        System.out.println("保存成功.time="+time);
//
//        List<IntPoint> list = kmxService.queryIntRange(SensorSupport.HELMET_DEVICE_IDPREFIX+deviceId,sensorId,time-1000*1000,time+1);
//        if(list == null){
//            System.out.println("查询无数据");
//        }else{
//            System.out.println("查询数据量:"+list.size());
//            for(IntPoint ip:list){
//                System.out.println(ip.getPrimaryTime()+","+ip.getSecondaryTime()+","+ip.getMetric()+","+ip.getValue());
//            }
//        }
//    }
//
//
//}
