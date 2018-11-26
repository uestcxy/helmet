package com.tianyi.helmet.service.file;

import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.service.fastdfs.FastDfsClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
/**
 * Created by liuhanc on 2017/9/26.
 */
public class FastDfsClientTest extends BaseServiceTest {

    @Autowired
    FastDfsClient fastDfsClient;

    @Test
    public void testUploadFile() throws Exception{
        File f = new File("D:\\workplace\\tianyi\\tyhm\\hm-server\\src\\main\\webapp\\video\\2017-10-13\\58fe854d-64cd-4acc-9211-6cfb7d48ccf3.mp4");
        String ext= "mp4";
        String [] ret = fastDfsClient.uploadFile(f.getAbsolutePath(),ext,3);
        if(ret != null ){
            System.out.println("group="+ret[0]);
            System.out.println("remoteFileName = "+ret[1]);
        }else{
            System.out.println("upload file return null");
        }
    }
    @Test
    public void testUpload() throws Exception{
//        File f = new File("C:\\Users\\liuhanc\\Downloads\\7239138402398249215.jpg");
//        String ext= "jpg";
        File f = new File("D:\\workplace\\tianyi\\tyhm\\hm-server\\src\\main\\webapp\\video\\2017-10-13\\58fe854d-64cd-4acc-9211-6cfb7d48ccf3.mp4");
        String ext= "mp4";
        FileInputStream fis = new FileInputStream(f);
        ByteArrayOutputStream byos = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len = -1;
        while((len = fis.read(bytes)) != -1){
            byos.write(bytes,0,len);
        }
        String [] ret = fastDfsClient.uploadFile(byos.toByteArray(),ext,1);
        if(ret != null ){
            System.out.println("group="+ret[0]);
            System.out.println("remoteFileName = "+ret[1]);
        }else{
            System.out.println("upload file return null");
        }
    }

    @Test
    public void testDownload(){
        byte[] bytes = fastDfsClient.downloadFile("group1","M00/00/00/wKgeJ1na_DqAA3bvAAoC5yREQJU115.jpg",1);
        System.out.println("下载文件大小："+bytes.length);
    }
}
