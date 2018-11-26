package com.tianyi.helmet.controller;

import com.tianyi.helmet.server.entity.svc.SvcAudioTypeEnum;
import com.tianyi.helmet.server.entity.svc.SvcImageTypeEnum;
import com.tianyi.helmet.server.entity.svc.SvcVideoTypeEnum;
import com.tianyi.helmet.server.service.svc.SvcMetaDataHelper;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuhanc on 2018/3/19.
 */
public class SvcDataControllerTest extends HelmetInterfaceControllerTest {



    private String testRwh = "RW20180410007";
    private String testEnv = "dev";

    @Before
    public void setup() {
        imei = "helmet1007";
        testEnv = "dev";
    }

    @Test
    public void testTaskCount() {
        String uri = "svc/taskCount";//operId = 1000002122
        executeGet(testEnv, uri, null);
    }

    @Test
    public void testTaskList() {
        String uri = "svc/taskList";
        Map<String, String> map = new HashMap();
        map.put("page", "1");
        map.put("pageSize", "100");
//        map.put("taskType", "expired");
//        executeGet(true, uri, map);

        String[] types = new String[]{
//                "notExpired",
                "expired"
//                ,
//                "today"
        };
        Arrays.stream(types).forEach(type -> {
            map.put("taskType", type);
            executeGet(testEnv, uri, map);
        });
    }

    @Test
    public void testTaskCustomer() {
        String uri = "svc/taskCustomer";
        Map<String, String> map = new HashMap();
        map.put("rwh", testRwh);
        executeGet(testEnv, uri, map);
    }

    @Test
    public void testTaskUsage() {
        String uri = "svc/taskUsage";
        Map<String, String> map = new HashMap();
        map.put("rwh", testRwh);
        executeGet(testEnv, uri, map);
    }

    @Test
    public void testTaskUsageSave() {
        String uri = "svc/taskUsage";
        Map<String, String> map = new HashMap();
        map.put("rwh", testRwh);
        map.put("gongkuang", "水泥地");
        map.put("ranyou", "5#");
        map.put("gaizhuang", "大臂");
        map.put("hour", "5488");
        executePost(testEnv, uri, map);
    }

    @Test
    public void testTaskTimeMiles() {
        String uri = "svc/taskTimeMile";
        Map<String, String> map = new HashMap();
        map.put("rwh", testRwh);
        executeGet(testEnv, uri, map);
    }

    @Test
    public void testTaskFault() {
        String uri = "svc/taskFault";
        Map<String, String> map = new HashMap();
        map.put("rwh", testRwh);
        executeGet(testEnv, uri, map);
    }


    @Test
    public void testTaskFaultResolveMethod() {
        String uri = "svc/taskFaultResolveMethod";
        Map<String, String> map = new HashMap();
        map.put("rwh", testRwh);
        executeGet(testEnv, uri, map);
    }

    @Test
    public void testTaskFaultResolveMethodSave() {
        String uri = "svc/taskFaultResolveMethod";
        Map<String, String> map = new HashMap();
        map.put("rwh", testRwh);
        map.put("resolveMethod", "不解决，解决的很好");
        executePost(testEnv, uri, map);
    }

    @Test
    public void testTaskFaultHandled() {
        String uri = "svc/taskFaultHandled";
        Map<String, String> map = new HashMap();
        map.put("rwh", testRwh);
        executeGet(testEnv, uri, map);
    }

    @Test
    public void testTaskFaultHandledSave() {
        String uri = "svc/taskFaultHandled";
        Map<String, String> map = new HashMap();
        map.put("rwh", testRwh);
        map.put("handled", "false");
        map.put("notHandleReason","未能处理，因为客户要紧急开工不想耽误时间");
        executePost(testEnv, uri, map);
    }

    @Test
    public void testTaskFaultRepaired() {
        String uri = "svc/taskFaultRepaired";
        Map<String, String> map = new HashMap();
        map.put("rwh", testRwh);
        executeGet(testEnv, uri, map);
    }

    @Test
    public void testTaskFaultRepairedSave() {
        String uri = "svc/taskFaultRepaired";
        Map<String, String> map = new HashMap();
        map.put("rwh", testRwh);
        map.put("repaired", "true");
        executePost(testEnv, uri, map);
    }

    @Test
    public void testUserOpinion() {
        String uri = "svc/taskUserOpinion";
        Map<String, String> map = new HashMap();
        map.put("rwh", testRwh);
        executeGet(testEnv, uri, map);
    }

    @Test
    public void testUploadVideo() {
        Map<String, String> params = new HashMap<>(3);
        params.put("rwh", testRwh);

        Map<String, String> map = new HashMap();
        map.put(SvcVideoTypeEnum.site.toString(), "工地");
        map.put(SvcVideoTypeEnum.digger.toString(), "挖机");
//        map.put(SvcVideoTypeEnum.faultCheck.toString(), "故障检查");
        map.put(SvcVideoTypeEnum.faultRepaird.toString(), "故障修复");

        map.keySet().stream().forEach(key -> {
            params.put("videoType", key);
            File file = new File("D:\\服务日志资源测试\\" + map.get(key) + ".mp4");
            System.out.println("上传视频:" + map.get(key) + "...");
            executeUpload(testEnv, "svc/uploadVideo", params, "videofile", file);
        });
    }

    @Test
    public void testUploadAudio() {
        Map<String, String> params = new HashMap<>(3);
        params.put("rwh", testRwh);

        Map<String, String> map = new HashMap();
        map.put(SvcAudioTypeEnum.faultContent.name(),"故障原因");
        map.put(SvcAudioTypeEnum.notHandleReason.toString(), "未处理原因");
        map.put(SvcAudioTypeEnum.userOpinion.toString(), "用户意见");

        map.keySet().stream().forEach(key -> {
            params.put("audioType", key);
            File file = new File("D:\\服务日志资源测试\\" + map.get(key) + ".wav");
            System.out.println("上传声音:" + map.get(key) + "...");
            executeUpload(testEnv, "svc/uploadAudio", params, "audiofile", file);
        });
    }



    @Test
    public void testSubmit() {
        String uri = "svc/taskSubmit";
        Map<String, String> map = new HashMap();
        map.put("rwh", testRwh);
        executeGet(testEnv, uri, map);
    }


    @Test
    public void testUploadImage() {
        Map<String, String> params = new HashMap<>(3);
        params.put("rwh", testRwh);
        params.put("imageType", SvcImageTypeEnum.jihao.name());
        File file = new File("D:\\服务日志资源测试\\机号.jpg");
        executeUpload(testEnv, "svc/uploadImage", params, "imagefile", file);
    }


    @Test
    public void testTaskFaultSave() {
        String uri = "svc/taskFault";
        Map<String, String> map = new HashMap();
        map.put("rwh", testRwh);
        map.put("youchang", "true");
        map.put("gongshifei", "12.334");
        map.put("tingji", "false");
        map.put("faultContent", "用户操作不当导致.");
        executePost(testEnv, uri, map);
    }

    @Test
    public void testUserOpinionSave() {
        String uri = "svc/taskUserOpinion";
        Map<String, String> map = new HashMap();
        map.put("rwh", testRwh);
        map.put("satisfy", "满意");
        map.put("userOpinion","请给发奖.");
        executePost(testEnv, uri, map);
    }

    @Test
    public void testTaskTimeMileSave() {
        String uri = "svc/taskTimeMile";
        Map<String, String> map = new HashMap();
        map.put("rwh", testRwh);
        map.put("setoutTime", "11:30");
        map.put("returnTime", "15:40");
        map.put("mile", "77");
        executePost(testEnv, uri, map);
    }

    @Test
    public void testUpload(){
        Arrays.stream(new String[]{"RW20180410040","RW20180410039","RW20180410038","RW20180410037"}).forEach(rwh->{
            Map<String, String> params = new HashMap<>(3);
            params.put("rwh", rwh);
            params.put("imageType", SvcImageTypeEnum.jihao.name());
            File file = new File("D:\\服务日志资源测试\\机号.jpg");
            executeUpload(testEnv, "svc/uploadImage", params, "imagefile", file);
        });
    }

    @Test
    public void testInputHelpApp(){
        testTaskTimeMileSave();
        testUserOpinionSave();
        testTaskFaultSave();
    }

    @Test
    public void testEnum() {
        SvcMetaDataHelper.SvcFaultPicMeta meta = SvcMetaDataHelper.SvcFaultPicMeta.JIHAO;
        System.out.println(meta.name());
        System.out.println(meta.toString());
    }

}