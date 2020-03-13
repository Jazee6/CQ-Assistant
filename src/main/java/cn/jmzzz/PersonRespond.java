package cn.jmzzz;

import cn.jmzzz.tools.*;

import java.io.IOException;

public class PersonRespond extends Assistant {

    public static void sendMenu(String msg, long fromQQ) {
        if (msg.equals("菜单")) {
            CQ.sendPrivateMsg(fromQQ, "菜单\n1.订阅列表\n2.功能列表\n3.关于\n请回复序号或名称");
        }
    }

    public static void sendSubscriptionList(String msg, long fromQQ) {
        if (msg.equals("订阅列表") || msg.equals("1")) {
            CQ.sendPrivateMsg(fromQQ, "订阅列表\n1.一言\n请回复订阅+项目名");
        }
    }

    public void sendSub(String msg, long fromQQ) throws IOException {
        if (msg.equals("订阅一言")) {
            if (Ini.read(f, "Hito", fromQQ + "").equals("0")) {
                Ini.write(f, "Hito", fromQQ + "", "1", false);
                CQ.sendPrivateMsg(fromQQ, "订阅一言成功！\n如需取消请发送取消+对应的项目名");
            } else CQ.sendPrivateMsg(fromQQ, "您已经订阅过了一言~\n如需取消请发送取消+对应的项目名");
        }
    }

    //if (msg.equals("订阅高考")) {
//        if (readCfgValue(f, "Countdown", fromQQ + "", "0").equals("0")) {
//            writeCfgValue(f, "Countdown", fromQQ + "", "1");
//            CQ.sendPrivateMsg(fromQQ, "订阅高考（2021）成功！\n如需取消请发送取消+对应的项目名");
//        } else CQ.sendPrivateMsg(fromQQ, "您已经订阅过了高考（2021）~\n如需取消请发送取消+对应的项目名");
//    }
    public void sendCancel(String msg, long fromQQ) throws IOException {
        if (msg.equals("取消一言")) {
            if (!Ini.read(f, "Hito", fromQQ + "").equals("0")) {
                Ini.write(f, "Hito", fromQQ + "", "0", true);
                CQ.sendPrivateMsg(fromQQ, "取消订阅一言成功！");
            } else CQ.sendPrivateMsg(fromQQ, "您没有订阅一言！");
        }
    }


//    public static void sendCheckin(String msg, long fromQQ) {
//    }

    public static void sendFeedback(String code, long fromQQ, String target) {
        CQ.sendPrivateMsg(AppInfo.getAdmin(), "Code:" + code + "\nFrom:" + fromQQ + "\nTarget:" + target);
    }

    //以下为Group和Person共有项目
    public static void sendAbout(String msg, long fromQQ) {
        if (msg.equals("关于") || msg.equals("3")) {
            CQ.sendPrivateMsg(fromQQ, R.getAbout());
        }
    }

    public static void sendFunctionList(String msg, long fromQQ) {
        if (msg.equals("功能列表") || msg.equals("2")) {
            CQ.sendPrivateMsg(fromQQ, R.getFunctionList());
        }
    }

    public static void sendHitokoto(String msg, long fromQQ) {
        if (msg.equals("一言")) {
            CQ.sendPrivateMsg(fromQQ, R.getHito());
        }
    }

    public static void updateCheck(String msg, long fromQQ) {
        if (msg.equals("检查更新")) {
            CQ.sendPrivateMsg(fromQQ, R.getUpdate());
        }
    }

    public static void sendSocial(String msg, long fromQQ) {
        if (msg.equals("社会语录")) {
            CQ.sendPrivateMsg(fromQQ, R.getSocial(fromQQ));
        }
    }

    public static void sendShortUrl(String msg, long fromQQ) {
        if (msg.startsWith("短网址")) {
            CQ.sendPrivateMsg(fromQQ, R.getDwz(msg, fromQQ));
        }
    }
}

//    public void sendFeedback(String msg, long fromQQ) {
//        if (msg.substring(0, 2).equals("反馈"))
//    }
