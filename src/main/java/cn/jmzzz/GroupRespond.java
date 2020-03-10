package cn.jmzzz;

import cn.jmzzz.tools.AppInfo;
import cn.jmzzz.tools.HttpGet;
import cn.jmzzz.tools.UpdateCheck;
import com.alibaba.fastjson.JSONObject;

import java.util.Random;

public class GroupRespond extends Assistant {
    String s;
    //String apiofficial = "https://www.jmzzz.cn/api/";

    private String getRespondString() {
        Random random = new Random();
        int r1 = random.nextInt(2);
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

    public void sendRespond(String msg, long fromGroup, long fromQQ) {
        boolean b = false;
        if (msg.contains("泽卡")) {
            b = true;
        } else if (msg.contains("机器人")) {
            b = true;
        }
        if (b && msg.length() <= 5) {
            CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + getRespondString());
        }
    }

    public void sendHitokoto(String msg, long fromGroup) {
        if (msg.equals("一言")) {
            s = HttpGet.doGet("https://v1.hitokoto.cn/");
            JSONObject object = JSONObject.parseObject(s);
            CQ.sendGroupMsg(fromGroup, object.getString("hitokoto"));
        }
    }

    public void updateCheck(String msg, long fromGroup) {
        if (msg.equals("检查更新")) {
            UpdateCheck updateCheck = new UpdateCheck();
            if (updateCheck.checkUpdate()) {
                CQ.sendPrivateMsg(fromGroup, "有新版本啦！\n最新版本：" + updateCheck.getNewver() + "\n\n" + HttpGet.doGet(AppInfo.getApiOfficial() + "A_info.txt"));
            } else
                CQ.sendPrivateMsg(fromGroup, "暂无新版本~\n当前版本：" + AppInfo.getAppVer() + "\n\n" + AppInfo.getUpdateInfo());
        }
    }


    @Override
    public int menuA() {
        return super.menuA();
    }

    @Override
    public int menuB() {
        return super.menuB();
    }
}