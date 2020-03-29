package cn.jmzzz;


import cn.jmzzz.tools.AppInfo;
import cn.jmzzz.tools.R;

public class GroupRespond extends Assistant {
    public static void sendRespond(String msg, long fromGroup, long fromQQ) {
        boolean b = false;
        if (msg.contains("泽卡")) {
            b = true;
        } else if (msg.contains("机器人")) {
            b = true;
        }
        if (b && msg.length() <= 5) {
            CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + R.getRespondString());
        }
    }

    public static void sendMenu(String msg, long fromGroup) {
        if (msg.equals("菜单")) {
            CQ.sendGroupMsg(fromGroup, "菜单\n1.一言\n2.社会语录\n请回复项目名称~\nV" + AppInfo.getAppVer());
        }
    }

    public static void sendHitokoto(String msg, long fromGroup) {
        if (msg.equals("一言")) {
            CQ.sendGroupMsg(fromGroup, R.getHito());
        }
    }

    public static void sendSocial(String msg, long fromGroup) {
        if (msg.equals("社会语录") || msg.equals("精神小伙")) {
            CQ.sendGroupMsg(fromGroup, R.getSocial());
        }
        if (msg.equals("社会三连")) {
            CQ.sendGroupMsg(fromGroup, R.getSocial());
            CQ.sendGroupMsg(fromGroup, R.getSocial());
            CQ.sendGroupMsg(fromGroup, R.getSocial());
        }
    }
}

