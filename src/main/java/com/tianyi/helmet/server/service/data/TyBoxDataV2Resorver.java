package com.tianyi.helmet.server.service.data;

import com.tianyi.helmet.server.entity.data.AbstractGpsData;
import com.tianyi.helmet.server.entity.data.GpsActionData;
import com.tianyi.helmet.server.entity.data.GpsData;
import com.tianyi.helmet.server.entity.data.GpsLineData;
import com.tianyi.helmet.server.entity.data.gpsenum.GpsDataTypeItemEnum;
import com.tianyi.helmet.server.util.Commons;
import com.tianyi.helmet.server.util.EncoderUtil;
import com.tianyi.helmet.server.vo.MainDetailVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 车载gps数据解析组件-版本2 CT100D
 * <p>
 * Created by liuhanc on 2018/05/21.
 */
@Component
public class TyBoxDataV2Resorver {
    private Logger logger = LoggerFactory.getLogger(TyBoxDataV2Resorver.class);


    private String doLineCut(String line, AtomicInteger startPos, int partLength, String partName) {
        int startIdx = startPos.get();
        int endIdx = startIdx + partLength;
        String part = Commons.subString(line, startPos.get(), endIdx);//2个长度1个字节
        if (part == null) {
            part = "长度不足无法拆分.总长度=" + line.length();
        }
        System.out.println(partName + ",startIdx=" + startIdx + ",partLength=" + partLength + ",endIdx=" + endIdx + ".hex数据=" + part);
        startPos.set(endIdx);
        return part;
    }

    //执行校验 校验码之前（标识位除外）信息内容相加之和，取低字节
    private boolean doDataVerify(String verifyDataPart, String verifyCodePart) {
        int dataLength = verifyDataPart.length();
        if (dataLength % 2 == 1) {
            logger.error("车载数据有误.计算校验码时数据长度不是偶数.length=" + dataLength);
            return false;
        }

        int size = dataLength / 2;
        long sum = 0l;
        for (int i = 0; i < size; i++) {
            //逐个字节的取出数据
            String oneByte = Commons.subString(verifyDataPart, i * 2, (i + 1) * 2);
            long part = Long.parseLong(oneByte, 16);
            sum += part;
        }

        String hex = Long.toHexString(sum);
        String lowerByteHex = hex.substring(hex.length() - 2);//取低字节
        if (!verifyCodePart.equalsIgnoreCase(lowerByteHex)) {
            logger.error("车载数据有误.校验码验证失败.计算得到=" + hex + ",低字节=" + lowerByteHex + ",传入=" + verifyCodePart);
            return false;
        }
        return true;
    }

    public GpsLineData resorveLineData(String line) {
        if (StringUtils.isEmpty(line))
            return null;

        AtomicInteger startPos = new AtomicInteger(0);
        String startFlagPart = doLineCut(line, startPos, 2, "开始标志");//2个长度1个字节
        if (!startFlagPart.equalsIgnoreCase("7E")) {
            logger.error("车载数据有误.不以7E开头,无效." + line);
            return null;
        }

        int startEndByteLength = 4;//报文首尾常规字节长度，4分别表示开始标志、结束标志、校验码、结束符 ，各自占用1个字节
        int lineLength = line.length();
        System.out.println("数据总长度=" + lineLength);
        String verifyDataPart = doLineCut(line, startPos, lineLength - startEndByteLength * 2, "校验数据部分");
        System.out.println("校验数据总长度=" + verifyDataPart.length());
        String verifyCodePart = doLineCut(line, startPos, 2, "校验码");//校验码 2个长度1个字节
        String endPart = doLineCut(line, startPos, 2, "结束位");//结束符 2个长度1个字节
        String endFlagPart = doLineCut(line, startPos, 2, "结束标志");//结束标志符 2个长度1个字节
        if (!endFlagPart.equalsIgnoreCase("7E")) {
            logger.error("车载数据有误.不以7E结尾,无效." + line);
            return null;
        }

//        boolean verifySuccess = doDataVerify(verifyDataPart,verifyCodePart);
//        if(!verifySuccess){
//            return null;
//        }

        //重新开始
        AtomicInteger dataPos = new AtomicInteger(0);
        String imeiLengthPart = doLineCut(verifyDataPart, dataPos, 2, "imei长度");//2个长度1个字节
        int imeiLength = Integer.parseInt(imeiLengthPart, 16);
        if (imeiLength % 2 == 1) imeiLength += 1;//IMEI号码数字个数为奇数个时补'F'凑成整数字节
        System.out.println("imei字符串的长度(10进制)=" + imeiLength);
        String imeiPart = doLineCut(verifyDataPart, dataPos, imeiLength, "imei");// 一般是16个长度8个字节
        String typePart = doLineCut(verifyDataPart, dataPos, 2, "消息类型");
        String versionPart = doLineCut(verifyDataPart, dataPos, 6, "版本");//6个长度3个字节
        String propertyPart = doLineCut(verifyDataPart, dataPos, 4, "属性");//4个长度2个字节 属性
        String timePart = doLineCut(verifyDataPart, dataPos, 12, "时间");//12个长度6个字节
        String dataLengthPart = doLineCut(verifyDataPart, dataPos, 4, "消息体长度");//4个长度2个字节
        int dataLength = Integer.parseInt(dataLengthPart, 16) / 8 * 2;
        System.out.println("消息体字符串的长度(10进制)=" + dataLength);
        String dataPart = doLineCut(verifyDataPart, dataPos, dataLength, "消息体");//

        //返回初步解析结果
        GpsLineData lineData = new GpsLineData();
        lineData.setImei(imeiPart.substring(0, 15));
        lineData.setByteLength(dataLength);
        LocalDateTime baseTime = calcuteTime(timePart);
        lineData.setBaseTime(baseTime);
        lineData.setDataPart(dataPart);

        return lineData;
    }

    /**
     * 解析gps数据
     *
     * @param line
     */
    public MainDetailVo<GpsLineData, AbstractGpsData> resorveGpsData(String line, String clientId) {
        MainDetailVo<GpsLineData, AbstractGpsData> md = new MainDetailVo();

        GpsLineData lineData = resorveLineData(line);
        if (lineData == null)
            return md;

        LocalDateTime baseTime = lineData.getBaseTime();
        lineData.setRealBaseTime(baseTime);
        String dataPart = lineData.getDataPart();
        List<AbstractGpsData> list = new ArrayList<>();

        AtomicInteger dataPos = new AtomicInteger(0);
        AtomicInteger dataCount = new AtomicInteger(0);
        StringBuffer dataParts = new StringBuffer();

        String countPart = doLineCut(dataPart, dataPos, 2, "PGN个数");
        int pgnCount = Integer.parseInt(countPart, 16);
        int onePgnDataLength = 14 * 2;//每个PGN由14个字节数据组成
        int onePgnDataContentLength = 8 * 2;//每个PGN的数据内容由8个字节(byte7-byte14)数据组成
        String onePgnDataContentLengthHex = "10";//16的16进制
        int pgnDataLength = onePgnDataLength * pgnCount;
        String pngDataPart = doLineCut(dataPart, dataPos, pgnDataLength, "PGN数据");
        System.out.println("pgn数据量=" + pgnCount + ",pgn数据长度=" + pgnDataLength);

        for (int i = 0; i < pgnCount; i++) {
            String onePgnData = pngDataPart.substring(i * onePgnDataLength, (i + 1) * onePgnDataLength);
            AtomicInteger onePgnDataPos = new AtomicInteger(0);
            String relateTimePart = doLineCut(onePgnData, onePgnDataPos, 4, "PGN采集时间-"+i);
            int relateTime = Integer.parseInt(relateTimePart, 16);//毫秒

            String srcIdPart = doLineCut(onePgnData, onePgnDataPos, 8, "PGN采集来源和ID-"+i);//4个字节
            String byte1 = srcIdPart.substring(0, 2);//第1个字节
            byte[] byte1Bytes = EncoderUtil.hexDecode(byte1);//第1个字节,应该长度=1
            String byte1Bits = EncoderUtil.byteToBits(byte1Bytes[0]);
            String srcBits = byte1Bits.substring(0, 3);//占第一字节的bit7-bit5：0-CAN0；1-CAN1；2-CAN2；3-CAN3；7-终端自身；其它-暂未定义
            String leftBits = "000" + byte1Bits.substring(3);
            byte leftByte1 = EncoderUtil.bitsToByte(leftBits);
            String newByte1 = EncoderUtil.hexEncode(new byte[]{leftByte1});//去掉高位的来源数据后剩余的第1字节的16进制表示
            String newIdPart = newByte1 + srcIdPart.substring(2);
            System.out.println("第1字节="+byte1+",第1字节toBits="+byte1Bits+",参数来源Bits(bit7-5)="+srcBits+",剩余bits(bit4-0)="+leftBits+",补齐后的第1字节="+newByte1+",ID(剩余29字节高位补齐)="+newIdPart);
            String contentPart = doLineCut(onePgnData, onePgnDataPos, onePgnDataContentLength, "PGN内容-"+i);

            List<AbstractGpsData> gpsDataList = createGpsDataList(contentPart, newIdPart);
            if (gpsDataList != null && gpsDataList.size() > 0) {
                gpsDataList.stream().forEach(gpsData -> {
                    gpsData.setClientId(clientId);
                    gpsData.setCreateTime(baseTime.plusNanos(1_000_000l * relateTime));//相对时间单位是毫秒
                    gpsData.setImei(lineData.getImei());//imei最后1个字符F忽略
                    gpsData.setDataFullPart(new String[]{newIdPart, relateTimePart, onePgnDataContentLengthHex, contentPart});
                    dataParts.append(dataCount.incrementAndGet()).append("-").append(Arrays.toString(gpsData.getDataFullPart()));
                    list.add(gpsData);
                });
            } else {
                logger.error("车载数据有误,无法解析出具体数据." + contentPart + ",pgnId=" + newIdPart);
            }
        }

        lineData.setDataCount(dataCount.get());
        lineData.setDataParts(dataParts.toString());
        md.setMain(lineData);
        md.setList(list);

        System.out.println("解析完毕-----------------");
        System.out.println("总数据量=" + list.size());
        System.out.println("============================================================================");
        int count = 0;
        for (AbstractGpsData gpsData : list) {
            count++;
            System.out.println(count + ":" + gpsData.toString());
        }
        System.out.println("===========================================================================");

        return md;
    }

    /**
     * 构造1个gps数据对象
     * ID	参数名称	字节位置	分辨率	偏移量	单位	说明	推土机	备注
     * 0x21F	水温	BYTE5-BYTE6	1/128	0	℃	BYTE2=0x01时，取值,有符号数		01 02 FF 1B 温度
     * 机油压力	BYTE8的bit7	1	0		BYTE2=0x03时，取值
     * 燃料瞬间消耗	BYTE5-BYTE6	1/64	0	L/H	BYTE2=0x02时，取值	是
     * 0x11F	转速	BYTE1-BYTE2	1/8	0			是
     * 扭矩百分比	BYTE6	1/2	0	%		是
     * 扭矩	BYTE4-BYTE5	0.0255	0			是
     * 0x13F	F泵泵压	BYTE1	2	0	Kg/cm2	"当压力超过了溢流压力34.8MPa{355kg/cm2}时，产生1级溢流
     * 如果2级溢流功能被打开，溢流压力会升高到大约37.2MPa{380kg/cm2}"	是
     * R泵泵压	BYTE2	2	0	Kg/cm2		是
     * 0x23F	F泵PC-EPC电流	BYTE3-BYTE5	1	0	mA	BYTE2=0x05时，取值
     * R泵PC-EPC电流	BYTE6-BYTE8	1	0	mA	BYTE2=0x05时，取值
     * 动作	BYTE8的bit2	——	——	——	BYTE2=0x01时，取值，0:有动作，1：无动作
     * 动作类型	BYTE4	——	——	——	"BYTE2=0x02时，取值：
     * bit0=1,动臂上升
     * bit1=1,动臂下降
     * bit2=1,斗杆回收
     * bit3=1,斗杆伸出
     * bit4=1,铲斗挖掘
     * bit5=1,铲斗卸载
     * bit6=1,回转
     * bit7=1,行走"
     * BYTE3	——	——	——	"BYTE2=0x02时，取值：
     * bit2=0,左右行走同时动作
     * bit2=1,左右行走其中一个动作"
     * 0x121	工作模式	BYTE7	——	——	——	"P模式:11
     * E模式:33
     * L模式:34
     * B模式:11"
     * 0x20F	E模式调整	BYTE3高4位	——	——	——	"BYTE2=0x01时，
     * E0:0x
     * E1:1x
     * E2:2x
     * E3:3x"
     *
     * @param dataDataPart 第7-14字节中的内容，共8个字节
     * @param pgnId
     * @return
     */
    public List<AbstractGpsData> createGpsDataList(String dataDataPart, String pgnId) {
        List<AbstractGpsData> gpsDataList = new ArrayList<>();
        if ("0000021F".equalsIgnoreCase(pgnId)) {
            //21F
            String byte2Part = dataDataPart.substring(1 * 2, 2 * 2);
            int byte2 = Integer.parseInt(byte2Part, 16);
            /**
             * 水温 BYTE2=0x01时，取值,有符号数
             机油压力 BYTE2=0x03时，取值
             燃料瞬间消耗 BYTE2=0x02时，取值
             */
            if (byte2 == 1) {
                String byte56Part = dataDataPart.substring(4 * 2, 6 * 2);
                gpsDataList.add(resorveIntegerData(byte56Part, GpsDataTypeItemEnum.WATER_TEMPERATURE.getId(), true));
            } else if (byte2 == 3) {
                String byte8 = dataDataPart.substring(7 * 2);
                byte[] bytes8 = EncoderUtil.hexDecode(byte8);
                byte byte8Byte = bytes8[0];
                String byte8Bits = EncoderUtil.byteToBits(byte8Byte);
                String bit7 = byte8Bits.substring(0, 1);//第8个字节的第7bit
                gpsDataList.add(resorveIntegerData(bit7, GpsDataTypeItemEnum.OIL_PRESSURE.getId(), true));
            } else if (byte2 == 2) {
                String byte56Part = dataDataPart.substring(4 * 2, 6 * 2);
                gpsDataList.add(resorveIntegerData(byte56Part, GpsDataTypeItemEnum.FUEL_CONSUMPTION.getId(), true));
            }
        } else if ("0000011F".equalsIgnoreCase(pgnId)){
            /**
             * 转速
             扭矩百分比
             扭矩
             */
            String byte12 = dataDataPart.substring(0 * 2, 2 * 2);
            gpsDataList.add(resorveIntegerData(byte12, GpsDataTypeItemEnum.REVOLUTION_SPEED.getId(), true));
            String byte6 = dataDataPart.substring(5 * 2, 6 * 2);
            gpsDataList.add(resorveIntegerData(byte6, GpsDataTypeItemEnum.TORQUE_PERCENT.getId(), true));
            String byte45 = dataDataPart.substring(3 * 2, 5 * 2);
            gpsDataList.add(resorveIntegerData(byte45, GpsDataTypeItemEnum.TORQUE.getId(), true));
        } else if ("0000013F".equalsIgnoreCase(pgnId)) {
            // 13F
            /**
             * F泵泵压
             R泵泵压
             */
            String byte1 = dataDataPart.substring(0 * 2, 1 * 2);
            gpsDataList.add(resorveIntegerData(byte1, GpsDataTypeItemEnum.PUMP_PRESSURE_F.getId(), true));
            String byte2 = dataDataPart.substring(1 * 2, 2 * 2);
            gpsDataList.add(resorveIntegerData(byte2, GpsDataTypeItemEnum.PUMP_PRESSURE_R.getId(), true));
        } else if ("0000023F".equalsIgnoreCase(pgnId)) {
            // 23F
            /**
             * F泵PC-EPC电流
             R泵PC-EPC电流
             动作
             动作类型
             */
            String byte2Part = dataDataPart.substring(1 * 2, 2 * 2);
            int byte2 = Integer.parseInt(byte2Part, 16);
            if (byte2 == 5) {
                String byte35Part = dataDataPart.substring(2 * 2, 5 * 2);
                gpsDataList.add(resorveIntegerData(byte35Part, GpsDataTypeItemEnum.ELECTRIC_CURRENT_F.getId(), true));
                String byte68Part = dataDataPart.substring(5 * 2);
                gpsDataList.add(resorveIntegerData(byte68Part, GpsDataTypeItemEnum.ELECTRIC_CURRENT_R.getId(), true));
            } else if (byte2 == 1) {
                String byte8 = dataDataPart.substring(7 * 2);
                byte[] bytes8 = EncoderUtil.hexDecode(byte8);
                byte byte8Byte = bytes8[0];
                String byte8Bits = EncoderUtil.byteToBits(byte8Byte);
                String bit2 = byte8Bits.substring(5, 6);//第8个字节的第2bit
                String action = bit2;
                gpsDataList.add(resorveActionData(action, "", ""));
            } else if (byte2 == 2) {
                String actionVal = dataDataPart.substring(3 * 2, 4 * 2);//byte4
                String walk = dataDataPart.substring(2 * 2, 3 * 2);//byte3
                gpsDataList.add(resorveActionData("", actionVal, walk));
            }
        } else if ("00000121".equalsIgnoreCase(pgnId)) {
            // 121 工作模式
            String byte7 = dataDataPart.substring(6 * 2, 7 * 2);
            gpsDataList.add(resorveIntegerData(byte7, GpsDataTypeItemEnum.WORK_MODE.getId(), false));
        } else if ("0000020F".equalsIgnoreCase(pgnId)) {
            // 20F E模式调整
            String byte2Part = dataDataPart.substring(1 * 2, 2 * 2);
            int byte2 = Integer.parseInt(byte2Part, 16);
            if (byte2 == 1) {
                String byte3 = dataDataPart.substring(2 * 2, 3 * 2);
                byte[] bytes3 = EncoderUtil.hexDecode(byte3);
                byte byte3Byte = bytes3[0];
                String byte3Bits = EncoderUtil.byteToBits(byte3Byte);
                String emode = byte3Bits.substring(0, 4);//高4位bit
                gpsDataList.add(resorveIntegerData(emode, GpsDataTypeItemEnum.MODE_E.getId(), false));
            }
        } else if("18FF244A".equalsIgnoreCase(pgnId)){
            //18FF244A gps定位信息

        } else if("18FF994A".equalsIgnoreCase(pgnId)){
            //18FF994A IMU陀螺仪信息

        }

        return gpsDataList;
    }

    /**
     * 解析地理定位数据
     * 第0个字节的最高位(bit7，左数第1位)表示纬度是北纬还是南纬
     * 字节0-3表示纬度具体值
     * 第4个字节的最高位(bit7，左数第1位)表示经度是东经还是西经
     * 字节4-7表示经度具体值
     * 字节8,9表示速度
     * 字节10,11表示方向
     * 字节12的最高位(bit7，左数第1位)表示gps位置的新旧标志
     * 字节12的次高位(bit6，左数第2位)表示海拔正负
     * 字节12的剩余6位+字节13合计表示海拔具体值
     * 字节14表示星星数量
     * 字节15-20折6位表示gps时间。每个字节表示1个时间，转成10进制后分别是年月日时分秒
     *
     * @param dataDataPart 共21个字节，长度=42
     * @return
     */
//    public GpsLocationData resorveLocationData(String dataDataPart) {
//        if (dataDataPart.length() != 42) {
//            logger.error("地理定位数据长度不是42个字符21个字节." + dataDataPart);
//            return null;
//        }
//
//        String latPart = dataDataPart.substring(0, 8);//4个字节 纬度
//        String lonPart = dataDataPart.substring(8, 16);//4个字节 经度
//        String speedPart = dataDataPart.substring(16, 20);//2个字节 速度
//
//        String orientPart = dataDataPart.substring(20, 24);//2个字节 方向
//        String gpsAltPart = dataDataPart.substring(24, 26);//1个字节 gps位置的新旧标志、海拔正负
//        String altPart = dataDataPart.substring(26, 28);//1个字节 海拔具体值
//
//        String starPart = dataDataPart.substring(28, 30);//1个字节 星星数量
//        String timePart = dataDataPart.substring(30, 42);//6个字节 gps时间
//
//
//        int[] lat = calcuteLatLong(latPart);
//        int[] lon = calcuteLatLong(lonPart);
//        String gpsALtBits = EncoderUtil.byteToBits(EncoderUtil.hexDecode(gpsAltPart)[0]);
//        GpsLocationData gld = new GpsLocationData();
//        gld.setLatns(lat[0]);
//        gld.setLat(calcuteLatLong(lat[1]));
//        gld.setLonew(lon[0]);
//        gld.setLon(calcuteLatLong(lon[1]));
//        gld.setSpeed(Integer.parseInt(speedPart, 16));//速度
//        gld.setOrient(Integer.parseInt(orientPart, 16));//方向
//        int oldNew = Integer.parseInt(gpsALtBits.substring(0, 1));
//        gld.setOldnew(oldNew);//第7个bit表示gps位置的新旧   0-旧值，1-新值
//        gld.setAltPosNeg(Integer.parseInt(gpsALtBits.substring(1, 2)));//第6个bit表示海拔数值的正负  0-正值，1-负值
//        String altFullPart = "00" + gpsALtBits.substring(2) + altPart;
//        gld.setAlt(Integer.parseInt(altFullPart, 16));//海拔
//        gld.setStar(Integer.parseInt(starPart, 16));//星数
//        if (oldNew == 0) {
//            //0表示gps无效,此时时间值是错误的
//            gld.setGpsTime(LocalDateTime.of(2000, 1, 1, 1, 1, 1));
//        } else {
//            gld.setGpsTime(calcuteTime(timePart));
//        }
//        return gld;
//    }

    /**
     * 根据4个字节的数据计算经纬度的标志和具体值
     * 第1个字节的最高位表示东西经还是南北纬标志
     * 第1个字节的最高位替换为0后组成的8个字符(4个字节)表示具体的经纬度的值。
     *
     * @param byte4String
     * @return
     */
//    public int[] calcuteLatLong(String byte4String) {
//        String byte1Str = byte4String.substring(0, 2);//取第1个字节
//        byte byte1 = EncoderUtil.hexDecode(byte1Str)[0];//第1个字节
//        String latBits = EncoderUtil.byteToBits(byte1);//转成bit
//        int flag = Integer.parseInt(latBits.substring(0, 1));//最高位(第7bit)作为标志
//        byte byte1New = EncoderUtil.bitsToByte("0" + latBits.substring(1));//更新第1个字节的最高位为0
//        String byte1StrNew = EncoderUtil.hexEncode(new byte[]{byte1New});//得到第1个字节
//        String byte4StringNew = byte1StrNew + byte4String.substring(2);//得到4个字节的新值
//        int val = Integer.parseInt(byte4StringNew, 16);//得到新值对应的经纬度的具体值
//        return new int[]{flag, val};
//    }

    /**
     * 将经纬度的值转换成a.b的格式。其中a表示度，b表示0.b度。
     * <p>
     * 经纬度值的解析步骤是：
     * 0 将这个具体的16进制的经纬度的值转成10进制；
     * 1 将这个10进制数/10000，得到一个余数mmmm，得到1个商数dd60ff；
     * 2 将步骤1的商数dd60ff/60,得到1个余数ff，得到1个商数dd；
     * 3 步骤2的商数dd表示经度纬度的度值；
     * 4 步骤1的余数mmm和步骤2的余数ff组合最终的分值f = ff + 0.0001 * mmmm；
     * 5 最终的经度纬度值=dd+(f/60)；
     *
     * @param fullNumber
     * @return
     */
//    public float calcuteLatLong(int fullNumber) {
//        int mmmm = fullNumber % 10000;//余数
//        int dd60ff = fullNumber / 10000;//商数
//        int dd = dd60ff / 60;//商数
//        int ff = dd60ff % 60;//余数
//        double f = ff + 0.0001 * mmmm;
//        double d2 = f / 60;
//        return (float) (dd + d2);
//    }


    /**
     * 计算时间数据
     *
     * @param timePart
     * @return
     */
    private LocalDateTime calcuteTime(String timePart) {
        int year = Integer.parseInt(timePart.substring(0, 2), 16);
        int month = Integer.parseInt(timePart.substring(2, 4), 16);
        int day = Integer.parseInt(timePart.substring(4, 6), 16);
        int hour = Integer.parseInt(timePart.substring(6, 8), 16);
        int min = Integer.parseInt(timePart.substring(8, 10), 16);
        int sec = Integer.parseInt(timePart.substring(10, 12), 16);
        LocalDateTime baseTime = LocalDateTime.of(2000 + year, month, day, hour, min, sec);
        return baseTime;
    }

    /**
     * 解析陀螺仪数据
     * 参数名称		字节数
     * 前后姿态角		2
     * 左右姿态角		2
     * 旋转角度		4
     * 旋转最大角速度		2
     * 旋转平均角速度		2
     * 减速时角加速度		4
     * 加速时角加速度		4
     * 回转时间		1
     *
     * @param dataDataPart 共21个字节，长度=42
     * @return
     */
//    public GpsGyroData resorveGyroData(String dataDataPart) {
//        if (dataDataPart.length() != 42) {
//            logger.error("陀螺仪数据长度不是42个字符21个字节." + dataDataPart);
//            return null;
//        }
//        String fb = dataDataPart.substring(0, 4);
//        String lr = dataDataPart.substring(4, 8);
//        String rt = dataDataPart.substring(8, 16);
//        String rt_ms = dataDataPart.substring(16, 20);
//        String rt_as = dataDataPart.substring(20, 24);
//        String da = dataDataPart.substring(24, 32);
//        String ua = dataDataPart.substring(32, 40);
//        String bt = dataDataPart.substring(40, 42);
//
//        GpsGyroData ggd = new GpsGyroData();
//        ggd.setFrontBack(Integer.parseInt(fb, 16));
//        ggd.setLeftRight(Integer.parseInt(lr, 16));
//        ggd.setRotate(Integer.parseInt(rt, 16));
//        ggd.setRotateMaxSpeed(Integer.parseInt(rt_ms, 16));
//        ggd.setRotateAvgSpeed(Integer.parseInt(rt_as, 16));
//        ggd.setDownAcceleration(Long.parseLong(da, 16));
//        ggd.setUpAcceleration(Long.parseLong(ua, 16));
//        ggd.setBackTime(Integer.parseInt(bt, 16));
//        return ggd;
//    }

    /**
     * 解析动作数据
     * 共3个字节，长度=6
     * <p>
     * 动作数据分3个字节。
     * 字节1：动作指示
     * 字节2：动作类型 按位解析
     * bit0=1,动臂上升
     * bit1=1,动臂下降
     * bit2=1,斗杆回收
     * bit3=1,斗杆伸出
     * bit4=1,铲斗挖掘
     * bit5=1,铲斗卸载
     * bit6=1,回转
     * bit7=1,行走
     * 字节3：行走类型
     *
     * @return
     */
    public GpsActionData resorveActionData(String action, String actionVal, String walk) {
        GpsActionData gad = new GpsActionData();
        if (!StringUtils.isEmpty(action)) {
            gad.setAction(Integer.parseInt(action, 16));//动作指示
        } else {
            gad.setAction(-99);//没有数据
        }
        if (!StringUtils.isEmpty(actionVal)) {
            byte actionValByte = EncoderUtil.hexDecode(actionVal)[0];
            String actionValBits = EncoderUtil.byteToBits(actionValByte);
            gad.setActionVal(actionValBits);//动作类型 8个字节，每1位表示1个动作的状态
        } else {
            gad.setActionVal("");//无数据
        }
        if (!StringUtils.isEmpty(walk)) {
            gad.setWalk(Integer.parseInt(walk, 16));//行走类型
        } else {
            gad.setWalk(-99);//没有数据
        }
        return gad;
    }

    /**
     * 解析基本的简单单一数据的传感器数据
     *
     * @param dataDataPart
     * @param dataId
     * @return
     */
    public GpsData resorveIntegerData(String dataDataPart, int dataId, boolean hexMode) {
        GpsData gd = new GpsData();
        gd.setDataType(dataId);
        if (hexMode) {//16进制的进行转换
            int intVal = Integer.parseInt(dataDataPart, 16);
            if ((dataId == 10 || dataId == 11) && intVal > 1200) {
                //电流,值超过1200就是有问题的,处理掉
                intVal = 1200;//暂时写成最大值
            }
            gd.setVal(intVal);
        } else {
            //工作模式\E模式 的值不转成10进制
            gd.setVal(Integer.parseInt(dataDataPart));
        }
        return gd;
    }

}
