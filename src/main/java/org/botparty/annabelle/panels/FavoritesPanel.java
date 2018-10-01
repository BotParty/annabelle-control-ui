package org.botparty.annabelle.panels;

import org.botparty.annabelle.Controller;
import org.botparty.annabelle.Data;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class FavoritesPanel extends JPanel{
    public FavoritesPanel(Dimension preferredDimensions) {
        int preferredButtonWidth = preferredDimensions.width/3;
        int preferredButtonHeight = preferredDimensions.height/3;

        this.setLayout(new GridLayout(3,3));

        Set<String> keySet = Data.getInstance().getFavorites().getFavorites().keySet();
        for(String key : keySet) {
            JButton button = new JButton(key);
            button.addActionListener(Controller.getInstance().getFavoritesController());
            button.setPreferredSize(new Dimension(preferredButtonWidth,preferredButtonHeight));
            button.setSize(button.getPreferredSize());
            button.setMaximumSize(button.getPreferredSize());
            this.add(button);
        }
    }
}
