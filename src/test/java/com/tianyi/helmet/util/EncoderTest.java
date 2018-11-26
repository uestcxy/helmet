package com.tianyi.helmet.util;

import com.tianyi.helmet.server.util.EncoderUtil;
import org.apache.commons.io.IOUtils;

import java.io.FileOutputStream;

/**
 * Created by liuhanc on 2017/10/15.
 */
public class EncoderTest {

    public static void main(String[] a) throws Exception {
       String ss =  EncoderUtil.sha1("createTime=20180711175700&description=工单视频&machineCode=&orderNo=20180703004&tag=故障,维修&appSecret=d4fa1741dcd0d5fda94a41bc98161bdc");
        System.out.println("ss="+ss);
//       String value = java.net.URLEncoder.encode("张三","GBK");//%E5%BC%A0%E4%B8%89
//        System.out.println(value);
//        value = java.net.URLEncoder.encode("zhangsan","GBK");
//        System.out.println(value);

//        System.out.println(new Date(1508811702605l));

//        System.out.println(Long.parseLong("009B",16));
//data:image/jpeg;base64,
//        byte[] bytes = EncoderUtil.base64Decode("");
//        System.out.println("图片大小=" + bytes.length);
//        IOUtils.write(bytes, new FileOutputStream("d:/aa.jpg"));
    }
}
