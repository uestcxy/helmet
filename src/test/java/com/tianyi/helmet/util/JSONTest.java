package com.tianyi.helmet.util;

/**
 * Created by liuhanc on 2017/11/3.
 */
public class JSONTest {

    public static void main(String[] a){
        String ss = "1522895341491";
        Long ll = Long.parseLong(ss);
        System.out.println("long = "+ll);
        System.out.println(ll > Integer.MAX_VALUE);
//        System.out.println(Integer.parseInt(ss));
//        System.out.println(new Date(1522895341491l));
//        String json="{\n" +
//                "\"id\":\"helmet1001\",\n" +
//                "\"time\":1523714233107,\n" +
//                "\"xa\":0.000000,\n" +
//                "\"ya\":0.000000,\n" +
//                "\"za\":0.000000,\n" +
//                "\"xg\":0.000000,\n" +
//                "\"yg\":0.000000,\n" +
//                "\"zg\":0.000000,\n" +
//                "\"battery\":86,\n" +
//                "\"runtime\":1522895341490 \n" +
//                "}";
//        JSONObject jo = JSON.parseObject(json);
//        Integer ii = jo.getInteger("concert");
//        System.out.println("ii=="+ii);


//        System.out.println(SvcAudioTypeEnum.valueOf("afasdf"));
//        SvcVideo vi = new SvcVideo();
//        System.out.println(JSON.toJSONString(vi));

//        JSONObject jo = JSON.parseObject("{\n" +
//                "\"id\":\"helmet1002\",\n" +
//                "\"time\":1509686738053,\n" +
//                "\"lat\":0.000000,\n" +
//                "\"lon\":0.000000,\n" +
//                "\"concert\":0,\n" +
//                "\"relax\":0,\n" +
//                "\"xa\":-75.000000,\n" +
//                "\"ya\":-24.000000,\n" +
//                "\"za\":0.000000,\n" +
//                "\"xg\":36.000000,\n" +
//                "\"yg\":-10980.000000,\n" +
//                "\"zg\":-13748.000000,\n" +
//                "\"battery\":'1%',\n" +
//                "\"runtime\":'00:44:46' \n" +
//                "}");
//        System.out.println(jo);
    }
}
