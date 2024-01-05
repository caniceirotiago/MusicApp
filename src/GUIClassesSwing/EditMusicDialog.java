package src.GUIClassesSwing;

import src.RockStar.Genre;
import src.RockStar.Music;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class EditMusicDialog extends JDialog {

    private GUIManager guiManager;

    private JTextField newName;
    private JComboBox<Genre.GENRE> selectedGender;
    private JTextField newPriceTF;
    private JComboBox<String> musicStateCombo;

    /**
     *
     * @param guiManager
     * @param associated
     * @param music
     */
    public EditMusicDialog(GUIManager guiManager, Frame associated, Music music){
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

        //Makes the selected item on the ComboBox the original music genre
        Genre.GENRE[] genres = Genre.GENRE.values();
        int indexCount = 0;
        int selectedIndex = 0;
        for(Genre.GENRE g : genres){
            if(g == music.getGenre()) selectedIndex = indexCount;
            indexCount++;
        }
        selectedGender = new JComboBox<>(genres);
        selectedGender.setSelectedIndex(selectedIndex);

        JLabel newPrice = new JLabel("New Price");
        newPriceTF = new JTextField(20);

        //Makes the selected item on the ComboBox the original state
        String[] state = {"Active", "Inactive"};
        musicStateCombo = new JComboBox<>(state);
        if(music.isActive()) musicStateCombo.setSelectedIndex(0);
        else musicStateCombo.setSelectedIndex(1);

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

    public Genre.GENRE getSelectedGender() {
        return selectedGender.getItemAt(selectedGender.getSelectedIndex());
    }

    public String getNewPrice() {
        return newPriceTF.getText();
    }

    public int getMusicState() {
        return musicStateCombo.getSelectedIndex();
    }
}
