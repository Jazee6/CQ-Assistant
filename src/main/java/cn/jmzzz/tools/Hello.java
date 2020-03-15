package cn.jmzzz.tools;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Hello {
    public static String getHello() {
        if (Integer.parseInt(getNowTime().substring(0, 2)) < 11 && Integer.parseInt(getNowTime().substring(0, 2)) >= 4) {
            return "早上好~Master！";
        } else if (Integer.parseInt(getNowTime().substring(0, 2)) < 13 && Integer.parseInt(getNowTime().substring(0, 2)) >= 11) {
            return "中午好~Master！";
        } else if (Integer.parseInt(getNowTime().substring(0, 2)) <= 17 && Integer.parseInt(getNowTime().substring(0, 2)) >= 13) {
            return "下午好~Master！";
        } else if (Integer.parseInt(getNowTime().substring(0, 2)) > 17) {
            return "晚上好~Master！";
        } else return "深夜好~Master！";
    }

    public static String getNowTime() {
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(formatter);
    }
}
