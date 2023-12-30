package src.GUIClassesSwing;

import javax.swing.*;
import java.awt.*;

public class retangleBarCart extends JComponent {
    private int value;
    private int maxValue;
    public retangleBarCart(int value, int maxValue){
        this.value = value;
        this.maxValue = maxValue;
        this.setPreferredSize(new Dimension(40,10));
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(maxValue > 0){
            int width = (int)((double)value / maxValue * this.getWidth());
            g.setColor(Color.CYAN);
            g.fillRect(0,0,width,this.getHeight());
        }
    }
}
