package cn.jmzzz.tools;

import cn.jmzzz.PersonRespond;
import com.alibaba.fastjson.JSONObject;

import java.util.Random;

import static com.sobte.cqp.jcq.event.JcqApp.CC;

public class R {
    public static String getRespondString() {
        Random random = new Random();
        int r1 = random.nextInt(2);
        String s = null;
        switch (r1) {
            case 0:
                s = "我在" + CC.face(32);
                break;
            case 1:
                s = "我在这~";
                break;
        }
        return s;
    }

    public static String getAbout() {
        return "关于\n简洁的订阅助理，基于CoolQ的JCQ\n当前版本：" + AppInfo.getAppVer() + "\n本项目在Github开源，访问官网了解更多";
    }

    public static String getFunctionList() {
        return "功能列表\n1.自定义称呼\n2.一言\n3.社会语录\n详细内容请回复项目名称\n\n有新点子或报错请发送反馈+内容哦~";
    }

    public static String getHito() {
        JSONObject object = JSONObject.parseObject(HttpGet.doGet(AppInfo.getHitoApi()));
        return object.getString("hitokoto") + "\n\t\t\t\t——" + object.getString("from");
    }


//    public static String getDwz(String msg, long fromQQ) {
//        if (msg.length() > 10) {
//            JSONObject object = new JSONObject();
//            if (msg.startsWith("http", 3)) {
//                object.put("Url", msg.substring(3));
//            } else {
//                object.put("Url", "http://" + msg.substring(3));
//            }
//            JSONObject object1 = JSONObject.parseObject(HttpPost.post(AppInfo.dwzRequest(), object.toJSONString(), AppInfo.dwzToken()));
//            if (object1.getString("Code").equals("0")) {
//                return object1.getString("ShortUrl");
//            } else {
//                PersonRespond.sendFeedback(object1.getString("Code"), fromQQ, "dwz");
//                return "错误(" + object1.getString("Code") + ")已经反馈回1号基地";
//            }
//        } else return "输入有误，请输入短网址+链接\n（链接长度大于7哦~）";
//    }

    public static String getSocial() {
        JSONObject object = JSONObject.parseObject(HttpGet.doGet(AppInfo.getOioApi()));
        if (Integer.parseInt(object.getString("code")) == 1) {
            return object.getString("msg");
        } else {
            PersonRespond.sendFeedback(object.getString("code"), "social");
            return "错误(" + object.getString("code") + ")已经反馈回1号基地";
        }
    }

    public static String getHoliday() {
        JSONObject object = JSONObject.parseObject(HttpGet.doGet(AppInfo.getHolidayApi()));
        if (Integer.parseInt(object.getString("code")) == 0) {
            return object.getJSONObject("type").getString("name");
        } else {
            PersonRespond.sendFeedback(object.getString("code"), "Holiday");
            return "错误(" + object.getString("code") + ")已经反馈回1号基地";
        }
    }

//    public static String sendWeather(String msg) {
//        if (msg.contains("天气")) {
//            if (msg.indexOf("天气") > 1) {
//
//            } else return "指令有误，请发送";
//        }
//        return null;
//    }

//    public static String getNowWeather(String place) {
//        JSONObject object = JSONObject.parseObject(HttpGet.doGet(AppInfo.getWeaApi() + place));
//        if (object.getString("code").equals("200")) {
//            JSONObject object1 = object.getJSONObject("data");
//            return object1.getString("wea") + "\t↑:" + object1.getString("tem1") + "℃\t↓:" + object1.getString("tem2") + "℃\tAQI:" + object1.getString("air");
//        } else {
//            PersonRespond.sendFeedback(object.getString("code"), "Wea");
//            return "错误(" + object.getString("code") + ")已经反馈回1号基地";
//        }
//    }

//    public static String getForecastWea(String city) {
//        JSONObject object = JSONObject.parseObject(HttpGet.doGet(AppInfo.getWeaForecastApi() + city));
//        JSONArray array = JSONArray.parseArray(object.getJSONObject("data").getString("data"));
//        JSONObject object1 = JSONObject.parseObject(String.valueOf(array.get(0)));
//        return object1.getString("wea") + "↑" + object1.getString("tem1") + "↓" + object1.getString("tem2") + "AQI:" + object1.getString("air");
//    }
}