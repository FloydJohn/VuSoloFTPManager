package it.floydjohn.vusolo.settings;

import java.util.prefs.Preferences;

public enum Setting {

    FTP_IP("", "", Type.DO_NOT_SHOW),
    FTP_USR("", "", Type.DO_NOT_SHOW),
    FTP_PWD("", "", Type.DO_NOT_SHOW),
    FTP_FIL("Remote file", "/var/etc/CCcam.cfg", Type.Connection),
    FTP_PRT("FTP Port", "21", Type.Connection),
    WEB_PRT("WebView Port", "80", Type.Connection),
    SCN_RNG("IP Range", "192.168.1", Type.Scan),
    SCN_NME("Host Name", "vusolo", Type.Scan);

    private final String desc, deft;
    private String value;
    private Type type;

    Setting(String desc, String deft, Type type) {
        this.desc = desc;
        this.deft = deft;
        this.type = type;
        this.value = Preferences.userNodeForPackage(Setting.class).get(getName(), deft);
    }

    private String getName() {
        return this.name().toLowerCase();
    }

    public String getDesc() {
        return desc;
    }

    public String getDeft() {
        return deft;
    }

    public Type getType() {
        return type;
    }

    public String v() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        Preferences.userNodeForPackage(Setting.class).put(getName(), value);
    }

    public enum Type {
        Connection, Scan, DO_NOT_SHOW
    }
}
