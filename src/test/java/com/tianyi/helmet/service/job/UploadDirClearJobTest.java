package com.tianyi.helmet.service.job;

import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.service.job.UploadDirClearJob;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liuhanc on 2018/5/23.
 */
public class UploadDirClearJobTest extends BaseServiceTest {

    @Autowired
    private UploadDirClearJob uploadDirClearJob;

    @Test
    public void doClearTest() {
        uploadDirClearJob.doClear();
    }
}
