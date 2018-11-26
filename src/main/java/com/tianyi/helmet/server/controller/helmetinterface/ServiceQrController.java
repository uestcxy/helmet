package com.tianyi.helmet.server.controller.helmetinterface;

import com.tianyi.helmet.server.controller.interceptor.HelmetImeiHolder;
import com.tianyi.helmet.server.entity.client.LoginUserInfo;
import com.tianyi.helmet.server.entity.client.User;
import com.tianyi.helmet.server.entity.device.EquipmentLedger;
import com.tianyi.helmet.server.service.client.*;
import com.tianyi.helmet.server.service.device.EquipmentService;
import com.tianyi.helmet.server.vo.AppAccountVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统用户二维码接口,便于头盔绑定天远/田一服务人员
 * <p>
 * <p>
 * Created by liuhanc on 2018/1/30.
 */
@Controller
@RequestMapping("serviceqr")
public class ServiceQrController {
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private LoginUserTokenService loginUserTokenService;
    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(ServiceQrController.class);

    //二维码账号绑定验证
    @RequestMapping(value = "/code/{token}", method = RequestMethod.POST)
    @ResponseBody
    public AppAccountVo qrcodeBindPost(@PathVariable("token") String token,@RequestParam int userId) {
        LoginUserInfo userInfo = loginUserTokenService.getLoginUserInfo(token);
        if (userInfo == null) {
            return new AppAccountVo("600", "请求URL无效");
        }
        /**
         * update by xiayuan 2018/10/19
         */
        String imei = HelmetImeiHolder.get();
        EquipmentLedger helmet = equipmentService.selectByUUID(imei);
        if (helmet == null) {
            return new AppAccountVo("600", "头盔不存在或者尚未初始化.imei=" + imei);
        }
        /**
         * update by xiayuan 2018/10/19
         *
         */
//
//        if(helmet.getUserId()!=0){
//            return new AppAccountVo("600", "头盔已绑定用户，请先注销再绑定新用户");
//        }
        User user = userService.selectById(userId);
        EquipmentLedger equipmentLedger = new EquipmentLedger();
        equipmentLedger.setUserId(userId);
        equipmentLedger.setDeviceUUID(imei);
        int rs = equipmentService.update(equipmentLedger);
        if (rs>0) {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("username", user.getName());
            return new AppAccountVo("200", "头盔绑定田一用户成功", dataMap);
        } else {
            return new AppAccountVo("600", "头盔绑定田一用户失败");
        }
    }

}
