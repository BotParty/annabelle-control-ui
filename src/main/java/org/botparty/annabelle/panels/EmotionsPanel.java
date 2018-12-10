package org.botparty.annabelle.panels;

import org.botparty.annabelle.Controller;
import org.botparty.annabelle.Data;

import javax.swing.*;
import java.awt.*;

public class EmotionsPanel extends JPanel {
    public EmotionsPanel() {
        this.setLayout(new GridLayout(2,3));

        String[] emotionList = Data.getInstance().getEmotionList();
        int i = 0;
        for(String emotion : emotionList) {
            if(i >= 6) break;
            JButton button = new JButton(emotion);
            button.addActionListener(
                    e -> Controller.getInstance().send(String.format("\\face %s",emotion))
            );
            this.add(button);
            i++;
        }
    }
}
