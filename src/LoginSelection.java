import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginSelection extends JFrame implements ActionListener
{
    boolean adminSelected = false;
    JPanel panel;
    private JButton customerButton, adminButton;
    Container loginPageContainer;
    public LoginSelection(){
        panel = new JPanel();
        panel.setLayout(null);
        loginPageContainer = getContentPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Login");

        customerButton = new JButton("Customer Login");
        customerButton.addActionListener(this);
        customerButton.setBounds(100,100,200,50);
        panel.add(customerButton);

        adminButton = new JButton("Admin Login");
        adminButton.addActionListener(this);
        adminButton.setBounds(100, 200, 200, 50);
        panel.add(adminButton);

        add(panel);
        setBounds(400, 400, 400, 400);
        setLocationRelativeTo(null); // Center the frame on the screen
        setResizable(false);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    LoginSelection frame = new LoginSelection();
                    frame.setVisible(true);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==adminButton){
            adminSelected = true;
            loginPageContainer.remove(panel);
            Login login = new Login(this);
            loginPageContainer.add(login.panel);
            loginPageContainer.validate();
            loginPageContainer.repaint();
            setTitle("Admin Login");
            return;
        }
        if(e.getSource()==customerButton){
            adminSelected = false;
            loginPageContainer.remove(panel);
            Login login = new Login(this);
            loginPageContainer.add(login.panel);
            loginPageContainer.validate();
            loginPageContainer.repaint();
            setTitle("Customer Login");
        }
    }
}
