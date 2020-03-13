package cn.jmzzz;

import cn.jmzzz.tools.Hello;
import cn.jmzzz.tools.HttpGet;
import cn.jmzzz.tools.Ini;
import com.alibaba.fastjson.JSONObject;
import org.dtools.ini.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TimeTask extends Assistant {
    IniFile ini = new BasicIniFile(false);//不使用大小写敏感
    //    int second = 0;

    public void sendHito() throws IOException {
        int hour24 = Integer.parseInt(Ini.read(subtimedir, "Time", "h"));
        int min = Integer.parseInt(Ini.read(subtimedir, "Time", "m"));
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
                            if (!item.getValue().equals("0")) {
                                CQ.sendPrivateMsg(Long.parseLong(item.getName()), Hello.getHello() + "\n\n" + object.getString("hitokoto") + "\n\t\t\t\t——" + object.getString("from"));
                            }
                        }
                    }
                }, time, 1000 * 60 * 60 * 24);// 这里设定将延时每天固定执行
            }
        }
    }

//    public String getNowTime(String dateformat) {
//        Date now = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);
//        return dateFormat.format(now);
//    }
}


