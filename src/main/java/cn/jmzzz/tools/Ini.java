package cn.jmzzz.tools;

import org.dtools.ini.*;

import java.io.File;
import java.io.IOException;

/**
 * @author Jazee
 */
public class Ini {
    IniFile iniFile;
    IniFileReaderU rad;
    File file;
    String sec;
    String item;

//    public Ini(String f, String sec, String item) {
//        file = new File(f);
//        iniFile = new BasicIniFile();
//        rad = new IniFileReaderU(iniFile, file);
//        this.sec = sec;
//        this.item = item;
//    }

    public Ini(File file, String sec, String item) {
        this.file = file;
        iniFile = new BasicIniFile();
        rad = new IniFileReaderU(iniFile, file);
        this.sec = sec;
        this.item = item;
    }

    public void write(String value) throws IOException {
        rad.read();
        IniFileWriterU wir = new IniFileWriterU(iniFile, file);
        IniSection iniSection = iniFile.getSection(sec);
        IniItem iniItem;
        if (iniSection.hasItem(item)) {
            iniItem = iniSection.getItem(item);
        } else iniItem = iniSection.addItem(item);
        iniItem.setValue(value);
        wir.write();
    }

    public void writeint(int value) throws IOException {
        write(String.valueOf(value));
    }

    public String read() throws IOException {
        rad.read();
        IniSection section = iniFile.getSection(sec);
        if (section.hasItem(item)) {
            IniItem iniItem = section.getItem(item);
            return iniItem.getValue();
        } else return "0";
    }

    public boolean nothasSec() {
        return !iniFile.hasSection(sec);
    }

    public void writeSec() throws IOException {
        rad.read();
        iniFile.addSection(sec);
        IniFileWriterU wir = new IniFileWriterU(iniFile, file);
        wir.write();
    }

    @Deprecated
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

    @Deprecated
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
}
