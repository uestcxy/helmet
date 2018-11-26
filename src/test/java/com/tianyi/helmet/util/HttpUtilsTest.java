package com.tianyi.helmet.util;

import com.tianyi.helmet.server.util.HttpUtils;
import org.apache.http.client.methods.HttpPost;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuhanc on 2018/6/15.
 */
public class HttpUtilsTest {

    public static void main(String[] a) throws Exception{
        HttpPost post = new HttpPost("http://www.baidu.com");
        Map<String,Object> map = new HashMap();
        map.put("keyword","国家");
        HttpUtils.createUrlEncodedFormEntity(map,post);
        String resp = HttpUtils.executeHttp(post,true);
        System.out.println("resp="+resp);
    }
}
