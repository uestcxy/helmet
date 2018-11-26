package com.tianyi.helmet.service.tianyuan;

import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.service.client.TianYuanUserService;
import com.tianyi.helmet.server.service.tianyuan.TianYuanApiBasicHelper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liuhanc on 2018/3/30.
 */
public class TianYuanServiceTest extends BaseServiceTest {

    @Autowired
    private TianYuanUserService tianYuanUserService;
    @Autowired
    private TianYuanApiBasicHelper tianYuanApiBasicHelper;

    @Test
    public void testGetUser() {
        String fullToken = "Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6IkE0MDc1MDAwRUNFMTVDRDZBMjY3N0M0QUJDNUMxNTMwRDYzRDRCREYiLCJ0eXAiOiJKV1QiLCJ4NXQiOiJwQWRRQU96aFhOYWlaM3hLdkZ3Vk1OWTlTOTgifQ.eyJuYmYiOjE1MjIzNzc2NTcsImV4cCI6MTUyNDk2OTY1NywiaXNzIjoiaHR0cDovL3Rlc3Q0LnR5Z3BzLmNvbS90eW1pY3Mvb2F1dGgiLCJhdWQiOlsiaHR0cDovL3Rlc3Q0LnR5Z3BzLmNvbS90eW1pY3Mvb2F1dGgvcmVzb3VyY2VzIiwiYXBpIl0sImNsaWVudF9pZCI6InJlbnRhbHdlYiIsInN1YiI6IjEwMDAwMDIxMjIiLCJhdXRoX3RpbWUiOjE1MjIzNzc2NTcsImlkcCI6ImxvY2FsIiwiYWNudF9pZCI6IjEwMDAwMDIxMjIiLCJncm91cF9pZCI6IiIsInN5c2lkIjoiMCIsIm9wcnRfbmFtZSI6InRpYW55aWtlamkiLCJzY29wZSI6WyJvcGVuaWQiLCJwcm9maWxlIiwiYXBpIiwib2ZmbGluZV9hY2Nlc3MiXSwiYW1yIjpbInBhc3N3b3JkIl19.qHpaKIjRgwtPE3n90bkoLsSlU3lJsm85_kxTDBIuf3os9qUR4U0sZ7yfSFzj571PzMUbjgaQ4XtzOuWgXAg04ULY4dQHD5fga4Qzn1MpMkL4kVNx1cMYwImjCpmQoMtIJl88ieS6Lv_kU4julH2gB8-q4UZdRmd02z6xuYNhdl30uQH9MyIYOJPrOdkyZ1ubj196KGp54Ly9-VlZyWToVO8c8DFtgQ10mN_ShEUhNODHTWFSFYeBvkMni9EJJpxp6mEqe-aqOaYKGQktjE-Aq5kkDLjc0JPTgWxzGJ91P-iXSIjKm5uE_vswZTuVwNXhCKs4nN7ynZ_Pqkv16ppYPQ";
        tianYuanUserService.getTianYuanUserOprtInfo(false, fullToken);
    }

    @Test
    public void testUserLogin() {
        tianYuanApiBasicHelper.userPassLogin("gaoxuzhao", "123");
    }
    @Test
    public void testProdUserLogin() {
        tianYuanApiBasicHelper.userPassLogin("prod#liuhan", "liu1234");
    }
}
