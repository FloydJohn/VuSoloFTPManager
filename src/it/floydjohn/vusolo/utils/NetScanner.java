package it.floydjohn.vusolo.utils;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by alessandro on 11/1/16.
 */
public class NetScanner {
    private static NetScanner instance = new NetScanner();

    private NetScanner() {
    }

    public static NetScanner getInstance() {
        if (instance == null) instance = new NetScanner();
        return instance;
    }

    /**
     * Scan the subnet in search of decoder.
     *
     * @param subnet Subnet to scan (for example 192.168.1 for all IPs in 192.168.1.*)
     * @return IP address of decoder if found, null otherwise.
     */
    public String scan(String subnet) {
        int timeout = 200;
        for (int i = 1; i < 255; i++) {
            String host = subnet + "." + i;
            System.out.println("[NetScanner#scan] Scanning " + host);
            try {
                if (InetAddress.getByName(host).isReachable(timeout)) {
                    System.out.println("[NetScanner#scan] HIT " + InetAddress.getByName(host).getHostName());
                    if (InetAddress.getByName(host).getHostName().contains("vusolo")) return host;
                }
            } catch (IOException ignored) {
            }
        }
        return null;
    }
}
