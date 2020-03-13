package cn.jmzzz.tools;

import org.dtools.ini.*;

import java.io.File;
import java.io.IOException;

public class Ini {
    public static void write(String f, String sec, String item, String value, boolean modify) throws IOException {
        File file = new File(f);
        IniFile iniFile = new BasicIniFile();
        IniFileReader rad = new IniFileReader(iniFile, file);
        IniFileWriter wir = new IniFileWriter(iniFile, file);
        rad.read();
        IniSection iniSection = iniFile.getSection(sec);
        IniItem iniItem;
        if (modify) {
            iniItem = iniSection.getItem(item);
        } else iniItem = iniSection.addItem(item);
        iniItem.setValue(value);
        wir.write();
    }

    public static String read(String f, String sec, String item) throws IOException {
        File file = new File(f);
        IniFile iniFile = new BasicIniFile();
        IniFileReader reader = new IniFileReader(iniFile, file);
        reader.read();
        IniSection section = iniFile.getSection(sec);
        if (section.hasItem(item)) {
            IniItem iniItem = section.getItem(item);
            return iniItem.getValue();
        } else return "0";
    }
}
