package com.tianyi.helmet.server.controller.helmetinterface;

import com.tianyi.helmet.server.controller.interceptor.TianyiUserHolder;
import com.tianyi.helmet.server.entity.client.TianyiContact;
import com.tianyi.helmet.server.entity.client.User;
import com.tianyi.helmet.server.service.client.TianyiContactService;
import com.tianyi.helmet.server.service.client.UserComponent;
import com.tianyi.helmet.server.entity.scene.svc.ContactState;
import com.tianyi.helmet.server.service.client.UserService;
import com.tianyi.helmet.server.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 联系人接口
 *
 * Created by liuhanc on 2018/6/30.
 */
@Controller
@RequestMapping("contact")
public class ContactController {
    @Autowired
    private TianyiContactService tianyiContactService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserComponent userComponent;

    /**
     * 头盔获得我的联系人姓名、用户名、设备在线状态
     * @return
     */
    @RequestMapping("mycontact")
    @ResponseBody
    public ResponseVo<List<ContactState>> contactList() {
        User tianyiUser = TianyiUserHolder.get();
//        User tianyiUser = userService.selectById(7);
        List<TianyiContact> tyContacts = tianyiContactService.selectByUserId(tianyiUser.getId());
        if (CollectionUtils.isEmpty(tyContacts)) {
            tyContacts = tianyiContactService.getDefaultContactList();
        }

        List<ContactState> contactList = tyContacts.stream().map(contact -> {
            User user = userService.selectById(contact.getContactId());
            return user;
        }).filter(user -> user != null)
                .map(user->{
                    ContactState cs = new ContactState(user.getId(),user.getAccount(),user.getName(),user.getNeUserHel(),user.getNeUserWeb(),user.getNeUserPhn());
                    int[] states = userComponent.onlineState(user);
                    cs.setHelmetOnline(states[0]);
                    cs.setMobileOnline(states[1]);
                    return cs;
                })
                .collect(Collectors.toList());

        return ResponseVo.success(contactList);
    }


}
