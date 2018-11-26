package com.tianyi.helmet.server.controller.helmetinterface;

import com.tianyi.helmet.server.controller.interceptor.HelmetImeiHolder;
import com.tianyi.helmet.server.controller.interceptor.TianyiUserHolder;
import com.tianyi.helmet.server.entity.client.User;
import com.tianyi.helmet.server.entity.data.HelmetGps;
import com.tianyi.helmet.server.entity.scene.svc.Weather;
import com.tianyi.helmet.server.entity.scene.svc.WorkOrder;
import com.tianyi.helmet.server.entity.scene.svc.WorkOrderStateEnum;
import com.tianyi.helmet.server.entity.scene.svc.WorkOrderWelcome;
import com.tianyi.helmet.server.service.baidu.BaiduMapService;
import com.tianyi.helmet.server.service.device.EquipmentService;
import com.tianyi.helmet.server.service.data.HelmetGpsService;
import com.tianyi.helmet.server.service.scene.WeatherService;
import com.tianyi.helmet.server.service.scene.WorkOrderService;
import com.tianyi.helmet.server.util.Dates;
import com.tianyi.helmet.server.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工单接口
 * <p>
 * Created by liuhanc on 2018/6/30.
 */
@Controller
@RequestMapping("workorder")
public class WorkOrderController {

    @Autowired
    private HelmetGpsService helmetGpsService;
    @Autowired
    private BaiduMapService baiduMapService;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private WorkOrderService workOrderService;

    /**
     * 欢迎页
     * 反馈位置地址、日期、时间、
     * 头盔佩戴者姓名、工单数
     *
     * @return
     */
    @RequestMapping("welcome")
    @ResponseBody
    public ResponseVo<WorkOrderWelcome> welcome() {
        User user = TianyiUserHolder.get();
        String imei = HelmetImeiHolder.get();

        WorkOrderWelcome welcome = new WorkOrderWelcome();
        welcome.setServerTime(Dates.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        welcome.setUserId(user.getId());
        welcome.setUserRealName(user.getName());

        List<WorkOrder> workOrderList = workOrderService.selectNotEndWorkOrderListByUserId(user.getId(), true);
        welcome.setWorkOrderList(workOrderList);

        //地址定位
        /**
         * update by xiayuan 2018/10/9
         */
//        List<EquipmentLedger> list = equipmentService.selectByUserId(user.getId());
//        EquipmentLedger helmet = new EquipmentLedger();
//        for (EquipmentLedger e : list) {
//            String state = (String) redisTemplate.opsForValue().get(e.getDeviceUUID());
//            if (MyConstants.DEVICE_STATE_ON.equals(state)) {
//                helmet = e;
//            }
//        }
        HelmetGps gps = helmetGpsService.getLatest(imei);

        if (gps != null) {
            //实时地址
            String address = baiduMapService.latLngToAddress(gps.getLat(), gps.getLon());
            welcome.setAddress(address);

            //实时天气
            Weather weather = weatherService.getWeather(gps.getLat(), gps.getLon());
            welcome.setWeather(weather);
        } else {
            welcome.setAddress("石家庄市裕华区黄河大道225号");
            Weather weather = weatherService.getWeather(114.5, 38);//石家庄默认gps
            welcome.setWeather(weather);
        }

        return ResponseVo.success(welcome);
    }

    /**
     * @return
     */
    @RequestMapping("getWorkOrder")
    @ResponseBody
    public ResponseVo<WorkOrder> getWorkOrder(@RequestParam String orderNo) {
        User user = TianyiUserHolder.get();
        WorkOrder workOrder = workOrderService.selectByOrderNo(orderNo, false);
        if (workOrder.getUserId() != user.getId()) {
            return ResponseVo.fail("工单未指派给你，无法获取");
        }

        //填充数据
        workOrderService.fillWorkOrderTypeStateName(workOrder);
        workOrderService.fillWorkOrderStrategyList(workOrder);
        workOrderService.fillVideoCounts(workOrder);

        return ResponseVo.success(workOrder);
    }


    /**
     * @return
     */
    @RequestMapping("start")
    @ResponseBody
    public ResponseVo startWorkOrder(@RequestParam String orderNo) {
        User user = TianyiUserHolder.get();
        WorkOrder workOrder = workOrderService.selectByOrderNo(orderNo, false);
        if (workOrder.getUserId() != user.getId()) {
            return ResponseVo.fail("工单未指派给你，无法开始");
        }

        String oldState = workOrder.getOrderState();
        if (!WorkOrderStateEnum.INIT.toString().equals(oldState)) {
            return ResponseVo.fail("工单当前状态是" + WorkOrderStateEnum.valueOf(oldState).getCnName() + ",无法开始");
        }

        workOrderService.updateOrderStateToIngByOrderNo(orderNo);

        Map<String, String> retData = new HashMap<>();
        retData.put("orderState", WorkOrderStateEnum.ING.toString());
        retData.put("orderStateName", WorkOrderStateEnum.ING.getCnName());
        return ResponseVo.success(retData);
    }

    /**
     * @return
     */
    @RequestMapping("end")
    @ResponseBody
    public ResponseVo endWorkOrder(@RequestParam String orderNo) {
        User user = TianyiUserHolder.get();
        WorkOrder workOrder = workOrderService.selectByOrderNo(orderNo, false);
        if (workOrder.getUserId() != user.getId()) {
            return ResponseVo.fail("工单未指派给你，无法结束");
        }
        String oldState = workOrder.getOrderState();
        if (!WorkOrderStateEnum.ING.toString().equals(oldState)) {
            return ResponseVo.fail("工单当前状态是" + WorkOrderStateEnum.valueOf(oldState).getCnName() + ",无法结束");
        }

        workOrderService.updateOrderStateToEndByOrderNo(orderNo);

        Map<String, String> retData = new HashMap<>();
        retData.put("orderState", WorkOrderStateEnum.END.toString());
        retData.put("orderStateName", WorkOrderStateEnum.END.getCnName());
        return ResponseVo.success(retData);
    }

    /**
     * @return
     */
    @RequestMapping("addCollaborate")
    @ResponseBody
    public ResponseVo addCollaborate(@RequestParam String orderNo) {
        User user = TianyiUserHolder.get();
        WorkOrder workOrder = workOrderService.selectByOrderNo(orderNo, false);
        if (workOrder.getUserId() != user.getId()) {
            return ResponseVo.fail("工单未指派给你，无法修改");
        }
        String oldState = workOrder.getOrderState();
        if (!WorkOrderStateEnum.ING.toString().equals(oldState)) {
            return ResponseVo.fail("工单当前状态是" + WorkOrderStateEnum.valueOf(oldState).getCnName() + ",无法添加协助次数");
        }

        workOrderService.increaseCollaborateCntByOrderNo(orderNo);
        workOrder = workOrderService.selectByOrderNo(orderNo, false);
        return ResponseVo.success(workOrder.getCollaborateCnt());
    }

}
