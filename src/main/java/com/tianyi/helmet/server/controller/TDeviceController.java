package com.tianyi.helmet.server.controller;
import com.tianyi.helmet.server.service.ITDeviceService;
import com.tianyi.helmet.server.entity.TDevice;
import com.tianyi.helmet.server.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
* 描述：设备控制层
* @author xiayuan
* @date 2018/10/30
*/
@Controller
public class TDeviceController {

    @Autowired
    private ITDeviceService tDeviceService;

    /**
    * 描述：查询列表
    * @param tDevice
    */
    @RequestMapping(value = "get", method = RequestMethod.POST)
    public ResponseVo listBy(@RequestBody TDevice tDevice) {
        List<TDevice> tDeviceList = tDeviceService.listBy(tDevice);
        if(tDeviceList != null){
            return ResponseVo.success(tDeviceList);
        }else{
            return ResponseVo.fail("查询失败");
        }
    }

    /**
    * 描述:新增TDevice
    * @param tDevice
    */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseVo add(@RequestBody TDevice tDevice){
        int rs = tDeviceService.add(tDevice);
        if(rs > 0){
            return ResponseVo.success();
        }
        return ResponseVo.fail("添加失败");
    }

    /**
    * 描述：删除设备
    * @param tDevice
    */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseVo deleteBy(@RequestBody TDevice tDevice){
        int rs = tDeviceService.deleteBy(tDevice);
        if(rs > 0){
            return ResponseVo.success();
        }
        return ResponseVo.fail("删除失败");
    }

        /**
        * 描述：更新
        * @param tDevice
        */
        @RequestMapping(value = "update", method = RequestMethod.POST)
        public ResponseVo updateBy(@RequestBody TDevice tDevice){
            int rs = tDeviceService.updateBy(tDevice);
            if(rs > 0){
                return ResponseVo.success();
            }
            return ResponseVo.fail("修改失败");
        }
}