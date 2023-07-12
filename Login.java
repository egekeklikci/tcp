import com.mysql.cj.log.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {
    JPanel panel;
    private JButton goBack, loginButton;
    JButton signup;
    private JTextField nameField;
    private JLabel errorLabel;
    private JPasswordField passField;
    private DataController dataC;
    LoginSelection losel;

    void printError(String error){
        errorLabel.setText(error);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(errorLabel);
        panel.updateUI();
    }

    public Login(LoginSelection losel){
        this.losel = losel;

        panel = new JPanel();
        panel.setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Login");

        JLabel nameLabel = new JLabel("Username", SwingConstants.CENTER);
        nameLabel.setBounds(105, 10, 180, 20);
        panel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(110,26,180,25);
        panel.add(nameField);

        nameLabel = new JLabel("Password", SwingConstants.CENTER);
        nameLabel.setBounds(105, 61, 180, 20);
        panel.add(nameLabel);

        passField = new JPasswordField();
        passField.setBounds(110,77,180,25);
        panel.add(passField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginButton.setBounds(135, 115, 130, 25);
        panel.add(loginButton);

        signup = new JButton("Sign up");
        signup.addActionListener(this);
        signup.setBounds(135, 160, 130, 25);
        if(!losel.admin)
            panel.add(signup);

        goBack = new JButton("Go Back");
        goBack.addActionListener(this);
        goBack.setBounds(150, 325, 100, 20);
        panel.add(goBack);

        errorLabel = new JLabel();
        errorLabel.setBounds(50, 220, 300, 20);
        panel.add(errorLabel);

        add(panel);
        setBounds(400, 400, 400, 400);
        setLocationRelativeTo(null); // Center the frame on the screen
        setResizable(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Login app = new Login();
            // app.setVisible(true);
        });
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==goBack){
            if(!losel.admin)
                losel.cp.remove(signup);
            losel.setTitle("Login");
            losel.cp.remove(this.panel);
            losel.cp.add(losel.panel);
            losel.cp.validate();
            losel.cp.repaint();
            return;
        }
        if(e.getSource()==loginButton){
            // admin
            if (losel.admin) {
                String nameText = nameField.getText();
                String passText = passField.getText();
                if (nameText.length() > 20 || passText.length() > 20) {
                    System.out.println("Length of the username and password can not exceed 20 characters");
                    return;
                }
                int result = SQL_IMPLEMENTATION.checkAdmin(nameText, passText, "adminAccounts");
                if (result == 1) {
                    System.out.println("login");
                    dataC = new DataController();
                    dataC.setVisible(true);

                    // IF YOU WANT TO OPEN MORE THAN 1 APP DELETE THIS
                    losel.dispose();
                } else {
                    printError("Login Failed");
                    System.out.println("fail");
                }
                return;
            }
            // customer
            else{
                String nameText = nameField.getText();
                String passText = passField.getText();
                if (nameText.length() > 20 || passText.length() > 20) {
                    System.out.println("Length of the username and password can not exceed 20 characters");
                    return;
                }
                int result = SQL_IMPLEMENTATION.checkAdmin(nameText, passText, "customerAccounts");
                if (result == 1) {
                    printError("Customer Login");
                    System.out.println("customer login");
                } else {
                    printError("Login Failed");
                    System.out.println("fail");
                }
                return;
            }
        }
        if(e.getSource()==signup){
            String nameText = nameField.getText();
            String passText = passField.getText();
            if (nameText.length() > 20 || passText.length() > 20) {
                System.out.println("Length of the username and password can not exceed 20 characters");
                return;
            }
            if (nameText.length() == 0 || passText.length() == 0){
                System.out.println("Length of the username and password can not be zero");
                return;
            }
            if (SQL_IMPLEMENTATION.addCustomer(nameText, passText)==0){
                printError("Signup Failed.");
            }
            else {
                printError("Successfully added. Please log in now");
            }
        }
    }
}
