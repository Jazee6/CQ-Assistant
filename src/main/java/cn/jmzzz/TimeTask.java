package cn.jmzzz;

import com.alibaba.fastjson.JSONObject;

import java.util.*;

public class TimeTask extends Assistant {
//    String f = CQ.getAppDirectory() + "Sub.ini";

    public void sendSubHitokoto() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date time = calendar.getTime();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                JSONObject object = JSONObject.parseObject(HttpGet.doGet("https://v1.hitokoto.cn/"));
                CQ.sendPrivateMsg(2609059914L, object.getString("hitokoto"));
//                for (String s : set) {
//                    Data.readCfgValue(f,"Hito",);
//                }
            }
        }, time, 1000 * 60 * 60 * 24);// 这里设定将延时每天固定执行
    }
}


