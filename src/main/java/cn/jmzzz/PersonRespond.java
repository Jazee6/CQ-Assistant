package cn.jmzzz;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

public class PersonRespond extends Assistant {
    String s;

    public void sendMenu(String msg, long fromQQ) {
        if (msg.equals("菜单")) {
            CQ.sendPrivateMsg(fromQQ, "菜单\n1.订阅列表\n2.功能列表\n3.关于\n请回复序号或名称");
            super.ifignore = true;
        }
    }

    public void sendAbout(String msg, long fromQQ) {
        if (msg.equals("关于") || msg.equals("3")) {
            CQ.sendPrivateMsg(fromQQ, "关于\n简洁的助手类机器人，基于CoolQ的JCQ\n当前版本：稳定版" + UpdateCheck.getVersion() + "\n发送“检查更新”查看最新版本\n本项目在Github开源，访问官网了解更多");
            super.ifignore = true;
        }
    }

    public void sendFunctionList(String msg, long fromQQ) {
        if (msg.equals("功能列表") || msg.equals("2")) {
            CQ.sendPrivateMsg(fromQQ, "功能列表\n1.群应答\n2.群一言\n详细内容请查看官网");
            super.ifignore = true;
        }
    }

    public void sendSubscriptionList(String msg, long fromQQ) {
        if (msg.equals("订阅列表") || msg.equals("1")) {
            CQ.sendPrivateMsg(fromQQ, "订阅列表\n1.一言\n请回复订阅+项目名");
            super.ifignore = true;
        }
    }

    public void sendSubscriptionHitokoto(String msg, long fromQQ) throws IOException {
        if (msg.equals("订阅一言")) {
            Data data = new Data();
            data.saveSubHito(fromQQ);
            super.ifignore = true;
        }
    }

    public void sendHitokoto(String msg, long fromQQ) {
        if (msg.equals("一言")) {
            s = HttpGet.doGet("https://v1.hitokoto.cn/");
            JSONObject object = JSONObject.parseObject(s);
            CQ.sendPrivateMsg(fromQQ, object.getString("hitokoto"));
            super.ifignore = true;
        }
    }

    public void updateCheck(String msg, long fromQQ) {
        if (msg.equals("检查更新")) {
            UpdateCheck updateCheck = new UpdateCheck();
            if (updateCheck.checkUpdate()) {
                CQ.sendPrivateMsg(fromQQ, "有新版本啦！最新版本：" + updateCheck.getNewver());
            } else CQ.sendPrivateMsg(fromQQ, "暂无新版本~当前版本：" + UpdateCheck.getVersion());
            super.ifignore = true;
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
}
