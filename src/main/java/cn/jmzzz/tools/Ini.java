package cn.jmzzz.tools;

import org.dtools.ini.*;

import java.io.File;
import java.io.IOException;

public class Ini {
    public static void write(String f, String sec, String item, String value) throws IOException {
        File file = new File(f);
        IniFile iniFile = new BasicIniFile();
        IniFileReaderU rad = new IniFileReaderU(iniFile, file);
        IniFileWriterU wir = new IniFileWriterU(iniFile, file);
        rad.read();
        IniSection iniSection = iniFile.getSection(sec);
        IniItem iniItem;
        if (iniSection.hasItem(item)) {
            iniItem = iniSection.getItem(item);
        } else iniItem = iniSection.addItem(item);
        iniItem.setValue(value);
        wir.write();
    }

    public static String read(String f, String sec, String item) throws IOException {
        File file = new File(f);
        IniFile iniFile = new BasicIniFile();
        IniFileReaderU reader = new IniFileReaderU(iniFile, file);
        reader.read();
        IniSection section = iniFile.getSection(sec);
        if (section.hasItem(item)) {
            IniItem iniItem = section.getItem(item);
            return iniItem.getValue();
        } else return "0";
    }

//    public static boolean hasItem(String f, String sec, String item) throws IOException {
//        File file = new File(f);
//        IniFile iniFile = new BasicIniFile();
//        IniFileReaderU reader = new IniFileReaderU(iniFile, file);
//        reader.read();
//        IniSection section = iniFile.getSection(sec);
//        return section.hasItem(item);
//    }
}
