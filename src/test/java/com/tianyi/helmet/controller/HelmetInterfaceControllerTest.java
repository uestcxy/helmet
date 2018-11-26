package com.tianyi.helmet.controller;

import com.alibaba.fastjson.JSONObject;
import com.tianyi.helmet.server.controller.signature.SignatureRequest;
import com.tianyi.helmet.server.util.HttpUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 头盔的接口测试器
 * <p>
 * Created by liuhanc on 2018/3/20.
 */
public class HelmetInterfaceControllerTest {

    public static String imei = "helmet1003";
    public static String url_local = "http://localhost:8080/";
    public static String url_test = "http://smarttest.tygps.com/";
    public static String url_prod = "http://smart.tygps.com/";
    public static String url_vpn = "http://192.168.22.39/";
    public static boolean setSignHeader = true;

    private static String getBaseUrl(String env) {
        if ("local".equals(env))
            return url_local;
        if ("test".equals(env))
            return url_test;
        if ("prod".equals(env))
            return url_prod;
        if ("vpn".equals(env))
            return url_vpn;
        return url_local;
    }

    public static byte[] executeDownFile(String env, String uri, Map<String, String> params) {
        if (params != null) {
            String appendString = params.keySet().stream().map(key -> {
                return key + "=" + params.get(key);
            }).collect(Collectors.joining("&"));
            uri += "?" + appendString;
        }

        HttpGet get = new HttpGet(getBaseUrl(env) + uri);
        return executeBinary(get, params);
    }

    public static String executeGet(String env, String uri, Map<String, String> params) {
        if (params != null) {
            String appendString = params.keySet().stream().map(key -> {
                return key + "=" + params.get(key);
            }).collect(Collectors.joining("&"));
            uri += "?" + appendString;
        }
        HttpGet get = new HttpGet(getBaseUrl(env) + uri);
        return execute(get, params);
    }

    public static String executePost(String env, String uri, Map<String, String> params) {
        HttpPost post = new HttpPost(getBaseUrl(env) + uri);
        HttpUtils.createUrlEncodedFormEntity(params, post);
        return execute(post, params);
    }

    public static String executePostJson(String env, String uri, JSONObject json) {
        HttpPost post = new HttpPost(getBaseUrl(env) + uri);
        String jsonStr = json.toJSONString();
        System.out.println("请求json=" + jsonStr);
        HttpUtils.createJsonEntity(jsonStr, post);
        return execute(post, null);
    }

    public static String executeUpload(String env, String uri, Map<String, String> params, String fileParaName, File file) {
        Map<String, File> uploadFiles = new HashMap<>(1);
        uploadFiles.put(fileParaName, file);
        return executeMultipleUpload(env, uri, params, uploadFiles);
    }

    public static String executeMultipleUpload(String env, String uri, Map<String, String> params, Map<String, File> uploadFiles) {
        HttpPost post = new HttpPost(getBaseUrl(env) + uri);
        HttpUtils.createUploadFileFormEntity(params, post, uploadFiles);
        return execute(post, params);
    }

    public static String execute(HttpUriRequest request, Map<String, String> params) {
        if (setSignHeader)
            setSignHeader(request, params);

        System.out.println("请求" + request.getURI());
        try {
            String resp = HttpUtils.executeHttp(request, false);
            System.out.println("请求" + request.getURI() + ",resp=" + resp);
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static byte[] executeBinary(HttpUriRequest request, Map<String, String> params) {
        if (setSignHeader)
            setSignHeader(request, params);

        System.out.println("请求" + request.getURI());
        try {
            byte[] bytes = HttpUtils.executeBinnaryHttp(request, false);
            System.out.println("请求" + request.getURI() + ",resp=" + bytes);
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String createSign(SortedMap<String, String> parameters) {
        SignatureRequest req = new SignatureRequest();
        req.setAppKey("tianyi.helmet");
        req.setParameters(parameters);
        String[] sign = req.calcuteSignature("d4fa1741dcd0d5fda94a41bc98161bdc");
        return sign[1];
    }

    public static void setSignHeader(HttpUriRequest requestBase, Map<String, String> parameters) {
        String sign = createSign(new TreeMap(parameters == null ? Collections.emptyMap() : parameters));
        requestBase.addHeader("appKey", "tianyi.helmet");
        requestBase.addHeader("signature", sign);
        requestBase.addHeader("imei", imei);
        System.out.println("传入header.sign=" + sign + ",imei=" + imei);
    }
}
