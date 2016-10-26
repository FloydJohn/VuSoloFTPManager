package it.floydjohn.vusolo.utils;

import java.util.prefs.Preferences;

/**
 * Created by alessandro on 10/26/16.
 */
public class Settings {

    public static final String FTP_FIL = "ftp_fil";
    public static final String FTP_IP = "ftp_ip";
    public static final String FTP_USR = "ftp_usr";
    public static final String FTP_PWD = "ftp_pwd";
    public static final String FTP_PRT = "ftp_prt";
    public static final String WEB_PRT = "web_prt";

    private static Settings instance;

    private Settings() {
        setDefault();
    }

    public static Settings getInstance() {
        if (instance == null) instance = new Settings();
        return instance;
    }

    public void set(String prefName, String prefValue) {
        Preferences prefs = Preferences.userNodeForPackage(it.floydjohn.vusolo.utils.Settings.class);
        prefs.put(prefName, prefValue);
    }

    public String get(String prefName, String defaultValue) {
        Preferences prefs = Preferences.userNodeForPackage(it.floydjohn.vusolo.utils.Settings.class);
        return prefs.get(prefName, defaultValue);
    }

    public String get(String prefName) {
        return get(prefName, "");
    }

    private void setDefault() {
        if (get(FTP_FIL).equals("")) set(FTP_FIL, "/var/etc/CCcam.cfg");
        if (get(FTP_PRT).equals("")) set(FTP_PRT, "21");
        if (get(WEB_PRT).equals("")) set(WEB_PRT, "80");
    }


}
