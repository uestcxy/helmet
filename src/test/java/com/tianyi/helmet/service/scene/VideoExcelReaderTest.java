package com.tianyi.helmet.service.scene;

import com.alibaba.fastjson.JSON;
import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.service.scene.VideoExcelDataPo;
import com.tianyi.helmet.server.service.scene.VideoExcelReader;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileInputStream;
import java.util.List;

/**
 * Created by liuhanc on 2018/7/25.
 */
public class VideoExcelReaderTest extends BaseServiceTest {
    @Autowired
    private VideoExcelReader videoExcelReader;


    @Test
    public void testRead() throws Exception {
        List<VideoExcelDataPo> list =  videoExcelReader.readExcel(new FileInputStream("D:\\workplace\\tianyi\\backend\\hm-server\\videoexcel.template.xlsx"));
        System.out.println("读取结果："+JSON.toJSONString(list));
    }

}
