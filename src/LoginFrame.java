package src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

//Esta classe foi criada automaticamente na internet e não é para ser utilizada
//Apenas serve para experimentar os comportamentos entre o backend e frontend

public class LoginFrame extends JFrame implements Serializable {
    private JButton loginButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private RockstarIncManager manager;

    public LoginFrame(RockstarIncManager manager) {
        super("Login"); // Define o título da janela

        this.manager = manager;

        // Inicializa os componentes gráficos
        initComponents();

        // Configurações padrão da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setResizable(false);
    }

    private void initComponents() {
        // Layout da janela
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Componentes gráficos
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");

        // Adiciona um ouvinte de ação ao botão de login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onLoginButtonClick();
            }
        });

        // Adiciona os componentes à janela
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(loginButton);
    }

    private void onLoginButtonClick() {
        // Obtém o texto dos campos de usuário e senha
        String username = usernameField.getText();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);

        // Chama o método de login na instância do RockstarIncManager
        manager.login(username, password,loginButton);
        // Limpa os campos após o login (opcional)
        usernameField.setText("");
        passwordField.setText("");
    }
}