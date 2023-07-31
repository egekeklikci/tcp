import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {
    JPanel panel;
    private JButton goBack, loginButton, signup;
    CustomerScreen customerScreen;
    private JTextField nameField;
    private JLabel errorLabel;
    private JPasswordField passField;
    private DataController dataController;
    LoginSelection loginSelection;

    void printError(String error) {
        errorLabel.setText(error);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(errorLabel);
        panel.updateUI();
    }

    public Login(LoginSelection loginSelection) {
        this.loginSelection = loginSelection;

        panel = new JPanel();
        panel.setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Login");

        JLabel nameLabel = new JLabel("Username", SwingConstants.CENTER);
        nameLabel.setBounds(105, 10, 180, 20);
        panel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(110, 26, 180, 25);
        panel.add(nameField);

        nameLabel = new JLabel("Password", SwingConstants.CENTER);
        nameLabel.setBounds(105, 61, 180, 20);
        panel.add(nameLabel);

        passField = new JPasswordField();
        passField.setBounds(110, 77, 180, 25);
        panel.add(passField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginButton.setBounds(135, 115, 130, 25);
        panel.add(loginButton);

        signup = new JButton("Sign up");
        signup.addActionListener(this);
        signup.setBounds(135, 160, 130, 25);
        if (!loginSelection.adminSelected)
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == goBack) {
            if (!loginSelection.adminSelected) {
                loginSelection.loginPageContainer.remove(signup);
            }
            loginSelection.setTitle("Login");
            loginSelection.loginPageContainer.remove(this.panel);
            loginSelection.loginPageContainer.add(loginSelection.panel);
            loginSelection.loginPageContainer.validate();
            loginSelection.loginPageContainer.repaint();
            return;
        }
        if (e.getSource() == loginButton) {
            // admin
            if (loginSelection.adminSelected) {
                String nameText = nameField.getText(), passText = passField.getText();
                if (nameText.length() > 20 || passText.length() > 20) {
                    System.out.println("Length of the username and password can not exceed 20 characters");
                    return;
                }
                int result = SQL_IMPLEMENTATION.checkUser(nameText, passText, "adminAccounts");
                if (result == 1) {
                    dataController = new DataController();
                    dataController.setVisible(true);

                    // IF YOU WANT TO OPEN MORE THAN 1 APP DELETE THIS
                    loginSelection.dispose();
                } else {
                    printError("Login Failed");
                    System.out.println("fail");
                }
                return;
            }
            // customer
            else {
                String nameText = nameField.getText();
                String passText = passField.getText();
                if (nameText.length() > 20 || passText.length() > 20) {
                    printError("Length of the username and password can not exceed 20 characters");
                    return;
                }
                int result = SQL_IMPLEMENTATION.checkUser(nameText, passText, "customerAccounts");
                if (result == 1) {
                    printError("Customer Login");
                    customerScreen = new CustomerScreen(nameText);
                    customerScreen.setVisible(true);

                    // if you want to open more than 1 app delete below
                    loginSelection.dispose();
                } else {
                    printError("Login Failed");
                }
                return;
            }
        }
        if (e.getSource() == signup) {
            String nameText = nameField.getText();
            String passText = passField.getText();
            if (nameText.length() > 20 || passText.length() > 20) {
                printError("Length can not exceed 20 characters");
                return;
            }
            if (nameText.length() == 0 || passText.length() == 0) {
                printError("Length can not be zero");
                return;
            }
            if (SQL_IMPLEMENTATION.checkUsername(nameText, "customerAccounts") > 0) {
                printError("Username already exists.");
                return;
            }
            if (SQL_IMPLEMENTATION.addCustomer(nameText, passText) == 0) {
                printError("Signup Failed.");
            } else {
                printError("Successfully added. Please log in now");
            }
        }
    }
}
