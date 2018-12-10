package org.botparty.annabelle;

import org.botparty.annabelle.domain.Script;
import org.botparty.annabelle.panels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,3));

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        JPanel scriptPanel = listPanel();

        panel1.add(scriptPanel);
        panel1.add(new EmotionsPanel());
        panel1.add(eyePanel());

        mainPanel.add(panel1);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

        JPanel scriptContentPanel = new ScriptContentPanel();
        panel2.add(scriptContentPanel);
        panel2.add(facePanel());
        mainPanel.add(panel2);

        JPanel miscPanel = miscPanel();
        mainPanel.add(miscPanel);
        this.add(mainPanel);

        createMenu();

        GraphicsDevice defaultDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        Rectangle screenBounds = defaultDevice.getDefaultConfiguration().getBounds();
        this.setSize(
                Math.round(screenBounds.width * .75f),
                Math.round(screenBounds.height * .75f));
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

    private JPanel miscPanel() {
        JPanel miscPanel = new JPanel();
     //   miscPanel.setLayout(new BoxLayout(miscPanel,BoxLayout.PAGE_AXIS));
        miscPanel.setLayout(new GridLayout(3,1));

        JPanel puppetPanel = puppetPanel();
        miscPanel.add(puppetPanel);

        JPanel favoritesPanel = new FavoritesPanel();
        miscPanel.add(favoritesPanel);

        JPanel historyPanel = historyPanel();
        miscPanel.add(historyPanel);

        return miscPanel;
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

            butt.addActionListener(e -> Controller.getInstance().send(
                    String.format("\\eye %s", e.getActionCommand())));

            group.add(butt);

            eyePanel.add(butt);
        }

        return eyePanel;
    }

    private JPanel facePanel() {
        JPanel facePanel = new JPanel();
        facePanel.setLayout(new BoxLayout(facePanel, BoxLayout.Y_AXIS));
        JComboBox<String> faces = new JComboBox<>(Data.getInstance().getEmotionList());
        faces.setSelectedIndex(0);
        faces.addActionListener(e -> {
            if (e.getSource() instanceof JComboBox) {
                JComboBox cb =  (JComboBox)e.getSource();
                String face = (String)cb.getSelectedItem();

                Controller.getInstance().send(String.format("\\face %s",face));
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
