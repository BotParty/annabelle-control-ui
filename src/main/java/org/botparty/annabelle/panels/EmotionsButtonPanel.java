package org.botparty.annabelle.panels;

import org.botparty.annabelle.Controller;
import org.botparty.annabelle.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmotionsButtonPanel extends JPanel {

    public EmotionsButtonPanel(Dimension preferredDimensions) {
        this.setLayout(new GridLayout(2,3));

        int preferredButtonWidth = preferredDimensions.width/3;
        int preferredButtonHeight = preferredDimensions.height/2;

        String[] emotionList = Data.getInstance().getEmotionList();
        int i = 0;
        for(String emotion : emotionList) {
            if(i >= 6) break;
            JButton button = new JButton(emotion);
            button.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Controller.getInstance().send(String.format("\\face %s",emotion));
                        }
                    }
            );
            button.setPreferredSize(new Dimension(preferredButtonWidth,preferredButtonHeight));
            button.setSize(button.getPreferredSize());
            button.setMaximumSize(button.getPreferredSize());
            this.add(button);
            i++;
        }
    }
}
