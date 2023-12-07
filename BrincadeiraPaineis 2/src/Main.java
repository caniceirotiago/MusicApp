import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Main {
    private final static int dimH =500;
    private final static int dimV = 500;
    public static void main(String[] args) throws IOException{
        JFrame jan = new JFrame();
        jan.setSize(dimH,dimV);
        jan.setTitle("Janela");
        jan.setBackground(Color.gray);
        jan.setCursor(Cursor.getDefaultCursor());
        jan.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Botoes but = new Botoes();
        JLabel background =  new JLabel(new ImageIcon("/Users/utilizador/Desktop/backgroundImage.png"));
        Container c = jan.getContentPane();//superficie de desenho/painel
        c.add(but, "South");
        c.add(background);
        jan.pack();
        jan.setLocationRelativeTo(null);
        jan.setVisible(true);
    }

}