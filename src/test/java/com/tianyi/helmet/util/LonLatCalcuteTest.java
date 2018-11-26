package com.tianyi.helmet.util;

import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.service.data.TyBoxDataV1Resorver;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liuhanc on 2017/11/16.
 */
public class LonLatCalcuteTest extends BaseServiceTest{
    @Autowired
    TyBoxDataV1Resorver tyBoxDataV1Resorver;

    private void calcute(String lonlatStr){
        int[] lonNum = tyBoxDataV1Resorver.calcuteLatLong(lonlatStr);
        int lonNum1 = lonNum[1];
        float lonNum2 = tyBoxDataV1Resorver.calcuteLatLong(lonNum1);
        System.out.println(lonlatStr+","+lonNum1+","+lonNum2);
    }

    @Test
    public void testGps(){
        String lon = "04196667";
        String lat = "015C4DC9";
        calcute(lon);
        calcute(lat);
    }
}
