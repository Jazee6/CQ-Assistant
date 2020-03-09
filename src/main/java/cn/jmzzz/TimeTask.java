package cn.jmzzz;

import cn.jmzzz.tools.HttpGet;
import cn.jmzzz.tools.Ini;
import com.alibaba.fastjson.JSONObject;
import org.dtools.ini.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TimeTask extends Assistant {
    IniFile ini = new BasicIniFile(false);//不使用大小写敏感
    //    int second = 0;

    public void sendHito() throws IOException {
        int hour24 = Integer.parseInt(Ini.readCfgValue(subtimedir, "Time", "h", "0"));
        int min = Integer.parseInt(Ini.readCfgValue(subtimedir, "Time", "m", "0"));
        IniFileReader reader = new IniFileReader(ini, new File(f));
        try {
            reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取ini文件的所有Section
        for (int i = 0; i < ini.getNumberOfSections(); i++) {
            IniSection sec = ini.getSection(i);
            //获取每个Section的Item
            JSONObject object = JSONObject.parseObject(HttpGet.doGet("https://v1.hitokoto.cn/"));
            for (IniItem item : sec.getItems()) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hour24);
                calendar.set(Calendar.MINUTE, min);
//                calendar.set(Calendar.SECOND, second);
                Date time = calendar.getTime();
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    public void run() {
                        Calendar calendar1 = Calendar.getInstance();
                        if (calendar1.get(Calendar.HOUR_OF_DAY) == hour24 && calendar1.get(Calendar.MINUTE) == min) {
                            if (Integer.parseInt(getNowTime().substring(0, 2)) <= 11 && Integer.parseInt(getNowTime().substring(0, 2)) >= 4) {
                                CQ.sendPrivateMsg(Long.parseLong(item.getName()), "早上好~Master！\n\n" + object.getString("hitokoto"));
                            }
                            if (Integer.parseInt(getNowTime().substring(0, 2)) < 13 && Integer.parseInt(getNowTime().substring(0, 2)) > 11) {
                                CQ.sendPrivateMsg(Long.parseLong(item.getName()), "中午好~Master！\n\n" + object.getString("hitokoto"));
                            }
                            if (Integer.parseInt(getNowTime().substring(0, 2)) <= 17 && Integer.parseInt(getNowTime().substring(0, 2)) >= 13) {
                                CQ.sendPrivateMsg(Long.parseLong(item.getName()), "下午好~Master！\n\n" + object.getString("hitokoto"));
                            }
                            if (Integer.parseInt(getNowTime().substring(0, 2)) > 17) {
                                CQ.sendPrivateMsg(Long.parseLong(item.getName()), "晚上好~Master！\n\n" + object.getString("hitokoto"));
                            }
                            if (Integer.parseInt(getNowTime().substring(0, 2)) > 0 && Integer.parseInt(getNowTime().substring(0, 2)) < 4) {
                                CQ.sendPrivateMsg(Long.parseLong(item.getName()), "深夜好~Master！\n\n" + object.getString("hitokoto"));
                            }
                        }
                    }
                }, time, 1000 * 60 * 60 * 24);// 这里设定将延时每天固定执行
            }
        }
    }

    public String getNowTime(String dateformat) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);
        return dateFormat.format(now);
    }

    public String getNowTime() {
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(formatter);
    }
}


