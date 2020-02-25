import cn.jmzzz.HttpGet;
import com.alibaba.fastjson.JSONObject;

public class Test {
    public static void main(String[] args) {
        //String apiofficial = "https://www.jmzzz.cn/api/";
        String s= HttpGet.doGet("https://v1.hitokoto.cn/");
        JSONObject object= JSONObject.parseObject(s);
        System.out.println(object.getString("hitokoto"));
    }
}
