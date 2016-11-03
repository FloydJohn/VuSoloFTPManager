package it.floydjohn.vusolo.utils;

import it.floydjohn.vusolo.gui.panels.InfoPanel;
import it.floydjohn.vusolo.settings.Setting;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class NetScanner extends Thread implements ObservableInterface {
    private static NetScanner instance = new NetScanner();

    private static ArrayList<ObserverInterface> observers = new ArrayList<>();
    private volatile boolean running = false;

    private NetScanner() {
    }

    public static NetScanner getInstance() {
        if (instance == null) instance = new NetScanner();
        return instance;
    }

    public static void addObserver(ObserverInterface o) {
        if (o != null) observers.add(o);
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        running = true;
        int timeout = 200;
        for (int i = 1; i < 255; i++) {
            if (!running) {
                NetScanner.instance = null;
                return;
            }
            String host = Setting.SCN_RNG.v() + "." + i;
            InfoPanel.getInstance().update("Scanning " + host, false);
            try {
                if (InetAddress.getByName(host).isReachable(timeout)) {
                    if (InetAddress.getByName(host).getHostName().contains(Setting.SCN_NME.v())) {
                        running = false;
                        InfoPanel.getInstance().update("Found IP for: " + InetAddress.getByName(host).getHostName(), false);
                        notifyObservers(host);
                    }
                }
            } catch (IOException ignored) {
            }
        }
        running = false;
        InfoPanel.getInstance().update("Host not found.", true);
        notifyObservers(null);
    }

    @Override
    public void notifyObservers(Object o) {
        for (ObserverInterface observer : observers)
            observer.update(this, o);
    }

    public void terminate() {
        running = false;
    }
}
