package cn.jmzzz;

import cn.jmzzz.tools.AppInfo;
import cn.jmzzz.tools.Ini;
import cn.jmzzz.tools.IniFileReaderU;
import cn.jmzzz.tools.IniFileWriterU;
import org.dtools.ini.BasicIniFile;
import org.dtools.ini.IniFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class GE extends Assistant {
    File fileg;
    File filed;

    public void sendGE(long fromGroup, long fromQQ) throws IOException {
        if (fileg == null) {
            fileg = new File(g);
        }
        Ini inig = new Ini(fileg, fromGroup + "", "open");
        if (inig.nothasSec()) {
            inig.writeSec();
            inig.write("1");
        }

        if (!inig.read().equals("0")) {
            if (filed == null) {
                filed = new File(d);
            }
            Ini money = new Ini(filed, fromQQ + "", "mon");
            Ini exp = new Ini(filed, fromQQ + "", "exp");
            if (money.nothasSec()) {
                money.writeSec();
            }
            Date day = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            int timenow = Integer.parseInt(df.format(day).substring(2, 8));
            Ini initime = new Ini(fileg, fromGroup + "", fromQQ + "");
            String lasttime = initime.read();
            if (lasttime.equals("0")) {
                initime.write("00000000000000");
                lasttime = "00000000000000";
            }
            if (timenow > Integer.parseInt(lasttime.substring(2, 8))) {
                Random random = new Random();
                int getexp = random.nextInt(6) + 5;
                int getmoney = random.nextInt(51) + 100;

                initime.write(df.format(day));
                String expnow = exp.read();
                String monnow = money.read();
                int exp1 = Integer.parseInt(expnow) + getexp;
                money.writeint(Integer.parseInt(monnow) + getmoney);
                exp.writeint(exp1);

                CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + "欢迎入群~\n" + "EXP:" + expnow + "(+" + getexp + ")" + "\nZ币:" + monnow + "(+" + getmoney + ")\n等级：" + getLevel(exp1));
            }
        }

    }

    public static void openGE(String msg, long fromGroup, long fromQQ) throws IOException {
        //管理权限 1/成员 2/管理员 3/群主
        if (msg.equals("开通群欢迎")) {
            if (AppInfo.getAdmin() == fromQQ) {
                start(fromGroup);
                if (Ini.read(g, fromGroup + "", "open").equals("0")) {
                    Ini.write(g, fromGroup + "", "open", "1");
                    CQ.sendGroupMsg(fromGroup, "开通成功~\n如需关闭请发送关闭群欢迎");
                } else CQ.sendGroupMsg(fromGroup, "已经开通过了~");
            } else CQ.sendGroupMsg(fromGroup, "没有权限哦~");
        }

        if (msg.equals("关闭群欢迎")) {
            if (AppInfo.getAdmin() == fromQQ) {
                start(fromGroup);

                if (Ini.read(g, fromGroup + "", "open").equals("1")) {
                    Ini.write(g, fromGroup + "", "open", "0");
                    CQ.sendGroupMsg(fromGroup, "关闭成功~\n如需开通请发送开通群欢迎");
                } else CQ.sendGroupMsg(fromGroup, "已经关闭了~");
            } else CQ.sendGroupMsg(fromGroup, "没有权限哦~");
        }
    }

    private static void start(long fromGroup) throws IOException {
        File file = new File(g);
        IniFile iniFile2 = new BasicIniFile();
        IniFileReaderU rad2 = new IniFileReaderU(iniFile2, file);
        rad2.read();

        if (!iniFile2.hasSection(fromGroup + "")) {
            iniFile2.addSection(fromGroup + "");
            IniFileWriterU iniFileWriterU = new IniFileWriterU(iniFile2, file);
            iniFileWriterU.write();
            Ini.write(g, fromGroup + "", "open", "0");
        }
    }

    static String getLevel(int exp) {
        if (exp < 20) {
            return "LV.1";
        } else if (exp < 200) {
            return "LV.2";
        } else if (exp < 2000) {
            return "LV.3";
        } else return "LV.4";
    }
}
