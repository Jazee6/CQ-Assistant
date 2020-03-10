package cn.jmzzz.tools;

public class UpdateCheck {

    String version = AppInfo.getAppVer();
    String newver;
    int newver1;
    int newver2;
    int newver3;
    int ver1;
    int ver2;
    int ver3;

    public boolean checkUpdate() {

        ver1 = Integer.parseInt(version.substring(0, 1));
        ver2 = Integer.parseInt(version.substring(2, 3));
        ver3 = Integer.parseInt(version.substring(4, 5));
        newver = HttpGet.doGet(AppInfo.getApiOfficial() + "A_ver.txt");

        newver1 = Integer.parseInt(newver.substring(0, 1));
        newver2 = Integer.parseInt(newver.substring(2, 3));
        newver3 = Integer.parseInt(newver.substring(4, 5));

        if (ver1 < newver1) {
            return true;
        } else if (ver2 < newver2) {
            return true;
        } else return ver3 < newver3;
    }

    public String getNewver() {
        return newver;
    }
}
