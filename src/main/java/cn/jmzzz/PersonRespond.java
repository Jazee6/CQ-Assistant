package cn.jmzzz;

import cn.jmzzz.tools.*;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static cn.jmzzz.tools.Ini.readCfgValue;
import static cn.jmzzz.tools.Ini.writeCfgValue;

public class PersonRespond extends Assistant {
    String s;

    public void sendMenu(String msg, long fromQQ) {
        if (msg.equals("菜单")) {
            CQ.sendPrivateMsg(fromQQ, "菜单\n1.订阅列表\n2.功能列表\n3.关于\n请回复序号或名称");
        }
    }

    public void sendAbout(String msg, long fromQQ) {
        if (msg.equals("关于") || msg.equals("3")) {
            CQ.sendPrivateMsg(fromQQ, "关于\n简洁的助手类机器人，基于CoolQ的JCQ\n当前版本：稳定版" + AppInfo.getAppVer() + "\n发送“检查更新”查看最新版本\n本项目在Github开源，访问官网了解更多");
        }
    }

    public void sendFunctionList(String msg, long fromQQ) {
        if (msg.equals("功能列表") || msg.equals("2")) {
            CQ.sendPrivateMsg(fromQQ, "功能列表\n1.群应答\n2.群一言\n3.社会语录\n详细内容请查看官网");
        }
    }

    public void sendSubscriptionList(String msg, long fromQQ) {
        if (msg.equals("订阅列表") || msg.equals("1")) {
            CQ.sendPrivateMsg(fromQQ, "订阅列表\n1.一言\n请回复订阅+项目名");
        }
    }

    public void sendSubscriptionHitokoto(String msg, long fromQQ) throws IOException {
        if (msg.equals("订阅一言")) {
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
                CQ.sendPrivateMsg(fromQQ, "订阅一言成功！\n如需取消请发送取消+对应的项目名");
            } else CQ.sendPrivateMsg(fromQQ, "您已经订阅过了一言~\n如需取消请发送取消+对应的项目名");
        }
    }

    public void sendHitokoto(String msg, long fromQQ) {
        if (msg.equals("一言")) {
            s = HttpGet.doGet("https://v1.hitokoto.cn/");
            JSONObject object = JSONObject.parseObject(s);
            CQ.sendPrivateMsg(fromQQ, object.getString("hitokoto"));
        }
    }

    public void updateCheck(String msg, long fromQQ) {
        if (msg.equals("检查更新")) {
            UpdateCheck updateCheck = new UpdateCheck();
            if (updateCheck.checkUpdate()) {
                CQ.sendPrivateMsg(fromQQ, "有新版本啦！最新版本：" + updateCheck.getNewver());
            } else CQ.sendPrivateMsg(fromQQ, "暂无新版本~当前版本：" + AppInfo.getAppVer());
        }
    }

    public void sendTest(String msg, long fromQQ) {
        if (msg.equals("t") && admin(fromQQ)) {
            CQ.sendPrivateMsg(fromQQ, "下面执行test1");
        } else if (msg.equals("t") && !admin(fromQQ)) {
            CQ.sendPrivateMsg(fromQQ, "没有权限！");
        }
    }

    private boolean admin(long fromQQ) {
        if (fromQQ == 1760017758L) {
            return true;
        } else return fromQQ == 2609059914L;
    }

    public void sendSocial(String msg, long fromQQ) {
        if (msg.equals("社会语录")) {
            JSONObject object = JSONObject.parseObject(HttpGet.doGet("https://api.oioweb.cn/api/jsyl.php"));
            if (Integer.parseInt(object.getString("code")) == 1) {
                CQ.sendPrivateMsg(fromQQ, object.getString("msg"));
            } else CQ.sendPrivateMsg(fromQQ, "错误(" + object.getString("code") + ")请截图反馈开发者");
        }
    }

    public void sendCancel(String msg, long fromQQ) throws IOException {
        if (msg.equals("取消一言")) {
            if (!Ini.readCfgValue(super.f, "Hito", fromQQ + "", "0").equals("0")) {
                Ini.writeCfgValue(super.f, "Hito", fromQQ + "", "0");
                CQ.sendPrivateMsg(fromQQ, "取消订阅一言成功！");
            } else CQ.sendPrivateMsg(fromQQ, "您没有订阅一言！");
        }
    }
}