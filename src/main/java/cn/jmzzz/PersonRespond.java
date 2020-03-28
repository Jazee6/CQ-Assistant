package cn.jmzzz;

import cn.jmzzz.tools.*;

import java.io.IOException;

public class PersonRespond extends Assistant {

    public static void sendMenu(String msg, long fromQQ) {
        if (msg.equals("菜单")) {
            CQ.sendPrivateMsg(fromQQ, "菜单\n1.订阅列表\n2.功能列表\n3.关于\n4.反馈\n请回复项目名称~\nV" + AppInfo.getAppVer());
        }
    }

    public static void sendSubscriptionList(String msg, long fromQQ) {
        if (msg.equals("订阅列表")) {
            CQ.sendPrivateMsg(fromQQ, "订阅列表\n1.一言\n2.社会语录\n请回复订阅+项目名~");
        }
    }

    public void sendSubCancel(String msg, long fromQQ) throws IOException {
        String name1 = "一言";
        if (msg.equals("订阅" + name1)) {
            if (Ini.read(f, "Hito", fromQQ + "").equals("0")) {
                Ini.write(f, "Hito", fromQQ + "", "1");
                CQ.sendPrivateMsg(fromQQ, "订阅" + name1 + "成功！\n如需取消请发送取消+对应的项目名~");
            } else CQ.sendPrivateMsg(fromQQ, "您已经订阅过了" + name1 + "~\n如需取消请发送取消+对应的项目名~");
        }
        if (msg.equals("取消" + name1)) {
            if (!Ini.read(f, "Hito", fromQQ + "").equals("0")) {
                Ini.write(f, "Hito", fromQQ + "", "0");
                CQ.sendPrivateMsg(fromQQ, "取消订阅" + name1 + "成功！");
            } else CQ.sendPrivateMsg(fromQQ, "您没有订阅" + name1 + "！");
        }
        String name2 = "社会语录";
        if (msg.equals("订阅" + name2)) {
            if (Ini.read(f, "Soc", fromQQ + "").equals("0")) {
                Ini.write(f, "Soc", fromQQ + "", "1");
                CQ.sendPrivateMsg(fromQQ, "订阅" + name2 + "成功！\n如需取消请发送取消+对应的项目名~");
            } else CQ.sendPrivateMsg(fromQQ, "您已经订阅过了" + name2 + "~\n如需取消请发送取消+对应的项目名~");
        }
        if (msg.equals("取消" + name2)) {
            if (!Ini.read(f, "Soc", fromQQ + "").equals("0")) {
                Ini.write(f, "Soc", fromQQ + "", "0");
                CQ.sendPrivateMsg(fromQQ, "取消订阅" + name2 + "成功！");
            } else CQ.sendPrivateMsg(fromQQ, "您没有订阅" + name2 + "！");
        }
        if (msg.startsWith("设置称呼")) {
            if (msg.length() == 4 || msg.equals("设置称呼0")) {
                CQ.sendPrivateMsg(fromQQ, "格式有误，请发送设置称呼+称呼哦~");
            } else if (msg.length() > 10) {
                CQ.sendPrivateMsg(fromQQ, "称呼太长了吧 Master~");
            } else {
                Ini.write(f, "Call", fromQQ + "", msg.substring(4));
                CQ.sendPrivateMsg(fromQQ, "改变称呼啦~\n" + Hello.getHello() + Ini.read(f, "Call", fromQQ + ""));
            }
        }
//        String name3 = "天气";
//        if (msg.contains("订阅") && msg.contains(name3)) {
//            if (msg.length() > 4) {
//                if (Ini.read(f, "Wea", fromQQ + "").equals("0")) {
//                    Ini.write(f, "Wea", fromQQ + "", msg.substring(2, msg.indexOf("天")));
//                    CQ.sendPrivateMsg(fromQQ, "订阅" + msg.substring(2, msg.indexOf("天")) + name3 + "成功！\n如需取消请发送取消+对应的项目名~");
//                } else
//                    CQ.sendPrivateMsg(fromQQ, "您已经订阅过了" + Ini.read(f, "Wea", fromQQ + "") + name3 + "~\n如需取消请发送取消+对应的项目名~");
//            } else CQ.sendPrivateMsg(fromQQ, "请发送订阅+城市+天气哦~\n例：订阅上海天气");
//        }
//        if (msg.equals("取消" + name3)) {
//            if (!Ini.read(f, "Wea", fromQQ + "").equals("0")) {
//                Ini.write(f, "Wea", fromQQ + "", "0");
//                CQ.sendPrivateMsg(fromQQ, "取消订阅" + name3 + "成功！");
//            } else CQ.sendPrivateMsg(fromQQ, "您没有订阅" + name3 + "！");
//        }
    }

    public static void sendFeedback(String msg, long fromQQ) {
        if (msg.startsWith("反馈") && msg.length() > 3) {
            sendFeedback("000", fromQQ, msg.substring(2));
            CQ.sendPrivateMsg(fromQQ, "已经反馈回1号基地~");
        }
        if (msg.equals("反馈")) {
            CQ.sendPrivateMsg(fromQQ, "请发送反馈+内容~");
        }
    }

    public static void sendFeedback(String code, long fromQQ, String target) {
        CQ.sendPrivateMsg(AppInfo.getAdmin(), "Code:" + code + "\nFrom:" + fromQQ + "\nTarget:" + target);
    }

    public static void sendFeedback(String code, String target) {
        CQ.sendPrivateMsg(AppInfo.getAdmin(), "Code:" + code + "\nTarget:" + target);
    }

    public static void sendRes(String msg, long fromQQ) {
        if (msg.equals("自定义称呼")) {
            CQ.sendPrivateMsg(fromQQ, "请回复：设置称呼+称呼哦~\n例：设置称呼泽卡\n在您订阅了内容之后才会用您的自定义称呼哦~");
        }
    }

    //以下为Group和Person共有项目
    public static void sendAbout(String msg, long fromQQ) {
        if (msg.equals("关于")) {
            CQ.sendPrivateMsg(fromQQ, R.getAbout());
        }
    }

    public static void sendFunctionList(String msg, long fromQQ) {
        if (msg.equals("功能列表")) {
            CQ.sendPrivateMsg(fromQQ, R.getFunctionList());
        }
    }

    public static void sendHitokoto(String msg, long fromQQ) {
        if (msg.equals("一言")) {
            CQ.sendPrivateMsg(fromQQ, R.getHito());
        }
    }

    public static void sendSocial(String msg, long fromQQ) {
        if (msg.equals("社会语录")) {
            CQ.sendPrivateMsg(fromQQ, R.getSocial());
        }
        if (msg.equals("社会三连")) {
            CQ.sendPrivateMsg(fromQQ, R.getSocial());
            CQ.sendPrivateMsg(fromQQ, R.getSocial());
            CQ.sendPrivateMsg(fromQQ, R.getSocial());
        }
    }

//    public static void sendShortUrl(String msg, long fromQQ) {
//        if (msg.startsWith("短网址")) {
//            CQ.sendPrivateMsg(fromQQ, R.getDwz(msg, fromQQ));
//        }
//    }

    //    public static void sendNowWea(String msg, long fromQQ) {
//        if (msg.contains("天气")) {
//            if (msg.length() > 3) {
//                CQ.sendPrivateMsg(fromQQ, R.getNowWeather(msg.substring(0, msg.indexOf("天"))));
//            } else CQ.sendPrivateMsg(fromQQ, "查询格式：城市+天气\n例：长沙天气");
//        }
//    }

    public static void sendRespond(String msg, long fromQQ) {
        boolean b = false;
        if (msg.contains("泽卡")) {
            b = true;
        } else if (msg.contains("机器人")) {
            b = true;
        }
        if (b && msg.length() <= 5) {
            CQ.sendPrivateMsg(fromQQ, R.getRespondString());
        }
    }
}
