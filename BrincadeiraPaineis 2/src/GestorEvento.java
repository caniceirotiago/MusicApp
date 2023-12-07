import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestorEvento extends JPanel implements ActionListener {
    private int bot;
    private JFrame frame;

    private Container container;
    public GestorEvento (int bot){
        this.bot = bot;
        this.frame = new JFrame();
        this.frame.setSize(450,450);
        this.frame.setBackground(Color.gray);
        this.frame.setLocationRelativeTo(null);
        this.container = frame.getContentPane();
    }
    public void actionPerformed (ActionEvent e){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = GridBagConstraints.REMAINDER;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.gridheight = GridBagConstraints.REMAINDER;
        switch (bot){
            case 1:
                //painel que contem as caixas de texto e afins
                JPanel usernamePanel =  new JPanel();
                usernamePanel.setLayout(new GridBagLayout());

                //propriedades das caixas DENTRO do painel
                JTextField t = new JTextField("Set your Username",15);
                JPasswordField userPass= new JPasswordField("Password",15);
                JButton login = new JButton("Login");
                login.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Isto esta a funcionar");
                    }
                });
                JButton goBack = new JButton("Go back");
                goBack.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                    }
                });

                //adiciona ao container que é mostrado na frame
                usernamePanel.add(t,constraints);
                usernamePanel.add(userPass,constraints);
                usernamePanel.add(login, constraints);
                usernamePanel.add(goBack,constraints);

                //adicionar ao frame
                container.add(usernamePanel);
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
                break;
            case 2:
                JPanel registryPanel = new JPanel();
                registryPanel.setLayout(new GridBagLayout());
                JTextField firstN = new JTextField("FirstName", 15);
                JTextField lastN = new JTextField("LastName", 15);
                JTextField email = new JTextField("Email",15);
                JCheckBox musicCreator = new JCheckBox("Music creator",true);
                JTextField pin =  new JTextField("pin",4);
                JButton retroceder = new JButton("Retroceder");
                retroceder.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                    }
                });
                registryPanel.add(firstN, constraints);
                registryPanel.add(lastN, constraints);
                registryPanel.add(email, constraints);
                registryPanel.add(musicCreator,constraints);
                registryPanel.add(pin,constraints);
                registryPanel.add(retroceder, constraints);
                container.add(registryPanel);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
                break;
            case 3:
            //nao consigo chamar a classe para fechar os logins da main daqui
                //perguntar ao prof
        }

    }
}
