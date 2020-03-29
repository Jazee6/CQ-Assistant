import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
        System.out.println(df.format(day));
    }
}