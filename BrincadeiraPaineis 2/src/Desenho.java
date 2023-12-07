import java.awt.*;
import javax.swing.*;
public class Desenho extends JPanel{
    protected int cx, cy, comp, larg;
    protected Color color;
    //Construtor inicializa as variaveis da instancia
    public Desenho(int cx, int cy, int comp, int larg, Color color){
        this.cx = cx;//
        this.cy = cy;
        this.comp = comp;
        this.larg = larg;
        this.color = color;
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(cx, cy, comp, larg);
    }
}
