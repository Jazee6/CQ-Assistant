package cn.jmzzz;

import cn.jmzzz.tools.Hello;
import cn.jmzzz.tools.Ini;
import cn.jmzzz.tools.R;
import org.dtools.ini.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TimeTask extends Assistant {
    IniFile ini = new BasicIniFile();
    String hito;
    String soc;

    public void sendSub() throws IOException {
        byte hour24 = Byte.parseByte(Ini.read(subtimedir, "Time", "h"));
        byte min = Byte.parseByte(Ini.read(subtimedir, "Time", "m"));
        IniFileReader reader = new IniFileReader(ini, new File(f));
        reader.read();
        Set<String> set = new HashSet<>();
        //获取ini文件的所有Section
        for (int i = 0; i < ini.getNumberOfSections(); i++) {
            IniSection sec = ini.getSection(i);
            //获取每个Section的Item
            for (IniItem item : sec.getItems()) {
                if (!item.getValue().equals("0") && !item.getName().equals("get")) {
                    set.add(item.getName());
                }
            }
        }

        byte min2 = (byte) (min - 1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, hour24);
        calendar2.set(Calendar.MINUTE, min2);
        Date time2 = calendar2.getTime();
        Timer timer2 = new Timer();
        timer2.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                try {
                    Ini.write(f, "Hito", "get", "false", true);
                    Ini.write(f, "Soc", "get", "false", true);
                    CQ.logDebug("write", "success");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, time2, 1000 * 60 * 60 * 24);// 这里设定将延时每天固定执行

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour24);
        calendar.set(Calendar.MINUTE, min);
        Date time = calendar.getTime();
        for (Object object : set) {
            CQ.logDebug("QQList", (String) object);
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    Calendar calendar1 = Calendar.getInstance();
                    if (calendar1.get(Calendar.HOUR_OF_DAY) == hour24 && calendar1.get(Calendar.MINUTE) == min) {
                        try {
                            CQ.sendPrivateMsg(Long.parseLong(String.valueOf(object)), Hello.getHello() + getContent(String.valueOf(object)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, time, 1000 * 60 * 60 * 24);// 这里设定将延时每天固定执行
        }
    }

    private String getContent(String QQ) throws IOException {
        String s = "";
        IniSection section = ini.getSection("Hito");
        if (section.hasItem(QQ)) {
            if (!String.valueOf(section.getItem(QQ).getValue()).equals("0")) {
                s += "\n\n" + getHito();
            }
        }
        IniSection section2 = ini.getSection("Soc");
        if (section2.hasItem(QQ)) {
            if (!String.valueOf(section2.getItem(QQ).getValue()).equals("0")) {
                s += "\n\n" + getSoc(Long.parseLong(QQ));
            }
        }
        return s;
    }

    private String getHito() throws IOException {
        if (!Boolean.parseBoolean(Ini.read(f, "Hito", "get"))) {
            hito = R.getHito();
            Ini.write(f, "Hito", "get", "true", true);
        }
        return hito;
    }

    private String getSoc(long QQ) throws IOException {
        if (!Boolean.parseBoolean(Ini.read(f, "Soc", "get"))) {
            soc = R.getSocial(QQ);
            Ini.write(f, "Soc", "get", "true", true);
        }
        return soc;
    }
}