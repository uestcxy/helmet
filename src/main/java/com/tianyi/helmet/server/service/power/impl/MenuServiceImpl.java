package com.tianyi.helmet.server.service.power.impl;

import com.tianyi.helmet.server.dao.power.MenuDao;
import com.tianyi.helmet.server.entity.power.Menu;
import com.tianyi.helmet.server.service.power.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 功能点信息
 * Created by wenxinyan on 2018/10/10.
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;

    public List<Menu> listAll(){
        return menuDao.listAll();
    }

    public Menu selectById(int id){
        return menuDao.selectById(id);
    }

    public List<Menu> listByNoPage(Map<String, Object> param){
        return menuDao.listByNoPage(param);
    }
}
