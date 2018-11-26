package com.tianyi.helmet.server.service.job;


import com.google.common.base.Strings;
import com.tianyi.helmet.server.entity.client.NeteaseUser;
import com.tianyi.helmet.server.entity.client.User;
import com.tianyi.helmet.server.service.client.NeteaseUserComponent;
import com.tianyi.helmet.server.service.client.NeteaseUserService;
import com.tianyi.helmet.server.service.client.UserService;
import com.tianyi.helmet.server.service.support.ConfigService;
import com.tianyi.helmet.server.vo.AppAccountVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


/**
 * 创建网易账号并且给用户添加3个网易账号
 * <p>
 * create by xiayuan 2018/10/11
 */
@Component //在applicationContext.xml中配置了此类接收某个pattern的订阅
public class NeteaseUserCreateJob implements MessageListener {
    private Logger logger = LoggerFactory.getLogger(NeteaseUserCreateJob.class);

    @Autowired
    private ConfigService configService;
    @Autowired
    private NeteaseUserComponent neteaseUserComponent;
    @Autowired
    private NeteaseUserService neteaseUserService;
    @Autowired
    private UserService userService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            logger.debug("NeteaseUserCreateJob ____________________________________________________");
            String patternStr = new String(pattern);
            String body = new String(message.getBody());
            logger.debug("NeteaseUserCreateJob 收到消息:patternStr=" + patternStr + ",channel=" + new String(message.getChannel()) + ",body=" + body);
            int success = processCreateNeUser(100);
            if(success<100){
                logger.error("创建100个网易账号失败，一共创建" +success+"个");
            }
            else{
                logger.error("创建100个网易账号成功");
            }
        } catch (Exception e) {
            logger.error("网易用户创建队列消息异常.", e);
        }
    }

    /**
     * 查询3个账号
     *
     * @param
     */
    @Transactional
    public void processCheckNeUser(int userId) {
        int count = neteaseUserService.countNull();
        int success = 0;
        if (count <= 30) {
            success = processCreateNeUser(100);
            if(success<100){
                logger.error("创建100个网易账号失败，一共创建" +success+"个");
            }
            else{
                logger.error("创建100个网易账号成功");
            }
        }
        User user = userService.selectById(userId);

        String neUserHel = neteaseUserService.selectNoUser();
        NeteaseUser neteaseUser1 = neteaseUserService.selectByUsername(neUserHel);
        neteaseUser1.setUserId(userId);
        neteaseUserService.updateById(neteaseUser1);
        user.setNeUserHel(neUserHel);
        String neUserWeb = neteaseUserService.selectNoUser();
        NeteaseUser neteaseUser2 = neteaseUserService.selectByUsername(neUserWeb);
        neteaseUser2.setUserId(userId);
        neteaseUserService.updateById(neteaseUser2);
        user.setNeUserWeb(neUserWeb);
        String neUserPhn = neteaseUserService.selectNoUser();
        NeteaseUser neteaseUser3 = neteaseUserService.selectByUsername(neUserPhn);
        neteaseUser3.setUserId(userId);
        neteaseUserService.updateById(neteaseUser3);
        user.setNeUserPhn(neUserPhn);

        userService.update(user);
    }


    //创建100个账号 count=100
    private int processCreateNeUser(int count) {
        String prefix = configService.getNeteaseUserNamePrefix();
        String maxUserName = neteaseUserService.selectMaxUserName(prefix);
        int minCount = 0010;
        if (maxUserName != null) {
            minCount = Integer.parseInt(maxUserName.substring(prefix.length())) + 1;
        }
        int successCount = 0;
        for (int i = 0; i < count; i++) {
            String userCounter = String.valueOf(minCount + i);
            String username = prefix + Strings.padStart(userCounter, 4, '0');
            //1头盔app用户;2 web/手机app用户 3管理员 4未设置用户类型
            logger.debug("userCounter::::::::::::::::::"+userCounter);
            logger.debug("username:::::::::::::::::::"+username);
            AppAccountVo vo = neteaseUserComponent.regNeteaseUser(0, username, username, 1);
            if ("200".equals(vo.getCode()))
                successCount++;
            logger.debug("扩充头盔账号.code=" + vo.getCode() + ",username=" + username);
        }
        return successCount;
    }

}
