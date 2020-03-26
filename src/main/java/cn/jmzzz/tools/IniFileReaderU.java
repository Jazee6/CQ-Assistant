package cn.jmzzz.tools;

import org.dtools.ini.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class IniFileReaderU {
    private final File file;
    private final IniFile ini;

    static String getEndLineComment(String line) {
        if (!isSection(line) && !isItem(line)) {
            throw new FormatException("getEndLineComment(String) is unable to return the comment from the given string (\"" + line + "\" as it is not an item nor a section.");
        } else {
            int pos = line.indexOf(59);
            return pos == -1 ? "" : line.substring(pos + 1).trim();
        }
    }

    static String getItemName(String line) {
        if (!isItem(line)) {
            throw new FormatException("getItemName(String) is unable to return the name of the item as the given string (\"" + line + "\" is not an item.");
        } else {
            int pos = line.indexOf(61);
            return pos == -1 ? "" : line.substring(0, pos).trim();
        }
    }

    static String getItemValue(String line) {
        if (!isItem(line)) {
            throw new FormatException("getItemValue(String) is unable to return the value of the item as the given string (\"" + line + "\" is not an item.");
        } else {
            int posEquals = line.indexOf(61);
            int posComment = line.indexOf(59);
            if (posEquals == -1) {
                return posComment == -1 ? line : line.substring(0, posComment).trim();
            } else {
                return posComment == -1 ? line.substring(posEquals + 1).trim() : line.substring(posEquals + 1, posComment).trim();
            }
        }
    }

    static String getSectionName(String line) {
        if (!isSection(line)) {
            throw new FormatException("getSectionName(String) is unable to return the name of the section as the given string (\"" + line + "\" is not a section.");
        } else {
            int firstPos = line.indexOf(91);
            int lastPos = line.indexOf(93);
            return line.substring(firstPos + 1, lastPos).trim();
        }
    }

    static boolean isComment(String line) {
        line = line.trim();
        if (line.isEmpty()) {
            return false;
        } else {
            char firstChar = line.charAt(0);
            return firstChar == ';';
        }
    }

    static boolean isItem(String line) {
        line = removeComments(line);
        if (line.isEmpty()) {
            return false;
        } else {
            int pos = line.indexOf(61);
            if (pos != -1) {
                String name = line.substring(0, pos).trim();
                return name.length() > 0;
            } else {
                return false;
            }
        }
    }

    static boolean isSection(String line) {
        line = removeComments(line);
        if (line.isEmpty()) {
            return false;
        } else {
            char firstChar = line.charAt(0);
            char lastChar = line.charAt(line.length() - 1);
            return firstChar == '[' && lastChar == ']';
        }
    }

    static String removeComments(String line) {
        return line.contains(String.valueOf(';')) ? line.substring(0, line.indexOf(59)).trim() : line.trim();
    }

    public IniFileReaderU(IniFile ini, File file) {
        if (ini == null) {
            throw new NullPointerException("The given IniFile cannot be null.");
        } else if (file == null) {
            throw new NullPointerException("The given File cannot be null.");
        } else {
            this.file = file;
            this.ini = ini;
        }
    }

    public void read() throws IOException {
        IniSection currentSection = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), StandardCharsets.UTF_8));
        StringBuilder comment = new StringBuilder();
        Commentable lastCommentable = null;

        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                if ((comment.length() > 0) && lastCommentable != null) {
                    lastCommentable.setPostComment(comment.toString());
                    comment = new StringBuilder();
                }
            } else {
                String itemName;
                if (isComment(line)) {
                    itemName = line.substring(1).trim();
                    if (comment.length() == 0) {
                        comment = new StringBuilder(itemName);
                    } else {
                        comment.append("\n").append(itemName);
                    }
                } else {
                    String itemValue;
                    if (isSection(line)) {
                        itemName = getSectionName(line);
                        itemValue = getEndLineComment(line);
                        if (this.ini.hasSection(itemName)) {
                            currentSection = this.ini.getSection(itemName);
                        } else {
                            currentSection = this.ini.addSection(itemName);
                        }

                        currentSection.setEndLineComment(itemValue);
                        if (comment.length() > 0) {
                            currentSection.setPreComment(comment.toString());
                            comment = new StringBuilder();
                        }

                        lastCommentable = currentSection;
                    } else if (isItem(line)) {
                        if (currentSection == null) {
                            throw new FormatException("An Item has been read,before any section.");
                        }

                        itemName = getItemName(line);
                        itemValue = getItemValue(line);
                        String endLineComment = getEndLineComment(line);
                        IniItem item;
                        if (currentSection.hasItem(itemName)) {
                            item = currentSection.getItem(itemName);
                        } else {
                            try {
                                item = currentSection.addItem(itemName);
                            } catch (InvalidNameException var11) {
                                throw new FormatException("The string \"" + itemName + "\" is an invalid name for an " + "IniItem.");
                            }
                        }

                        item.setValue(itemValue);
                        item.setEndLineComment(endLineComment);
                        if (comment.length() > 0) {
                            item.setPreComment(comment.toString());
                            comment = new StringBuilder();
                        }

                        lastCommentable = item;
                    }
                }
            }
        }

        if ((comment.length() > 0) && lastCommentable != null) {
            lastCommentable.setPostComment(comment.toString());
        }

        reader.close();
    }
}