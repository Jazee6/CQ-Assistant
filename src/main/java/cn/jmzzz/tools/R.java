package cn.jmzzz.tools;

import cn.jmzzz.PersonRespond;
import com.alibaba.fastjson.JSONObject;

import java.util.Random;

import static com.sobte.cqp.jcq.event.JcqApp.CC;

public class R {
    public static String getRespondString() {
        Random random = new Random();
        int r1 = random.nextInt(2);
        String s = null;
        switch (r1) {
            case 0:
                s = "\n我在" + CC.face(32);
                break;
            case 1:
                s = "\n我在这~";
                break;
        }
        return s;
    }

    public static String getAbout() {
        return "关于\n简洁的助手类机器人，基于CoolQ的JCQ\n当前版本：稳定版" + AppInfo.getAppVer() + "\n发送“检查更新”查看最新版本\n本项目在Github开源，访问官网了解更多";
    }

    public static String getFunctionList() {
        return "功能列表\n1.群应答\n2.群一言\n3.社会语录\n4.短网址\n详细内容请查看官网";
    }

    public static String getHito() {
        JSONObject object = JSONObject.parseObject(HttpGet.doGet("https://v1.hitokoto.cn/"));
        return object.getString("hitokoto") + "\n\t\t\t——" + object.getString("from");
    }

    public static String getUpdate() {
        UpdateCheck updateCheck = new UpdateCheck();
        if (updateCheck.checkUpdate()) {
            return "有新版本啦！\n最新版本：" + updateCheck.getNewver() + "\n\n" + "更新内容请访问Github";
        } else return "暂无新版本~\n当前版本：" + AppInfo.getAppVer() + "\n\n" + "更多内容请访问官网";
    }

    public static String getDwz(String msg, long fromQQ) {
        if (msg.length() > 10) {
            JSONObject object = new JSONObject();
            if (msg.startsWith("http", 3)) {
                object.put("Url", msg.substring(3));
            } else {
                object.put("Url", "http://" + msg.substring(3));
            }
            JSONObject object1 = JSONObject.parseObject(HttpPost.post(AppInfo.dwzRequest(), object.toJSONString(), AppInfo.dwzToken()));
            if (object1.getString("Code").equals("0")) {
                return object1.getString("ShortUrl");
            } else {
                PersonRespond.sendFeedback(object1.getString("Code"), fromQQ, "dwz");
                return "错误(" + object1.getString("Code") + ")已经反馈给开发者";
            }
        } else return "输入有误，请输入短网址+链接\n（链接长度大于7）";
    }

    public static String getSocial(long fromQQ) {
        JSONObject object = JSONObject.parseObject(HttpGet.doGet("https://api.oioweb.cn/api/jsyl.php"));
        if (Integer.parseInt(object.getString("code")) == 1) {
            return object.getString("msg");
        } else {
            PersonRespond.sendFeedback(object.getString("code"), fromQQ, "social");
            return "错误(" + object.getString("code") + ")已经反馈给开发者";
        }
    }
}
