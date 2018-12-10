package org.botparty.annabelle.panels;

import org.botparty.annabelle.Controller;
import org.botparty.annabelle.Data;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class FavoritesPanel extends JPanel {

    public FavoritesPanel() {
        this.setLayout(new GridLayout(3,3));

        Set<String> keySet = Data.getInstance().getFavorites().getFavorites().keySet();
        for(String key : keySet) {
            JButton button = new JButton(key);
            button.addActionListener(Controller.getInstance().getFavoritesController());
            this.add(button);
        }
    }

}
