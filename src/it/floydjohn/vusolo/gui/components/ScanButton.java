package it.floydjohn.vusolo.gui.components;

import it.floydjohn.vusolo.gui.panels.InfoPanel;
import it.floydjohn.vusolo.utils.NetScanner;
import it.floydjohn.vusolo.utils.ObservableInterface;
import it.floydjohn.vusolo.utils.ObserverInterface;

import javax.swing.*;

import static it.floydjohn.vusolo.gui.components.ScanButton.State.Scan;
import static it.floydjohn.vusolo.gui.components.ScanButton.State.Stop;

public class ScanButton extends JButton implements ObserverInterface {

    private State state;

    public ScanButton() {
        NetScanner.addObserver(this);
        updateState();
        this.addActionListener(ActionEvent -> {
            switch (getState()) {
                case Scan:
                    NetScanner.getInstance().start();
                    InfoPanel.getInstance().update("Started scan!", false);
                    break;
                case Stop:
                    NetScanner.getInstance().terminate();
                    InfoPanel.getInstance().update("Stopped scan!", false);
                    break;
            }
            updateState();
        });
    }

    @Override
    public void update(ObservableInterface ob, Object o) {
        updateState();
    }

    private State getState() {
        return state;
    }

    private void updateState() {
        this.state = NetScanner.getInstance().isRunning() ? Stop : Scan;
        switch (state) {
            case Scan:
                this.setText("Scan");
                break;
            case Stop:
                this.setText("Stop");
                break;
        }
    }

    enum State {
        Scan, Stop
    }


}
