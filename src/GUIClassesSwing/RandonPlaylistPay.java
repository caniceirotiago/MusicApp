package src.GUIClassesSwing;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RandonPlaylistPay  extends JDialog {
    private JButton addToBasketbtn;
    private JButton buyMusicbtn;
    private JButton onlyFreebtn;
    private GUIManager guiManager;
    private int returnValue;

    public RandonPlaylistPay(GUIManager guiManager, Frame associated, ArrayList<String> songNames, double totalPrice, boolean canBuy){
        super (associated,"Paid Music", true);
        this.guiManager = guiManager;


        JPanel musicListPanel = new JPanel();
        musicListPanel.setLayout(new BoxLayout(musicListPanel, BoxLayout.Y_AXIS));
        musicListPanel.add(new Label("Paid Musics"));
        for(String m : songNames){
            musicListPanel.add(new JLabel(m));
        }
        musicListPanel.add(new JLabel("Total price: " + totalPrice));

        addToBasketbtn = new JButton("Add to Bascket");
        buyMusicbtn = new JButton("Buy Music");
        onlyFreebtn = new JButton("Only Free Music");

        buyMusicbtn.setEnabled(canBuy); // Se não tiver dinheiro o butão não  dá para clicar

        addToBasketbtn.addActionListener(e -> onAddToBasckeClickbtn());
        buyMusicbtn.addActionListener(e -> onBuyMusicbtnClick());
        onlyFreebtn.addActionListener(e -> onOnlyFreebtnClick());

        JPanel btnPanel = new JPanel();

        btnPanel.add(addToBasketbtn);
        btnPanel.add(buyMusicbtn);
        btnPanel.add(onlyFreebtn);

        getContentPane().add(new JScrollPane(musicListPanel),BorderLayout.CENTER);
        getContentPane().add(btnPanel,BorderLayout.SOUTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(associated);

        pack();
        setVisible(true);
    }
    private void onAddToBasckeClickbtn(){
        returnValue = 1;
        dispose();
    }
    private void onBuyMusicbtnClick(){
        returnValue = 2;
        dispose();
    }
    private void onOnlyFreebtnClick(){
        returnValue = 3;
        dispose();
    }
    public int getReturnValue() {
        return returnValue;
    }
}
