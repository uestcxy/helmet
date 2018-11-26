package com.tianyi.helmet.service.tianyuan;

import com.tianyi.helmet.server.controller.signature.SignatureRequest;
import com.tianyi.helmet.server.util.HttpUtils;
import org.apache.http.client.methods.HttpPost;

/**
 * 天远零件接口测试
 *
 * Created by liuhanc on 2018/3/5.
 */
public class TianYuanPartServiceTest {

    public static void main(String[] a) throws Exception{
        String appKey = "tianyi.helmet";
        String appSecret="d4fa1741dcd0d5fda94a41bc98161bdc";
        SignatureRequest request = new  SignatureRequest();
        request.setAppKey(appKey);
        String[] signature = request.calcuteSignature(appSecret);
        System.out.println("签名="+signature[1]+",签名字符串="+signature[0]);

        String url="http://smart.tygps.com/ty/data/GetTons";
        HttpPost post = new HttpPost(url);
        post.addHeader("appKey",appKey);
        post.addHeader("signature",signature[1]);
        post.addHeader("imei","helmet1008");

        String json = "{\"brand\":\"小松\"}";
        HttpUtils.createJsonEntity(json,post);
        String resp = HttpUtils.executeHttp(post,true);
        System.out.println("请求反馈:"+resp);
    }
}
