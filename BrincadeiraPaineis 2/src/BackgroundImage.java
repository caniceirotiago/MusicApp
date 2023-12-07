import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.net.URL;

public class BackgroundImage extends JComponent {
    private Image image;

    public BackgroundImage (Image image) throws IOException {
    this.image = image;
    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
            g.drawImage(image,0,0,getWidth(),getHeight(),this);
    }
}
