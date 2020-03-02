package cn.jmzzz;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Data extends Assistant {
    String f = CQ.getAppDirectory() + "Sub.ini";

    public void saveSubHito(long fromQQ) throws IOException {
        File file = new File(f);
        if (!file.exists()) {
            boolean b = file.createNewFile();
            if (b) {
                String content = "[Hito]";
                FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fileWriter);
                bw.write(content);
                bw.close();
            }
        }
        if (readCfgValue(f, "Hito", fromQQ + "", "0").equals("0")) {
            writeCfgValue(f, "Hito", fromQQ + "", "1");
            CQ.sendPrivateMsg(fromQQ, "订阅一言成功！");
        } else CQ.sendPrivateMsg(fromQQ, "您已经已阅了一言~");
    }

    /**
     * 从ini配置文件中读取变量的值
     *
     * @param file         配置文件的路径
     * @param section      要获取的变量所在段名称
     * @param variable     要获取的变量名称
     * @param defaultValue 变量名称不存在时的默认值
     * @return 变量的值
     * @throws IOException 抛出文件操作可能出现的io异常
     */
    public static String readCfgValue(String file, String section, String variable, String defaultValue) throws IOException {
        String strLine, value;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            boolean isInSection = false;
            while ((strLine = bufferedReader.readLine()) != null) {
                strLine = strLine.trim();
                strLine = strLine.split("[;]")[0];
                isInSection = isInSection(section, strLine, isInSection);
                if (isInSection) {
                    strLine = strLine.trim();
                    String[] strArray = strLine.split("=");
                    if (strArray.length == 1) {
                        value = strArray[0].trim();
                        if (value.equalsIgnoreCase(variable)) {
                            value = "";
                            return value;
                        }
                    } else if (strArray.length == 2) {
                        value = strArray[0].trim();
                        if (value.equalsIgnoreCase(variable)) {
                            value = strArray[1].trim();
                            return value;
                        }
                    } else if (strArray.length > 2) {
                        value = strArray[0].trim();
                        if (value.equalsIgnoreCase(variable)) {
                            value = strLine.substring(strLine.indexOf("=") + 1).trim();
                            return value;
                        }
                    }
                }
            }
        }
        return defaultValue;
    }

    /**
     * 修改ini配置文件中变量的值
     *
     * @param file     配置文件的路径
     * @param section  要修改的变量所在段名称
     * @param variable 要修改的变量名称
     * @param value    变量的新值
     * @throws IOException 抛出文件操作可能出现的io异常
     */
    public static void writeCfgValue(String file, String section, String variable, String value) throws IOException {
        StringBuilder fileContent;
        String allLine;
        String strLine;
        String newLine;
        String getValue;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            boolean isInSection = false;
            fileContent = new StringBuilder();
            while ((allLine = bufferedReader.readLine()) != null) {
                allLine = allLine.trim();
                strLine = allLine.split(";")[0];
                isInSection = isInSection(section, strLine, isInSection);
                if (isInSection) {
                    strLine = strLine.trim();
                    String[] strArray = strLine.split("=");
                    getValue = strArray[0].trim();
                    if (getValue.equalsIgnoreCase(variable)) {
                        newLine = getValue + "=" + value;
                        fileContent.append(newLine);
                        while ((allLine = bufferedReader.readLine()) != null) {
                            fileContent.append("\r\n").append(allLine);
                        }
                        bufferedReader.close();
                        //canAdd = false;
                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));
                        bufferedWriter.write(fileContent.toString());
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        return;
                    }
                }
                fileContent.append(allLine).append("\r\n");
            }
            String str = variable + "=" + value;
            fileContent.append(str).append("\r\n");
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));
            bufferedWriter.write(fileContent.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        }
    }

    private static boolean isInSection(String section, String strLine, boolean isInSection) {
        Pattern p;
        Matcher m;
        p = Pattern.compile("\\[\\w+]");
        m = p.matcher((strLine));
        if (m.matches()) {
            p = Pattern.compile("\\[" + section + "]");
            m = p.matcher(strLine);
            isInSection = m.matches();
        }
        return isInSection;
    }


}



