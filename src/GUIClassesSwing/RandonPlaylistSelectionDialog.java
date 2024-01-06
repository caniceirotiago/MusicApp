package src.GUIClassesSwing;

import src.rockstar.model.data.Music;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 */
public class RandonPlaylistSelectionDialog extends JDialog {
    private JButton addToBasketbtn;
    private JButton buyMusicbtn;
    private JButton onlyFreebtn;
    private GUIManager guiManager;
    private int returnValue;

    /**
     *
     * @param guiManager
     * @param associated
     * @param songNames
     * @param totalPrice
     * @param canBuy
     */
    public RandonPlaylistSelectionDialog(GUIManager guiManager, Frame associated, ArrayList<Music> songNames, double totalPrice, boolean canBuy){
        super (associated,"Paid Music", true);
        this.guiManager = guiManager;


        JPanel musicListPanel = new JPanel();

        musicListPanel.setLayout(new BoxLayout(musicListPanel, BoxLayout.Y_AXIS));
        musicListPanel.add(new Label("Some of the chosen songs are paid"));
        for(Music m : songNames){
            musicListPanel.add(new JLabel(m.toString()));
        }
        musicListPanel.add(new JLabel("Total price: " + totalPrice));

        addToBasketbtn = new JButton("Add to Basket");
        buyMusicbtn = new JButton("Buy Music");
        onlyFreebtn = new JButton("Only Free Music");

        buyMusicbtn.setEnabled(canBuy); // Se não tiver dinheiro o butão não  dá para clicar

        addToBasketbtn.addActionListener(e -> onAddToBasckeClickbtn());
        buyMusicbtn.addActionListener(e -> onBuyMusicbtnClick());
        onlyFreebtn.addActionListener(e -> onOnlyFreebtnClick());

        JScrollPane scrollPane =  new JScrollPane(musicListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel btnPanel = new JPanel();

        btnPanel.add(addToBasketbtn);
        btnPanel.add(buyMusicbtn);
        btnPanel.add(onlyFreebtn);

        getContentPane().add(scrollPane,BorderLayout.CENTER);
        getContentPane().add(btnPanel,BorderLayout.SOUTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(associated);

        setPreferredSize(new Dimension(450,400));
        setResizable(false);
        pack();
        setVisible(true);
    }

    /**
     *
     */
    private void onAddToBasckeClickbtn(){
        returnValue = 1;
        dispose();
    }

    /**
     *
     */
    private void onBuyMusicbtnClick(){
        returnValue = 2;
        dispose();
    }

    /**
     *
     */
    private void onOnlyFreebtnClick(){
        returnValue = 3;
        dispose();
    }

    /**
     *
     * @return
     */
    public int getReturnValue() {
        return returnValue;
    }
}
