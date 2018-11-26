//package com.tianyi.helmet.server.controller.client;
//
//import com.tianyi.helmet.server.controller.interceptor.LoginUserHolder;
//import com.tianyi.helmet.server.entity.client.LoginUserInfo;
//import com.tianyi.helmet.server.entity.client.NeteaseUser;
//import com.tianyi.helmet.server.entity.client.TianyiUser;
//import com.tianyi.helmet.server.service.client.NeteaseUserService;
//import com.tianyi.helmet.server.service.client.TianyiUserRoleService;
//import com.tianyi.helmet.server.service.client.TianyiUserService;
//import com.tianyi.helmet.server.service.client.UserComponent;
//import com.tianyi.helmet.server.util.Commons;
//import com.tianyi.helmet.server.vo.PageListVo;
//import com.tianyi.helmet.server.vo.ResponseVo;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
///**
// * 田一后台用户
// * <p>
// * Created by liuhanc on 2017/11/2.
// */
//@Controller
//@RequestMapping("tianyiuser")
//public class TianyiUserController {
//
//    private Logger logger = LoggerFactory.getLogger(TianyiUserController.class);
//
//    @Autowired
//    private TianyiUserService tianyiUserService;
//    @Autowired
//    private UserComponent userComponent;
//    @Autowired
//    private TianyiUserRoleService tianyiUserRoleService;
//    @Autowired
//    private NeteaseUserService neteaseUserService;
//
//    /**
//     * 进入列表页面首页
//     *
//     * @return
//     */
//    @RequestMapping(value = "list")
//    public String list(String keyword, Model model) {
//        return list(1, keyword, model);
//    }
//
//    /**
//     * 进入列表页面某一页
//     *
//     * @return
//     */
//    @RequestMapping(value = "list/{page}")
//    public String list(@PathVariable Integer page, String keyword, Model model) {
//        Map<String, Object> map = PageListVo.createParamMap(page);
//        if (!StringUtils.isEmpty(keyword)) {
//            map.put("keyword", keyword);
//        }
//        List<TianyiUser> list = tianyiUserService.listBy(map);
//        int count = tianyiUserService.countBy(map);
//        PageListVo<TianyiUser> vo = new PageListVo(page);
//        vo.setTotal(count);
//        vo.setList(list);
//        model.addAttribute("vo", vo);
//        model.addAttribute("keyword", keyword);
//        return "user/listTianyiUser";
//    }
//
//    @RequestMapping(value = "/get/{userId}")
//    @ResponseBody
//    public ResponseVo<TianyiUser> get(@PathVariable int userId) {
//        TianyiUser user = tianyiUserService.selectById(userId);
//        if (user == null) {
//            return ResponseVo.fail("用户不存在");
//        }
//
//        LoginUserInfo loginUserInfo = LoginUserHolder.get();
//        if (loginUserInfo.getId() == user.getId() || loginUserInfo.isAdmin()) {
//            //clone一个返回，避免影响到缓存
//            TianyiUser retUser = new TianyiUser();
//            retUser.setId(user.getId());
//            retUser.setNeUsername(user.getNeUsername());
//            retUser.setUserSex(user.getUserSex());
//            retUser.setCompany(user.getCompany());
//            retUser.setRegTime(user.getRegTime());
//            retUser.setName(user.getName());
//            retUser.setUsername(user.getUsername());
//            retUser.setMobile(user.getMobile());
//            retUser.setBindWxTime(user.getBindWxTime());
//            retUser.setWxId(user.getWxId());
//
//            String roleCodes = tianyiUserRoleService.selectByUserId(user.getId()).stream().map(userrole -> userrole.getRoleCode()).distinct().collect(Collectors.joining(","));
//            retUser.setRoleCodes(roleCodes);
//
//            return ResponseVo.success(retUser);
//        }
//        return ResponseVo.fail("您无权获取其它用户信息");
//    }
//
//    @RequestMapping(value = "save", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseVo<TianyiUser> save(TianyiUser tianyiUser) {
//        LoginUserInfo loginUserInfo = LoginUserHolder.get();
//        if (!loginUserInfo.isAdmin()) {
//            return ResponseVo.fail("无权修改用户");
//        }
//        if (tianyiUser.getId() == 0) {
//            return ResponseVo.fail("无法修改用户");
//        }
//        TianyiUser oldUser = tianyiUserService.selectById(tianyiUser.getId());
//        if (oldUser == null) {
//            return ResponseVo.fail("用户不存在,无法修改用户");
//        }
//
//        //如果修改了手机号
//        if (!Objects.equals(tianyiUser.getMobile(), oldUser.getMobile()) && !StringUtils.isEmpty(tianyiUser.getMobile())) {
//            if (!Commons.isMobile(tianyiUser.getMobile())) {
//                return ResponseVo.fail("手机号不合法");
//            }
//
//            //检查新手机号是否已被他人占用，如果已被占用则不可以修改
//            TianyiUser mobileUser = tianyiUserService.selectByMobile(tianyiUser.getMobile());
//            if (mobileUser != null) {
//                return ResponseVo.fail("手机号对应用户已存在[" + mobileUser.getUsername() + "]，请修改为其他手机号");
//            }
//        }
//        //以下几个属性不能随意修改，故置空不修改
//        tianyiUser.setPassword(null);
//        tianyiUser.setUsername(null);
//        tianyiUser.setNeUsername(null);
//        tianyiUser.setUsername(null);
//        tianyiUser.setRegTime(null);
//        tianyiUser.setBindWxTime(null);
//        boolean success = userComponent.updateTianyiUserAndRole(tianyiUser);
//        if (success)
//            return ResponseVo.success(tianyiUser);
//        return ResponseVo.fail("保存失败");
//    }
//
//
//    @RequestMapping(value = "add", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseVo add(TianyiUser tianyiUser) {
//        LoginUserInfo loginUserInfo = LoginUserHolder.get();
//        if (!loginUserInfo.isAdmin()) {
//            return ResponseVo.fail("无权添加用户");
//        }
//
//        String userName = tianyiUser.getUsername();
//        if(StringUtils.isEmpty(userName)){
//            return ResponseVo.fail("用户账号不可为空.");
//        }
//        String password = tianyiUser.getPassword();
//        if(StringUtils.isEmpty(password)){
//            return ResponseVo.fail("用户密码不可为空.");
//        }
//        if(userName.toLowerCase().startsWith("helmet")){
//            return ResponseVo.fail("用户账号不可以helmet开头.");//helmet是系统保留关键字
//        }
//
//        TianyiUser oldUser = tianyiUserService.selectByUsername(userName);
//        if (oldUser != null) {
//            return ResponseVo.fail("用户账号已存在." + userName);
//        }
//        String mobile = tianyiUser.getMobile();
//        if (!StringUtils.isEmpty(mobile)) {
//            //检查手机号格式是否正确
//            if (!Commons.isMobile(mobile)) {
//                return ResponseVo.fail("手机号不合法");
//            }
//            oldUser = tianyiUserService.selectByMobile(mobile);
//            if (oldUser != null) {
//                return ResponseVo.fail("手机号对应用户已存在." + mobile);
//            }
//        }
//
//        boolean neUsernameIlligle = false;
//        String neUsername = tianyiUser.getNeUsername();
//        if (!StringUtils.isEmpty(neUsername)) {
//            oldUser = tianyiUserService.selectByNeUsername(neUsername);
//            if (oldUser != null) {
//                return ResponseVo.fail("网易账号对应用户已存在." + neUsername);
//            }
//
//            NeteaseUser neteaseUser = neteaseUserService.selectByUsername(neUsername);
//            if (neteaseUser == null) {
//                //输入的网易账号不存在
//                neUsernameIlligle = true;
//                tianyiUser.setNeUsername(null);
//            }
//        }
//
//        //String userName,String mobile, String password,String name,String company,int userSex,String neUserName,String roleCodes
//        try {
//            userComponent.addNewUser(tianyiUser.getUsername(), tianyiUser.getMobile(), tianyiUser.getPassword(), tianyiUser.getName(), tianyiUser.getCompany(), tianyiUser.getUserSex(), tianyiUser.getNeUsername(), tianyiUser.getRoleCodes());
//        } catch (Exception e) {
//            return ResponseVo.fail("" + e.getMessage());
//        }
//        return ResponseVo.success("添加用户成功." + (neUsernameIlligle ? "但设置的网易用户账号无效，所以忽略了网易账号的设置" : ""));
//    }
//
//}
