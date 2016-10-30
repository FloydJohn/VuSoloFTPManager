package it.floydjohn.vusolo.gui.components;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by alessandro on 10/30/16.
 */
public class CopyPastePopUp extends JPopupMenu {

    private JMenuItem copy, paste;

    public CopyPastePopUp() {
        copy = new JMenuItem("Copy");
        paste = new JMenuItem("Paste");
        add(copy);
        add(paste);
    }

    public void setCopyAction(ActionListener a) {
        copy.addActionListener(a);
    }

    public void setPasteAction(ActionListener a) {
        paste.addActionListener(a);
    }

}
