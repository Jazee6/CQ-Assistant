package cn.jmzzz.tools;

import org.dtools.ini.IniFile;
import org.dtools.ini.IniItem;
import org.dtools.ini.IniSection;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class IniFileWriterU {
    private final IniFile ini;
    private final File file;
    private boolean sectionLineSeparator;
    private boolean includeSpaces;
    private boolean itemLineSeparator;

    public IniFileWriterU(IniFile ini, File file) {
        if (ini == null) {
            throw new IllegalArgumentException("Cannot write a null IniFile");
        } else if (file == null) {
            throw new IllegalArgumentException("Cannot write an IniFile to a null file");
        } else {
            this.ini = ini;
            this.file = file;
            this.setIncludeSpaces(false);
            this.setItemLineSeparator(false);
            this.setSectionLineSeparator(false);
        }
    }

    private String iniToString(IniFile ini) {
        StringBuilder builder = new StringBuilder();
        int size = ini.getNumberOfSections();

        for (int i = 0; i < size; ++i) {
            IniSection section = ini.getSection(i);
            builder.append(this.sectionToString(section));
            builder.append("\r\n");
        }

        return builder.toString();
    }

    private String formatComment(String comment, boolean prefixNewLine) {
        StringBuilder sb = new StringBuilder();
        if (comment.contains("\n")) {
            String[] comments = comment.split("\n");

            for (String aComment : comments) {
                if (prefixNewLine) {
                    sb.append("\r\n");
                }

                sb.append(';').append(aComment);
                if (!prefixNewLine) {
                    sb.append("\r\n");
                }
            }
        } else {
            if (prefixNewLine) {
                sb.append("\r\n");
            }

            sb.append(';').append(comment);
            if (!prefixNewLine) {
                sb.append("\r\n");
            }
        }

        return sb.toString();
    }

    private String itemToString(IniItem item) {
        StringBuilder builder = new StringBuilder();
        String comment = item.getPreComment();
        if (!comment.equals("")) {
            builder.append(this.formatComment(comment, false));
        }

        if (this.includeSpaces) {
            builder.append(item.getName()).append(" = ");
        } else {
            builder.append(item.getName()).append("=");
        }

        if (item.getValue() != null) {
            builder.append(item.getValue());
        }

        if (!item.getEndLineComment().equals("")) {
            builder.append(" ;").append(item.getEndLineComment());
        }

        comment = item.getPostComment();
        if (!comment.equals("")) {
            builder.append(this.formatComment(comment, true));
            builder.append("\r\n");
        } else if (this.itemLineSeparator) {
            builder.append("\r\n");
        }

        return builder.toString();
    }

    private String sectionToString(IniSection section) {
        StringBuilder builder = new StringBuilder();
        if (this.sectionLineSeparator) {
            builder.append("\r\n");
        }

        String comment = section.getPreComment();
        if (!comment.equals("")) {
            builder.append(this.formatComment(comment, false));
        }

        builder.append("[").append(section.getName()).append("]");
        comment = section.getEndLineComment();
        if (!comment.equals("")) {
            builder.append(" ;").append(comment);
        }

        comment = section.getPostComment();
        if (!comment.equals("")) {
            builder.append(this.formatComment(comment, true));
            builder.append("\r\n");
        } else if (this.sectionLineSeparator) {
            builder.append("\r\n");
        }

        int size = section.getNumberOfItems();

        for (int i = 0; i < size; ++i) {
            IniItem item = section.getItem(i);
            builder.append("\r\n");
            builder.append(this.itemToString(item));
        }

        return builder.toString();
    }

    public void setIncludeSpaces(boolean value) {
        this.includeSpaces = value;
    }

    public void setItemLineSeparator(boolean value) {
        this.itemLineSeparator = value;
    }

    public void setSectionLineSeparator(boolean value) {
        this.sectionLineSeparator = value;
    }

    public void write() throws IOException {
        BufferedWriter bufferWriter;
        FileOutputStream fos = new FileOutputStream(this.file);
        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        bufferWriter = new BufferedWriter(osw);
        bufferWriter.write(this.iniToString(this.ini));
        bufferWriter.close();
    }
}
