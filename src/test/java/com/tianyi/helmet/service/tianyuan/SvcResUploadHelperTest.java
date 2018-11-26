package com.tianyi.helmet.service.tianyuan;

import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.service.svc.SvcResUploadHelper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liuhanc on 2018/4/10.
 */
public class SvcResUploadHelperTest extends BaseServiceTest {
    @Autowired
    private SvcResUploadHelper svcResUploadHelper;

    @Test
    public void testAddToVideoParseQueue(){
        svcResUploadHelper.addToProcessQueue("toSvcVideoParse",6);
    }

    @Test
    public void testAddToImageParseQueue(){
        svcResUploadHelper.addToProcessQueue("toSvcImageParse",5);
    }

    @Test
    public void testAddToAudioParseQueue(){
        svcResUploadHelper.addToProcessQueue("toSvcAudioParse",1);
        svcResUploadHelper.addToProcessQueue("toSvcAudioParse",2);
    }
}
