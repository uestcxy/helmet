package com.tianyi.helmet.service.netease;

import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.service.netease.NeteaseApiService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liuhanc on 2018/3/23.
 */
public class NeteaseUserServiceTest extends BaseServiceTest {
    @Autowired
    private NeteaseApiService apiService;


    @Test
    public void testRegetToken() {
        String token = apiService.userRefreshToken("Zhangxin");
        System.out.println("生成的token=" + token);
    }
}
