package cn.jmzzz;

import cn.jmzzz.tools.Hello;
import cn.jmzzz.tools.Ini;
import cn.jmzzz.tools.IniFileReaderU;
import cn.jmzzz.tools.R;
import org.dtools.ini.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TimeTask extends Assistant {
    IniFile ini = new BasicIniFile();
    String hito;
    String soc;
    String Hol;
    int i;
    int i2;
    int i3;
    byte hour24;
    byte min;

    public void sendSub() throws IOException {
        hour24 = Byte.parseByte(Ini.read(subtimedir, "Time", "h"));
        min = Byte.parseByte(Ini.read(subtimedir, "Time", "m"));

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, hour24);
        calendar2.set(Calendar.MINUTE, min);
        calendar2.set(Calendar.SECOND, 0);
        Date time2 = calendar2.getTime();
        Timer timer2 = new Timer();
        timer2.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Set<String> set = new HashSet<>();
                IniFileReaderU reader = new IniFileReaderU(ini, new File(f));
                try {
                    hour24 = Byte.parseByte(Ini.read(subtimedir, "Time", "h"));
                    min = Byte.parseByte(Ini.read(subtimedir, "Time", "m"));
                    reader.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                i = 0;
                i2 = 0;
                i3 = 0;
                //获取ini文件的所有Section
                for (int i = 0; i < ini.getNumberOfSections(); i++) {
                    IniSection sec = ini.getSection(i);
                    //获取每个Section的Item
                    for (IniItem item : sec.getItems()) {
                        if (!item.getValue().equals("0") && !sec.getName().equals("Call")) {
                            set.add(item.getName());
                        }
                    }
                }
                CQ.logDebug("QQ", String.valueOf(set.size()));
                for (Object object : set) {
                    Calendar calendar1 = Calendar.getInstance();
                    if (calendar1.get(Calendar.HOUR_OF_DAY) == hour24 && calendar1.get(Calendar.MINUTE) == min) {
                        {
                            try {
                                Thread.sleep(1000);
                                CQ.sendPrivateMsg(Long.parseLong(String.valueOf(object)), Hello.getHello() + getContent(String.valueOf(object)));
                            } catch (IOException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }, time2, 1000 * 60 * 60 * 24);// 这里设定将延时每天固定执行
    }

    private String getContent(String QQ) throws IOException {
        String s = "";
        IniSection section3 = ini.getSection("Call");
        if (section3.hasItem(QQ)) {
            s += Ini.read(f, "Call", QQ) + "！";
        } else s += "Master!";
        s += "\n今天是" + getHoliday() + "哦~";
//        IniSection section4 = ini.getSection("Wea");
//        if (section4.hasItem(QQ)) {
//            String s1 = String.valueOf(section4.getItem(QQ).getValue());
//            if (!s1.equals("0")) {
//                s += "\n\n" + R.getForecastWea(s1);
//            }
//        }
        IniSection section = ini.getSection("Hito");
        if (section.hasItem(QQ)) {
            if (!String.valueOf(section.getItem(QQ).getValue()).equals("0")) {
                s += "\n\n" + getHito();
            }
        }
        IniSection section2 = ini.getSection("Soc");
        if (section2.hasItem(QQ)) {
            if (!String.valueOf(section2.getItem(QQ).getValue()).equals("0")) {
                s += "\n\n" + getSoc();
            }
        }
        return s;
    }

    private String getHito() {
        if (i == 0) {
            hito = R.getHito();
        }
        i++;
        return hito;
    }

    private String getSoc() {
        if (i2 == 0) {
            soc = R.getSocial();
        }
        i2++;
        return soc;
    }

    private String getHoliday() {
        if (i3 == 0) {
            Hol = R.getHoliday();
        }
        i3++;
        return Hol;
    }
}