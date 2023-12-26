package src.GUIClassesSwing;

import src.RockStar.Music;
import src.RockStar.RockstarIncManager;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;

public class EditMusic extends JDialog {

    private GUIManager guiManager;

    private JTextField newName;
    private JComboBox<RockstarIncManager.GENRE> selectedGender;
    private JTextField newPriceTF;
    private JComboBox<String> musicStateCombo;

    public EditMusic(GUIManager guiManager, Frame associated, Music music){
        super (associated,"Edit Music", true);
        this.guiManager = guiManager;

        String musicState;
        if(music.isActive()) musicState = "active";
        else musicState = "inactive";

        String songDataString = music.getName() + " - " + music.getGenre() + " - " +
                music.getPrice() + "€ - " + musicState;
        JLabel songData = new JLabel(songDataString);
        JLabel newTitle = new JLabel("New Title");
        newName = new JTextField(20);

        RockstarIncManager.GENRE[] genres = RockstarIncManager.GENRE.values();
        selectedGender = new JComboBox<>(genres);

        JLabel newPrice = new JLabel("New Price");
        newPriceTF = new JTextField(20);

        String[] state = {"Active", "Inactive"};
        musicStateCombo = new JComboBox<>(state);

        JButton confirmationBtn = new JButton("Confirm");
        confirmationBtn.addActionListener(e -> onConfirmationClick());

        GridBagConstraints ce = new GridBagConstraints();
        JPanel centerPanel = new JPanel(new GridBagLayout());

        ce.gridx= GridBagConstraints.REMAINDER; //Ocupa o restante espaço na linha
        ce.gridy = GridBagConstraints.RELATIVE; // O compunente é colucado na linha seguinte do ultimo compunente
        ce.gridwidth = GridBagConstraints.REMAINDER;

        centerPanel.add(songData,ce);
        centerPanel.add(newTitle,ce);
        centerPanel.add(newName,ce);
        centerPanel.add(selectedGender,ce);
        centerPanel.add(newPrice,ce);
        centerPanel.add(newPriceTF,ce);
        centerPanel.add(musicStateCombo,ce);

        getContentPane().add(centerPanel,BorderLayout.CENTER);
        getContentPane().add(confirmationBtn,BorderLayout.SOUTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(associated);
        setPreferredSize(new Dimension(300,300));
        setResizable(false);
        pack();
        setVisible(true);
    }
    private void onConfirmationClick(){
        dispose();
    }

    public String getNewName() {
        return newName.getText();
    }

    public RockstarIncManager.GENRE getSelectedGender() {
        return selectedGender.getItemAt(selectedGender.getSelectedIndex());
    }

    public String getNewPrice() {
        return newPriceTF.getText();
    }

    public int getMusicState() {
        return musicStateCombo.getSelectedIndex();
    }
}
