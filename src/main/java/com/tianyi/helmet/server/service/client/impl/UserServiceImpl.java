package com.tianyi.helmet.server.service.client.impl;

import com.tianyi.helmet.server.dao.client.UserDao;
import com.tianyi.helmet.server.entity.client.Company;
import com.tianyi.helmet.server.entity.client.NeteaseUser;
import com.tianyi.helmet.server.entity.client.TianyiContact;
import com.tianyi.helmet.server.entity.client.User;
import com.tianyi.helmet.server.entity.device.EquipmentLedger;
import com.tianyi.helmet.server.entity.power.Menu;
import com.tianyi.helmet.server.entity.power.RoleMenu;
import com.tianyi.helmet.server.service.client.CompanyService;
import com.tianyi.helmet.server.service.client.NeteaseUserService;
import com.tianyi.helmet.server.service.client.UserService;
import com.tianyi.helmet.server.service.device.EquipmentService;
import com.tianyi.helmet.server.service.job.RedisMqPublisher;
import com.tianyi.helmet.server.service.power.MenuService;
import com.tianyi.helmet.server.service.power.RoleMenuService;
import com.tianyi.helmet.server.service.support.ChannelNameConstants;
import com.tianyi.helmet.server.service.support.CacheKeyConstants;
import com.tianyi.helmet.server.util.RelationUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * 用户管理服务
 * Created by wenxinyan on 2018/9/25.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisMqPublisher redisMqPublisher;
    @Autowired
    private NeteaseUserService neteaseUserService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private MenuService menuService;

    @Transactional
    public int insert(User user) {
        int rs = userDao.insert(user);
        int count = neteaseUserService.countNull();
        if (count <= 50) {
            addToNeUserCreateQueue(user.getId());
        }
        int userId = user.getId();

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
        update(user);

        return rs;
    }

    @CacheEvict(value = CacheKeyConstants.USER_BY_ID, key = "#id.toString()")
    public int deleteById(int id) {
        int rs = userDao.deleteById(id);
        List<EquipmentLedger> list = equipmentService.selectByUserId(id);
        for (EquipmentLedger e : list) {
            e.setUserId(-1);
            equipmentService.update(e);
        }
        return rs;
    }

    @CacheEvict(value = CacheKeyConstants.USER_BY_ID, key = "#user.id.toString()")
    public int update(User user) {
        return userDao.update(user);
    }

    public List<User> listBy(Map<String, Object> map) {
        return userDao.listBy(map);
    }

    public int countBy(Map<String, Object> map) {
        return userDao.countBy(map);
    }

    @Cacheable(value = CacheKeyConstants.USER_BY_ID, key = "#id.toString()", unless = "#result == null")
    public User selectById(int id) {
        return userDao.selectById(id);
    }

    public List<User> listByNoPage(Map<String, Object> map) {
        return userDao.listByNoPage(map);
    }

    public List<String> listDept(Map<String, Object> map) {
        return userDao.listDept(map);
    }

    public void addToNeUserCreateQueue(int id) {
        redisMqPublisher.sendMessage(ChannelNameConstants.channelName_NeUser_Create, String.valueOf(id));//加入处理队列
    }

    public User selectByAccount(String account) {
        return userDao.selectByAccount(account);
    }

    public boolean checkAccount(String account) {
        Map<String, Object> map = new HashMap<>();
        map.put("account",account);
        map.put("status", 1);
        List<User> users = this.listByNoPage(map);
        if(users.size() > 0)
        {
            return true;
        }

        return false;
    }

    public boolean checkName(String name, int organisation) {
        Map<String, Object> map = new HashMap<>();
        map.put("namecheck",name);
        map.put("organisation", organisation);
        map.put("status", 1);
        List<User> users = this.listByNoPage(map);
        if(users.size() > 0)
        {
            return true;
        }

        return false;
    }

    public List<Menu> getHelmetMenuByUserId(int id) {
        List<Menu> result = new ArrayList<>();

        String[] roleCodes = userDao.selectById(id).getRoleCodes().split(",");
        for(int i = 0; i < roleCodes.length; i++){
            Map<String, Object> map = new HashMap<>();
            map.put("roleId", roleCodes[i]);
            List<RoleMenu> roleMenus = roleMenuService.listByNoPage(map);

            for(RoleMenu rm : roleMenus){
                Menu menu = menuService.selectById(rm.getMenuId());
                if(menu.getMenuType() == 1 && !result.contains(menu)){
                    result.add(menu);
                }
            }
        }

        return result;
    }
}
