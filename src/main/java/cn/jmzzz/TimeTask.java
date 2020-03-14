package cn.jmzzz;

import cn.jmzzz.tools.Hello;
import cn.jmzzz.tools.Ini;
import org.dtools.ini.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TimeTask extends Assistant {

    public void sendSub() throws IOException {
        IniFile ini = new BasicIniFile();
        int hour24 = Integer.parseInt(Ini.read(subtimedir, "Time", "h"));
        int min = Integer.parseInt(Ini.read(subtimedir, "Time", "m"));
        IniFileReader reader = new IniFileReader(ini, new File(f));
        reader.read();
        Set<String> set = new HashSet<>();
        //获取ini文件的所有Section
        for (int i = 0; i < ini.getNumberOfSections(); i++) {
            IniSection sec = ini.getSection(i);
            //获取每个Section的Item
            for (IniItem item : sec.getItems()) {
                if (!item.getValue().equals("0")) {
                    set.add(item.getName());
                }
            }
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour24);
        calendar.set(Calendar.MINUTE, min);
        Date time = calendar.getTime();
        for (Object object : set) {
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    Calendar calendar1 = Calendar.getInstance();
                    if (calendar1.get(Calendar.HOUR_OF_DAY) == hour24 && calendar1.get(Calendar.MINUTE) == min) {
                        CQ.sendPrivateMsg(Long.parseLong(String.valueOf(object)), Hello.getHello());
                    }
                }
            }, time, 1000 * 60 * 60 * 24);// 这里设定将延时每天固定执行
        }
    }
}