package com.tianyi.helmet.server.controller.client;

import com.tianyi.helmet.server.entity.app.ApkFile;
import com.tianyi.helmet.server.entity.app.ApkFileTypeEnum;
import com.tianyi.helmet.server.entity.app.ApkUpdate;
import com.tianyi.helmet.server.entity.client.User;
import com.tianyi.helmet.server.entity.device.EquipmentLedger;
import com.tianyi.helmet.server.service.app.ApkFileService;
import com.tianyi.helmet.server.service.app.ApkUpdateService;
import com.tianyi.helmet.server.service.client.UserService;
import com.tianyi.helmet.server.service.device.EquipmentService;
import com.tianyi.helmet.server.service.support.ConfigService;
import com.tianyi.helmet.server.util.MyConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 头盔获取升级信息
 *
 * <p>
 * Created by liuhanc on 2017/11/2.
 */
@Controller
@RequestMapping("appupdate")
public class AppUpdateController {

    private Logger logger = LoggerFactory.getLogger(AppUpdateController.class);
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private ApkUpdateService apkUpdateService;
    @Autowired
    private ApkFileService apkFileService;
    @Autowired
    private ConfigService configService;

    @RequestMapping("/{apkFileType}/version.xml")
    /**
     * 2018/10/9 @RequestParam(name = "clientId") String neUserName 改成deviceUUID
     * update by xiayuan
      */
    public void version(@PathVariable String apkFileType, @RequestParam(name = "clientId") String deviceUUID, HttpServletResponse resp) throws IOException {
        ApkFileTypeEnum typeEnum = ApkFileTypeEnum.valueOf(apkFileType);
        if (typeEnum == null) {
            resp.getWriter().write("无法识别的类型:" + apkFileType);
            return;
        }
        if (StringUtils.isEmpty(deviceUUID)) {
            resp.getWriter().write("设备ID不能为空:" + deviceUUID);
            return;
        }
        EquipmentLedger equipmentLedger = equipmentService.selectByUUID(deviceUUID);
        if (equipmentLedger == null) {
            resp.getWriter().write("设备不存在:" + deviceUUID);
            return;
        }

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/xml; charset=utf-8");
        ApkUpdate apkUpdate = apkUpdateService.getLatestByClientIdFileType(deviceUUID, typeEnum.getCode());
        StringBuffer sb = new StringBuffer();
        sb.append("<update>\n");
        sb.append(createVersionXmlContent(apkUpdate));
        sb.append("</update>");
        resp.getWriter().write(sb.toString());
    }

    private String createVersionXmlContent(ApkUpdate apkUpdate) {
        StringBuffer sb = new StringBuffer();
        if (apkUpdate == null) {
            sb.append("        <errorCode>400</errorCode>\n");
            sb.append("        <errorMsg>未配置升级设置</errorMsg>\n");
        } else {
            ApkFile apkFile = apkFileService.selectById(apkUpdate.getApkId());
            if (apkFile == null) {
                sb.append("        <errorCode>404</errorCode>\n");
                sb.append("        <errorMsg>升级设置对应文件信息丢失</errorMsg>\n");
            } else {
                sb.append(
                        "        <version>" + apkFile.getVersion() + "</version>\n" +
                                "        <name>" + apkFile.getFileName() + "</name>\n" +
                                "        <url>" + configService.getFastdfsServerUrl() + apkFile.getOssPath() + "</url>\n");
            }
        }
        return sb.toString();
    }
}
