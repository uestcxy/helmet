package com.tianyi.helmet.server.controller.device;

import com.tianyi.helmet.server.controller.interceptor.HelmetImeiHolder;
import com.tianyi.helmet.server.entity.client.NeteaseUser;
import com.tianyi.helmet.server.entity.client.User;
import com.tianyi.helmet.server.entity.device.EquipmentLedger;
import com.tianyi.helmet.server.entity.power.Menu;
import com.tianyi.helmet.server.service.client.NeteaseUserService;
import com.tianyi.helmet.server.service.client.UserService;
import com.tianyi.helmet.server.service.device.EquipmentService;
import com.tianyi.helmet.server.service.dictionary.VersionService;
import com.tianyi.helmet.server.util.MyConstants;
import com.tianyi.helmet.server.vo.AppAccountVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 设备管理
 * Created by xiayuan on 2018/10/19.
 */
@Controller
@RequestMapping("device")
@Api(value = "api", description = "设备管理")
public class EquipmentHelController {

    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private UserService userService;
    @Autowired
    private NeteaseUserService neteaseUserService;
    @Autowired
    private RedisTemplate redisTemplate;

    private Logger logger = LoggerFactory.getLogger(EquipmentHelController.class);
    /**
     * 注销
     *
     * @return
     */
    @RequestMapping(value = "ledger/logout", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "注销")
    public AppAccountVo logout() {
        String deviceUUID = HelmetImeiHolder.get();
        if (StringUtils.isEmpty(deviceUUID)) {
            return new AppAccountVo("600", "参数传递错误");
        }

        EquipmentLedger device = equipmentService.selectByUUID(deviceUUID);
        device.setUserId(-1);
        int rs = equipmentService.update(device);
        if (device == null) {
            return new AppAccountVo("600", "该设备不存在" + deviceUUID);
        }
        if (rs == MyConstants.DEVICE_FAIL) {
            return new AppAccountVo("600", "数据库操作失败");
        }
        return new AppAccountVo("200", "注销成功" + deviceUUID);

    }


    /**
     * 设备开机请求网易账户信息
     *
     * @return
     */
    @RequestMapping(value = "helmet/get/neUser", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "设备开机请求网易账户信息")
    public AppAccountVo getNeUser() {
        String deviceUUID = HelmetImeiHolder.get();
        EquipmentLedger device = equipmentService.selectByUUID(deviceUUID);
        if (device == null) {
            return new AppAccountVo("600", "该设备不存在.");
        }
        Map<String, Object> resultMap = new HashMap<>();
        Integer userId = device.getUserId();
        if (userId == -1 || userId == 0) {
            resultMap.put("userId", -1);
            return new AppAccountVo("200", "头盔账号获取成功", resultMap);
        }
        User user = userService.selectById(userId);
        List<Menu> menus = userService.getHelmetMenuByUserId(userId);

        List<String> menusName = new ArrayList<>();
        for (Menu m : menus) {
            menusName.add(m.getMenuName());
        }
        resultMap.put("neUserName", user.getNeUserHel());
        NeteaseUser neUserInfo = neteaseUserService.selectByUsername(user.getNeUserHel());
        resultMap.put("yun_token", neUserInfo.getYunToken());
        resultMap.put("userName", user.getName());
        resultMap.put("deviceId", device.getId());
        resultMap.put("userId", device.getUserId());
        resultMap.put("menusName", menusName);
        if (redisTemplate.opsForValue().get(user.getNeUserHel()) != null) {
            redisTemplate.delete(user.getNeUserHel());
        }
        redisTemplate.opsForValue().set(user.getNeUserHel(), deviceUUID);
        return new AppAccountVo("200", "头盔账号获取成功", resultMap);
    }

}
