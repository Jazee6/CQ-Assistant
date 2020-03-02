package cn.jmzzz;

import com.alibaba.fastjson.JSONObject;
import org.dtools.ini.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TimeTask extends Assistant {
    String f = CQ.getAppDirectory() + "Sub.ini";

    IniFile ini = new BasicIniFile(false);//不使用大小写敏感

    public void readContent() {
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
                calendar.set(Calendar.HOUR_OF_DAY, 8);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                Date time = calendar.getTime();
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    public void run() {
                        CQ.sendPrivateMsg(Long.parseLong(item.getName()), object.getString("hitokoto"));
                    }
                }, time, 1000 * 60 * 60 * 24);// 这里设定将延时每天固定执行
            }
        }
    }
}


