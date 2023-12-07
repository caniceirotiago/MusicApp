import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Botoes extends JPanel {
    public Botoes (){
        JButton login = new JButton("Login");
        login.addActionListener(new GestorEvento(1));
        this.add(login);
        JButton registo =  new JButton("Registar");
        registo.addActionListener(new GestorEvento(2));
        this.add(registo);
        JButton sair = new JButton("sair");
        sair.addActionListener(new GestorEvento(3));
        this.add(sair);

    }
}
