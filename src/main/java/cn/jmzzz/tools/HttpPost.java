package cn.jmzzz.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPost {
    /**
     * 发送HttpPost请求
     *
     * @param strURL 服务地址
     * @param params json字符串,例如: "{ \"id\":\"12345\" }" ;其中属性名必须带双引号<br/>
     * @return 成功:返回json字符串<br/>
     */
    public static String post(String strURL, String params, String Token) {
        BufferedReader reader;
        try {
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            // connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
//            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.setRequestProperty("Token", Token);
            connection.connect();
            //一定要用BufferedReader 来接收响应， 使用字节来接收响应的方法是接收不到内容的
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "GBK"); // utf-8编码
            out.append(params);
            out.flush();
            out.close();
            // 读取响应
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "GBK"));
            String line;
            StringBuilder res = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                res.append(line);
            }
            reader.close();

            return res.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error"; // 自定义错误信息
    }
}
//如果一定要使用如下方式接收响应数据， 则响应必须为: response.getWriter().print(StringUtils.join("{\"errCode\":\"1\",\"errMsg\":\"", message, "\"}")); 来返回
//            int length = (int) connection.getContentLength();// 获取长度
//            if (length != -1) {
//                byte[] data = new byte[length];
//                byte[] temp = new byte[512];
//                int readLen = 0;
//                int destPos = 0;
//                while ((readLen = is.read(temp)) > 0) {
//                    System.arraycopy(temp, 0, data, destPos, readLen);
//                    destPos += readLen;
//                }
//                String result = new String(data, "UTF-8"); // utf-8编码
//                return result;
//            }
