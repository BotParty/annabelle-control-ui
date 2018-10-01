package org.botparty.annabelle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.botparty.annabelle.domain.Script;
import org.botparty.annabelle.panels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class AnnabelleFrame extends JFrame implements  ActionListener {

    private JMenuItem menuItemSave;
    private JMenuItem menuItemOpen;

    static AnnabelleFrame createAndShowGUI() {
        AnnabelleFrame frame = new AnnabelleFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setState(Frame.NORMAL);
        frame.setVisible(true);
        return frame;
    }

    private AnnabelleFrame() {

        GraphicsDevice defaultDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        Rectangle screenBounds = defaultDevice.getDefaultConfiguration().getBounds();
        this.setSize(
                Math.round(screenBounds.width * .75f),
                Math.round(screenBounds.height * .75f));

         int preferredPanelWidth = this.getWidth()/3;
         int preferredPanelHeight = this.getHeight()/3;

        this.setLayout(new GridBagLayout());

        GridBagConstraints scriptPanelConstraints = new GridBagConstraints();
        scriptPanelConstraints.gridx = 0;
        scriptPanelConstraints.gridy = 0;
        scriptPanelConstraints.gridwidth = 1;
        scriptPanelConstraints.gridheight = 1;
        scriptPanelConstraints.weightx = 0.5;
        scriptPanelConstraints.weighty = 0.5;
        scriptPanelConstraints.fill = GridBagConstraints.BOTH;

        JPanel scriptPanel = listPanel();
        scriptPanel.setPreferredSize(new Dimension(preferredPanelWidth, preferredPanelHeight));
        scriptPanel.setSize(scriptPanel.getPreferredSize());
        scriptPanel.setMaximumSize(scriptPanel.getPreferredSize());
        scriptPanel.setMinimumSize(scriptPanel.getPreferredSize());

        this.add(scriptPanel,scriptPanelConstraints);

        GridBagConstraints topNinePanelConstraints = new GridBagConstraints();
        topNinePanelConstraints.gridx = 0;
        topNinePanelConstraints.gridy = 1;
        topNinePanelConstraints.gridwidth = 1;
        topNinePanelConstraints.gridheight = 1;
        topNinePanelConstraints.weightx = 0.5;
        topNinePanelConstraints.weighty = 0.5;
        topNinePanelConstraints.fill = GridBagConstraints.BOTH;

        JPanel topNinePanel = new EmotionsButtonPanel(new Dimension(preferredPanelWidth,preferredPanelHeight));
        topNinePanel.setPreferredSize(new Dimension(preferredPanelWidth, preferredPanelHeight));
        topNinePanel.setSize(topNinePanel.getPreferredSize());
        topNinePanel.setMaximumSize(topNinePanel.getPreferredSize());
        topNinePanel.setMinimumSize(topNinePanel.getPreferredSize());

        this.add(topNinePanel, topNinePanelConstraints);

        GridBagConstraints facesPanelConstraints = new GridBagConstraints();
        facesPanelConstraints.gridx = 0;
        facesPanelConstraints.gridy = 2;
        facesPanelConstraints.gridwidth = 1;
        facesPanelConstraints.gridheight = 1;
        facesPanelConstraints.weightx = 0.5;
        facesPanelConstraints.weighty = 0.5;
        facesPanelConstraints.fill = GridBagConstraints.BOTH;

        JPanel facesPanel = eyePanel();
        facesPanel.setSize(facesPanel.getPreferredSize());

        this.add(facesPanel, facesPanelConstraints);

        GridBagConstraints scriptContentPanelConstraints = new GridBagConstraints();
        scriptContentPanelConstraints.gridx = 1;
        scriptContentPanelConstraints.gridy = 0;
        scriptContentPanelConstraints.gridwidth = 1;
        scriptContentPanelConstraints.gridheight = 2;
        scriptContentPanelConstraints.weightx = 0.5;
        scriptContentPanelConstraints.weighty = 0.5;
        scriptContentPanelConstraints.fill = GridBagConstraints.BOTH;


        ScriptContentPanel scriptContentPanel = new ScriptContentPanel();
        scriptContentPanel.setSize(scriptContentPanel.getPreferredSize());




        this.add(scriptContentPanel, scriptContentPanelConstraints);

        GridBagConstraints historyPanelConstraints = new GridBagConstraints();
        historyPanelConstraints.gridx = 1;
        historyPanelConstraints.gridy = 2;
        historyPanelConstraints.gridwidth = 1;
        historyPanelConstraints.gridheight = 1;
        historyPanelConstraints.weightx = 0.5;
        historyPanelConstraints.weighty = 0.5;
        historyPanelConstraints.fill = GridBagConstraints.BOTH;

        JPanel historyPanel = facePanel();
        historyPanel.setPreferredSize(new Dimension(preferredPanelWidth, preferredPanelHeight));
        historyPanel.setSize(historyPanel.getPreferredSize());
        historyPanel.setMaximumSize(historyPanel.getPreferredSize());
        historyPanel.setMinimumSize(historyPanel.getPreferredSize());


        this.add(historyPanel, historyPanelConstraints);

        GridBagConstraints chatTextPanelConstraints = new GridBagConstraints();
        chatTextPanelConstraints.gridx = 2;
        chatTextPanelConstraints.gridy = 0;
        chatTextPanelConstraints.gridwidth = 1;
        chatTextPanelConstraints.gridheight = 1;
        chatTextPanelConstraints.weightx = 0.5;
        chatTextPanelConstraints.weighty = 0.5;
        chatTextPanelConstraints.fill = GridBagConstraints.BOTH;

        JPanel chatTextPanel = puppetPanel();
        chatTextPanel.setSize(chatTextPanel.getPreferredSize());

        this.add(chatTextPanel, chatTextPanelConstraints);

        GridBagConstraints topEighteenPanelConstraints = new GridBagConstraints();
        topEighteenPanelConstraints.gridx = 2;
        topEighteenPanelConstraints.gridy = 1;
        topEighteenPanelConstraints.gridwidth = 1;
        topEighteenPanelConstraints.gridheight = 1;
        topEighteenPanelConstraints.weightx = 0.5;
        topEighteenPanelConstraints.weighty = 0.5;
        topEighteenPanelConstraints.fill = GridBagConstraints.BOTH;

        JPanel topEighteenPanel = new FavoritesPanel(new Dimension(preferredPanelWidth,preferredPanelHeight));
        topEighteenPanel.setPreferredSize(new Dimension(preferredPanelWidth, preferredPanelHeight));
        topEighteenPanel.setSize(topEighteenPanel.getPreferredSize());
        topEighteenPanel.setMaximumSize(topEighteenPanel.getPreferredSize());
        topEighteenPanel.setMinimumSize(topEighteenPanel.getPreferredSize());

        this.add(topEighteenPanel, topEighteenPanelConstraints);

        GridBagConstraints queuePanelConstraints = new GridBagConstraints();
        queuePanelConstraints.gridx = 2;
        queuePanelConstraints.gridy = 2;
        queuePanelConstraints.gridwidth = 1;
        queuePanelConstraints.gridheight = 1;
        queuePanelConstraints.weightx = 0.5;
        queuePanelConstraints.weighty = 0.5;
        queuePanelConstraints.fill = GridBagConstraints.BOTH;

        JPanel queuePanel = historyPanel();
        queuePanel.setSize(queuePanel.getPreferredSize());

        this.add(queuePanel, queuePanelConstraints);



        /*
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,3));

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        JPanel scriptPanel = listPanel();

        panel1.add(scriptPanel);
        panel1.add(emotionsButtonsPanel());
        panel1.add(eyePanel());

        mainPanel.add(panel1);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

        JPanel scriptContentPanel = scriptContentPanel();
        panel2.add(scriptContentPanel);
        panel2.add(facePanel());
        mainPanel.add(panel2);

        JPanel miscPanel = miscPanel();
        mainPanel.add(miscPanel);
        this.add(mainPanel);
*/
        createMenu();


   }

    private JPanel listPanel() {
        JPanel scriptPanel = new JPanel(new BorderLayout());

        JList<String> list = new JList<>();
        list.setModel(Data.getInstance().scriptModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);

        list.setSelectedIndex(0);
        list.getSelectionModel().addListSelectionListener(Controller.getInstance().scriptListController);

        JScrollPane scrollPane = new JScrollPane(list);

        scriptPanel.add(scrollPane, BorderLayout.CENTER);

        return scriptPanel;
    }

    private JPanel puppetPanel() {
        return PuppetPanel.create();
    }
    private JPanel historyPanel() {
        return HistoryPanel.create();
    }

    private JPanel eyePanel() {
        JPanel eyePanel = new JPanel();
        eyePanel.setLayout(new BoxLayout(eyePanel, BoxLayout.Y_AXIS));

        String[] eyeStates = {"blinking","open","closed","mid"};
        ButtonGroup group = new ButtonGroup();
        JLabel label = new JLabel("Eye Control");
        eyePanel.add(label);

        for (String state : eyeStates) {
            JRadioButton butt = new JRadioButton(state);
            butt.setActionCommand(state);
            if (state.equals("blinking")) butt.setSelected(true);

            butt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Controller.getInstance().send(
                            String.format("\\eye %s", e.getActionCommand()));
                }
            });

            group.add(butt);

            eyePanel.add(butt);
        }

        return eyePanel;
    }

    private JPanel facePanel() {
        JPanel facePanel = new JPanel();
        facePanel.setLayout(new BoxLayout(facePanel, BoxLayout.Y_AXIS));
        JComboBox faces = new JComboBox(Data.getInstance().getEmotionList());
        faces.setSelectedIndex(0);
        faces.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JComboBox) {
                    JComboBox cb =  (JComboBox)e.getSource();
                    String face = (String)cb.getSelectedItem();

                    Controller.getInstance().send(String.format("\\face %s",face));
                }
            }
        });

        JLabel label = new JLabel("Faces");
        facePanel.add(label);
        facePanel.add(faces);

        return facePanel;
    }


    private void createMenu() {
        menuItemSave = new JMenuItem("Save");
        menuItemSave.addActionListener(this);

        menuItemOpen = new JMenuItem("Open");
        menuItemOpen.addActionListener(this);

        JMenu menu = new JMenu("File");
        menu.add(menuItemSave);
        menu.add(menuItemOpen);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        this.setJMenuBar(menuBar);
    }

    private void save() {
        Set<String> files = Data.getInstance().scriptFileMap.keySet();
        for(String fileName: files) {
            // get title
            String title = Data.getInstance().scriptFileMap.get(fileName);
            Script script = Data.getInstance().getScripts().get(title);
            script.save(fileName);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == menuItemSave) {
            save();
        } else if(e.getSource() == menuItemOpen) {
            openFile();
        }
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to open");

        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {

            File fileToOpen = fileChooser.getSelectedFile();
            System.out.println("Open file: " + fileToOpen.getAbsolutePath());

            Data.getInstance().loadScriptList(fileToOpen);
        }
    }
}
