package src.GUIClassesSwing;

import src.RockStar.Music;
import src.RockStar.MusicAcquisition;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Classe que faz a gestão da janela de diálogo que é chamada quando o utilizador Cliente quer criar uma nova
 * playlist aleatória.
 */
public class HistoricPurchaseDialog extends JDialog {
    private GUIManager guiManager;
    /**
     * A classe que faz gestão da caixa de diálogo para apresentação do histórico de compras.
     * @param guiManager O gestor da interface gráfica responsável pela comunicação com o sistema.
     * @param associated O quadro associado à caixa de diálogo.
     * @param musicAquisitions Lista de aquisições.
     */
    public HistoricPurchaseDialog(GUIManager guiManager, Frame associated, ArrayList<MusicAcquisition> musicAquisitions){
        super (associated,"Historic Purchase", true);
        this.guiManager = guiManager;
        JPanel acquisitionPanel = new JPanel();

        acquisitionPanel.setLayout(new BoxLayout(acquisitionPanel, BoxLayout.Y_AXIS));
        for(MusicAcquisition ma : musicAquisitions){
            acquisitionPanel.add(new JLabel(ma.toString()));
        }
        JScrollPane scrollPane =  new JScrollPane(acquisitionPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        getContentPane().add(scrollPane,BorderLayout.CENTER);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(associated);

        setMaximumSize(new Dimension(400, 400));
        setResizable(false);
        pack();
        setVisible(true);
    }
}
