package com.tianyi.helmet.service.tianyuan;

import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.entity.client.TianYuanUser;
import com.tianyi.helmet.server.service.client.TianYuanUserService;
import com.tianyi.helmet.server.service.job.TyUserSvcTaskRefreshJob;
import com.tianyi.helmet.server.service.tianyuan.TianYuanSvcService;
import com.tianyi.helmet.server.util.Dates;
import com.tianyi.helmet.server.vo.ResponseVo;
import org.apache.commons.fileupload.util.Streams;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 天远服务日志接口测试
 * <p>
 * Created by liuhanc on 2018/3/28.
 */
public class TianYuanSvcServiceTest extends BaseServiceTest {
    @Autowired
    private TianYuanUserService tianYuanUserService;

    @Autowired
    private TyUserSvcTaskRefreshJob tyUserSvcTaskRefreshJob;

    @Autowired
    private TianYuanSvcService tianYuanSvcService;

    @Test
    public void testRefreshSvcTask() {
        TianYuanUser user = tianYuanUserService.selectByUsername("gaoxuzhao");
        Date sendTime = Dates.toDate(LocalDate.of(2018, 3, 20));
        int[] result = tyUserSvcTaskRefreshJob.refreshTaskByTime(user, sendTime, false);
        System.out.println("刷新结果：" + result[0] + "," + result[1]);
    }

    @Test
    public void testRefreshSvcMsg() {
        TianYuanUser user = tianYuanUserService.selectByUsername("gaoxuzhao");
        Date sendTime = Dates.toDate(LocalDate.of(2018, 3, 20));
        int[] result = tyUserSvcTaskRefreshJob.refreshMsgByTime(user, sendTime, false);
        System.out.println("刷新结果：" + result[0] + "," + result[1]);
    }

    @Test
    public void testPostPhoto() {
        File imgFile = new File("D:\\201804090902-liuhan.jpg");
        String rwh = "RW20180410007";
        String svcId = "10";
        ResponseVo vo = tianYuanSvcService.postPhoto(imgFile, rwh, svcId, false);
        if (vo.isSuccess()) {
            System.out.println("上传照片成功.");
        } else {
            System.out.println("上传照片失败." + vo.getMessage());
        }
    }

    @Test
    public void testLikeAndroid() {
        String actionUrl = "http://wd2.tygps.com/TYMICS_Svc/Service.asmx/PostPhoto";
        Map<String, String> params = new HashMap<>(2);
        params.put("photoName", "201804090902-liuhan.jpg");
        params.put("rwh", "RW20180410007");
        File file = new File("D:\\201804090902-liuhan.jpg");
        PushDataForServerHttps9(actionUrl, params, file);
    }

    public static String PushDataForServerHttps9(String actionUrl, Map<String, String> params, File file) {
        String msg = "";
        try {
            String BOUNDARY = java.util.UUID.randomUUID().toString();
            String PREFIX = "--", LINEND = "\r\n";
            String MULTIPART_FROM_DATA = "multipart/form-data";
            String CHARSET = "UTF-8";

            URL url = new URL(actionUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();// 打开连接
            // conn.setInstanceFollowRedirects(true);

            conn.setConnectTimeout(1000 * 1000);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Charset", CHARSET); // 设置编码
            conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);
            // conn.setRequestProperty("Content-Length",
            // String.valueOf(10000000));

            // 首先组拼文本类型的参数
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINEND);
                sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
//                sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
//                sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
                sb.append(LINEND);
                sb.append(entry.getValue());
                sb.append(LINEND);
            }

            DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
            outStream.write(sb.toString().getBytes());
            System.out.println(sb.toString());
            // 发送文件数据
            StringBuilder sb1 = new StringBuilder();
            sb1.append(PREFIX);
            sb1.append(BOUNDARY);
            sb1.append(LINEND);
            sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"" + LINEND);
            sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEND);
            sb1.append(LINEND);
            System.out.println(sb1.toString());

            outStream.write(sb1.toString().getBytes());
            InputStream is = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }

            is.close();
            outStream.write(LINEND.getBytes());
            System.out.println(LINEND);
            // 请求结束标志
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
            outStream.write(end_data);
            outStream.flush();
            System.out.println(new String(end_data));

            /**
             * 获取响应码 200=成功 当响应成功，获取响应的流
             */
            if (conn.getResponseCode() == 200) {
                InputStream input = conn.getInputStream();
                msg = Streams.asString(input);
            }

            System.out.println("000000000000：" + msg);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return msg;
    }

}
