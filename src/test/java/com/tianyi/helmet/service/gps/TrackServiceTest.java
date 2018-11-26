package com.tianyi.helmet.service.gps;

import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.service.data.TyBoxDataService;
import com.tianyi.helmet.server.service.file.UploadEntityServiceFactory;
import com.tianyi.helmet.server.service.track.TrackService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liuhanc on 2017/11/1.
 */
public class TrackServiceTest extends BaseServiceTest {

    @Autowired
    TrackService trackService;
    @Autowired
    private UploadEntityServiceFactory uploadEntityServiceFactory;

}
