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

public class GE extends Assistant implements Runnable {
    public static void sendGE(long fromGroup, long fromQQ) throws IOException {

        File file = new File(g);
        IniFile iniFile = new BasicIniFile();
        IniFileReaderU readerU = new IniFileReaderU(iniFile, file);
        readerU.read();
        if (!iniFile.hasSection(fromGroup + "")) {
            iniFile.addSection(fromGroup + "");
            IniFileWriterU writerU = new IniFileWriterU(iniFile, file);
            writerU.write();
            Ini.write(g, fromGroup + "", "open", "1");
        }
        if (iniFile.hasSection(fromGroup + "")) {
            if (!Ini.read(g, fromGroup + "", "open").equals("0")) {
                Date day = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                String s = Ini.read(g, fromGroup + "", fromQQ + "");
                if (!s.equals("0")) {
                    if (Integer.parseInt(df.format(day).substring(2, 8)) > Integer.parseInt(s.substring(2, 8))) {
                        Ini.write(g, fromGroup + "", fromQQ + "", df.format(day));
                        CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + "欢迎入群");
                    }
                } else {
                    Ini.write(g, fromGroup + "", fromQQ + "", df.format(day));
                    CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + "欢迎入群~");
                }
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

    @Override
    public void run() {

    }
}
