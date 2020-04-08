package cn.jmzzz;

import javax.swing.JOptionPane;

import cn.jmzzz.tools.*;
import com.sobte.cqp.jcq.entity.Anonymous;
import com.sobte.cqp.jcq.entity.ICQVer;
import com.sobte.cqp.jcq.entity.IMsg;
import com.sobte.cqp.jcq.entity.IRequest;
import com.sobte.cqp.jcq.event.JcqAppAbstract;
import org.dtools.ini.*;

import java.io.File;
import java.io.IOException;

/**
 * 本文件是JCQ插件的主类<br>
 * <br>
 * <p>
 * 注意修改json中的class来加载主类，如不设置则利用appid加载，最后一个单词自动大写查找<br>
 * 例：appid(com.example.demo) 则加载类 com.example.Demo<br>
 * 文档地址： https://gitee.com/Sobte/JCQ-CoolQ <br>
 * 帖子：https://cqp.cc/t/37318 <br>
 * 辅助开发变量: {@link JcqAppAbstract#CQ CQ}({@link com.sobte.cqp.jcq.entity.CoolQ
 * 酷Q核心操作类}), {@link JcqAppAbstract#CC
 * CC}({@link com.sobte.cqp.jcq.entity 酷Q码操作类}), 具体功能可以查看文档
 */
public class Assistant extends JcqAppAbstract implements ICQVer, IMsg, IRequest, Runnable {

    String f = CQ.getAppDirectory() + "Sub.ini";
    String subtimedir = CQ.getAppDirectory() + "Time.ini";
    static String g = CQ.getAppDirectory() + "Group.ini";
    static String d = CQ.getAppDirectory() + "Data.ini";

    GE ge;

    /**
     * 打包后将不会调用 请不要在此事件中写其他代码
     *
     * @return 返回应用的ApiVer、Appid
     */
    public String appInfo() {
        // 应用AppID,规则见 http://d.cqp.me/Pro/开发/基础信息#appid
        String AppID = "cn.jmzzz.assistant";// 记住编译后的文件和json也要使用appid做文件名
        // 本函数【禁止】处理其他任何代码，以免发生异常情况。 如需执行初始化代码请在 startup 事件中执行（Type=1001）。

        return CQAPIVER + "," + AppID;
    }

    /**
     * 酷Q启动 (Type=1001)<br>
     * 本方法会在酷Q【主线程】中被调用。<br>
     * 请在这里执行插件初始化代码。<br>
     * 请务必尽快返回本子程序，否则会卡住其他插件以及主程序的加载。
     *
     * @return 请固定返回0
     */
    public int startup() {
        // 获取应用数据目录(无需储存数据时，请将此行注释)
        //appDirectory = CQ.getAppDirectory();
        // 返回如：D:\CoolQ\app\com.sobte.cqp.jcq\app\com.example.demo\
        // 应用的所有数据、配置【必须】存放于此目录，避免给用户带来困扰。

        return 0;
    }

    /**
     * 酷Q退出 (Type=1002)<br>
     * 本方法会在酷Q【主线程】中被调用。<br>
     * 无论本应用是否被启用，本函数都会在酷Q退出前执行一次，请在这里执行插件关闭代码。
     *
     * @return 请固定返回0，返回后酷Q将很快关闭，请不要再通过线程等方式执行其他代码。
     */
    public int exit() {
        return 0;
    }

    /**
     * 应用已被启用 (Type=1003)<br>
     * 当应用被启用后，将收到此事件。<br>
     * 如果酷Q载入时应用已被启用，则在 {@link #startup startup}(Type=1001,酷Q启动)
     * 被调用后，本函数也将被调用一次。<br>
     * 如非必要，不建议在这里加载窗口。
     *
     * @return 请固定返回0。
     */
    public int enable() {
        enable = true;

        File file = new File(subtimedir);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    CQ.logDebug("初始化", "进度50%");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        IniFile iniFile = new BasicIniFile();
        IniFileReaderU rad = new IniFileReaderU(iniFile, file);
        IniFileWriterU wir = new IniFileWriterU(iniFile, file);
        try {
            rad.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!iniFile.hasSection("Time")) {
            IniSection iniSection = iniFile.addSection("Time");
            IniItem iniItem = iniSection.addItem("h");
            iniItem.setValue("8");
            IniItem iniItem2 = iniSection.addItem("m");
            iniItem2.setValue("0");
        }
        try {
            wir.write();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file1 = new File(f);
        if (!file1.exists()) {
            try {
                if (file1.createNewFile()) {
                    CQ.logDebug("初始化", "进度97%");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        IniFile iniFile1 = new BasicIniFile();
        IniFileReaderU rad1 = new IniFileReaderU(iniFile1, file1);
        IniFileWriterU wir1 = new IniFileWriterU(iniFile1, file1);
        try {
            rad1.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!iniFile1.hasSection("Hito")) {
            iniFile1.addSection("Hito");
        }
        if (!iniFile1.hasSection("Soc")) {
            iniFile1.addSection("Soc");
        }
        if (!iniFile1.hasSection("Call")) {
            iniFile1.addSection("Call");
        }
        try {
            wir1.write();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file2 = new File(g);
        if (!file2.exists()) {
            try {
                if (file2.createNewFile()) {
                    CQ.logDebug("初始化", "进度98%");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File file3 = new File(d);
        if (!file3.exists()) {
            try {
                if (file3.createNewFile()) {
                    CQ.logDebug("初始化", "进度99%");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //以下为定时任务
        TimeTask task = new TimeTask();
        try {
            task.sendSub();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 应用将被停用 (Type=1004)<br>
     * 当应用被停用前，将收到此事件。<br>
     * 如果酷Q载入时应用已被停用，则本函数【不会】被调用。<br>
     * 无论本应用是否被启用，酷Q关闭前本函数都【不会】被调用。
     *
     * @return 请固定返回0。
     */
    public int disable() {
        enable = false;
        return 0;
    }

    /**
     * 私聊消息 (Type=21)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subType 子类型，11/来自好友 1/来自在线状态 2/来自群 3/来自讨论组
     * @param msgId   消息ID
     * @param fromQQ  来源QQ
     * @param msg     消息内容
     * @param font    字体
     * @return 返回值*不能*直接返回文本 如果要回复消息，请调用api发送<br>
     * 这里 返回 {@link IMsg#MSG_INTERCEPT MSG_INTERCEPT} - 截断本条消息，不再继续处理<br>
     * 注意：应用优先级设置为"最高"(10000)时，不得使用本返回值<br>
     * 如果不回复消息，交由之后的应用/过滤器处理，这里 返回 {@link IMsg#MSG_IGNORE MSG_IGNORE} -
     * 忽略本条消息
     */
    public int privateMsg(int subType, int msgId, long fromQQ, String msg, int font) {
        // 这里处理消息
//		CQ.sendPrivateMsg(fromQQ, "你发送了这样的消息：" + msg + "\n来自Java插件");

        new Thread(() -> {
            PersonRespond.sendMenu(msg, fromQQ);
            PersonRespond.sendSubscriptionList(msg, fromQQ);
            PersonRespond.sendSocial(msg, fromQQ);
            PersonRespond.sendHitokoto(msg, fromQQ);
            PersonRespond.sendAbout(msg, fromQQ);
            PersonRespond.sendFunctionList(msg, fromQQ);
            PersonRespond.sendRespond(msg, fromQQ);
            PersonRespond.sendFeedback(msg, fromQQ);
            PersonRespond.sendRes(msg, fromQQ);

            //以下为非静态
            PersonRespond personRespond = new PersonRespond();
            try {
                personRespond.sendSubCancel(msg, fromQQ);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        return MSG_IGNORE;
    }

    /**
     * 群消息 (Type=2)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subType       子类型，目前固定为1
     * @param msgId         消息ID
     * @param fromGroup     来源群号
     * @param fromQQ        来源QQ号
     * @param fromAnonymous 来源匿名者
     * @param msg           消息内容
     * @param font          字体
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    public int groupMsg(int subType, int msgId, long fromGroup, long fromQQ, String fromAnonymous, String msg,
                        int font) {
        // 如果消息来自匿名者
        if (fromQQ == 80000000L && !fromAnonymous.equals("")) {
            // 将匿名用户信息放到 anonymous 变量中
            @SuppressWarnings("unused")
            Anonymous anonymous = CQ.getAnonymous(fromAnonymous);
        }
        // 解析CQ码案例 如：[CQ:at,qq=100000]
        // 解析CQ码 常用变量为 CC(CQCode) 此变量专为CQ码这种特定格式做了解析和封装
        // CC.analysis();// 此方法将CQ码解析为可直接读取的对象
        // 解析消息中的QQID
        // long qqId = CC.getAt(msg);// 此方法为简便方法，获取第一个CQ:at里的QQ号，错误时为：-1000
        // List<Long> qqIds = CC.getAts(msg); // 此方法为获取消息中所有的CQ码对象，错误时返回 已解析的数据
        // 解析消息中的图片
        // CQImage image = CC.getCQImage(msg);//
        // 此方法为简便方法，获取第一个CQ:image里的图片数据，错误时打印异常到控制台，返回 null
        // List<CQImage> images = CC.getCQImages(msg);//
        // 此方法为获取消息中所有的CQ图片数据，错误时打印异常到控制台，返回 已解析的数据

        // 这里处理消息
        // CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + "你发送了这样的消息：" + msg +
        // "\n来自Java插件");

        new Thread(() -> {
            GroupRespond.sendRespond(msg, fromGroup, fromQQ);
            GroupRespond.sendMenu(msg, fromGroup);
            GroupRespond.sendHitokoto(msg, fromGroup);
            GroupRespond.sendSocial(msg, fromGroup);
            try {
                GE.openGE(msg, fromGroup, fromQQ);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


        //以下为非静态方法

        if (ge == null) {
            ge = new GE();
        }
        try {
            ge.sendGE(fromGroup, fromQQ);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return MSG_IGNORE;
    }

    /**
     * 讨论组消息 (Type=4)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subtype     子类型，目前固定为1
     * @param msgId       消息ID
     * @param fromDiscuss 来源讨论组
     * @param fromQQ      来源QQ号
     * @param msg         消息内容
     * @param font        字体
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    public int discussMsg(int subtype, int msgId, long fromDiscuss, long fromQQ, String msg, int font) {
        // 这里处理消息

        return MSG_IGNORE;
    }

    /**
     * 群文件上传事件 (Type=11)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subType   子类型，目前固定为1
     * @param sendTime  发送时间(时间戳)// 10位时间戳
     * @param fromGroup 来源群号
     * @param fromQQ    来源QQ号
     * @param file      上传文件信息
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    public int groupUpload(int subType, int sendTime, long fromGroup, long fromQQ, String file) {
        //GroupFile groupFile = CQ.getGroupFile(file);
        //if (groupFile == null) { // 解析群文件信息，如果失败直接忽略该消息
        //  return MSG_IGNORE;
        //}
        // 这里处理消息
        return MSG_IGNORE;
    }

    /**
     * 群事件-管理员变动 (Type=101)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subtype        子类型，1/被取消管理员 2/被设置管理员
     * @param sendTime       发送时间(时间戳)
     * @param fromGroup      来源群号
     * @param beingOperateQQ 被操作QQ
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    public int groupAdmin(int subtype, int sendTime, long fromGroup, long beingOperateQQ) {
        // 这里处理消息

        return MSG_IGNORE;
    }

    /**
     * 群事件-群成员减少 (Type=102)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subtype        子类型，1/群员离开 2/群员被踢
     * @param sendTime       发送时间(时间戳)
     * @param fromGroup      来源群号
     * @param fromQQ         操作者QQ(仅子类型为2时存在)
     * @param beingOperateQQ 被操作QQ
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    public int groupMemberDecrease(int subtype, int sendTime, long fromGroup, long fromQQ, long beingOperateQQ) {
        // 这里处理消息

        return MSG_IGNORE;
    }

    /**
     * 群事件-群成员增加 (Type=103)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subtype        子类型，1/管理员已同意 2/管理员邀请
     * @param sendTime       发送时间(时间戳)
     * @param fromGroup      来源群号
     * @param fromQQ         操作者QQ(即管理员QQ)
     * @param beingOperateQQ 被操作QQ(即加群的QQ)
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    public int groupMemberIncrease(int subtype, int sendTime, long fromGroup, long fromQQ, long beingOperateQQ) {
        // 这里处理消息
        return MSG_IGNORE;
    }

    /**
     * 好友事件-好友已添加 (Type=201)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subtype  子类型，目前固定为1
     * @param sendTime 发送时间(时间戳)
     * @param fromQQ   来源QQ
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    public int friendAdd(int subtype, int sendTime, long fromQQ) {
        // 这里处理消息
        return MSG_IGNORE;
    }

    /**
     * 请求-好友添加 (Type=301)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subtype      子类型，目前固定为1
     * @param sendTime     发送时间(时间戳)
     * @param fromQQ       来源QQ
     * @param msg          附言
     * @param responseFlag 反馈标识(处理请求用)
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    public int requestAddFriend(int subtype, int sendTime, long fromQQ, String msg, String responseFlag) {
        // 这里处理消息
        // REQUEST_ADOPT 通过 REQUEST_REFUSE 拒绝

        CQ.setFriendAddRequest(responseFlag, REQUEST_ADOPT, ""); // 同意好友添加请求
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CQ.sendPrivateMsg(fromQQ, "感谢添加，回复“菜单”获取更多内容");
        return MSG_IGNORE;
    }

    /**
     * 请求-群添加 (Type=302)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subtype      子类型，1/他人申请入群 2/自己(即登录号)受邀入群
     * @param sendTime     发送时间(时间戳)
     * @param fromGroup    来源群号
     * @param fromQQ       来源QQ
     * @param msg          附言
     * @param responseFlag 反馈标识(处理请求用)
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    public int requestAddGroup(int subtype, int sendTime, long fromGroup, long fromQQ, String msg,
                               String responseFlag) {
        // 这里处理消息
        /*
          REQUEST_ADOPT 通过 REQUEST_REFUSE 拒绝 REQUEST_GROUP_ADD 群添加 REQUEST_GROUP_INVITE
          群邀请
         */
        /*
         * if(subtype == 1){ // 本号为群管理，判断是否为他人申请入群 CQ.setGroupAddRequest(responseFlag,
         * REQUEST_GROUP_ADD, REQUEST_ADOPT, null);// 同意入群 } if(subtype == 2){
         * CQ.setGroupAddRequest(responseFlag, REQUEST_GROUP_INVITE, REQUEST_ADOPT,
         * null);// 同意进受邀群 }
         */
        if (subtype == 2 && fromQQ == AppInfo.getAdmin()) {
            CQ.setGroupAddRequestV2(responseFlag, REQUEST_GROUP_INVITE, REQUEST_ADOPT, null);
        }
        return MSG_IGNORE;
    }

    /**
     * 本函数会在JCQ【线程】中被调用。
     *
     * @return 固定返回0
     */
    public int menuA() {
        JOptionPane.showMessageDialog(null, "设置窗口还在开发中");
        new SetWindow();
        return 0;
    }

    /**
     * 本函数会在酷Q【线程】中被调用。
     *
     * @return 固定返回0
     */
    public int menuB() {
        JOptionPane.showMessageDialog(null, "关于窗口还在开发中");
        return 0;
    }

    @Override
    public void run() {

    }
}
