import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Modify extends JFrame implements ActionListener {
    public JPanel panel;
    private JTextField nameText, priceTextf, vatTextf, quantityTextf;
    private JButton goBack, nameButton, priceButton, vatButton, goToChange, changeButton, quantityButton;
    private JLabel nameLabel, priceLabel, vatLabel, quantityLabel;
    private JLabel errorLabel;
    private int changeType;

    public Modify() {
        panel = new JPanel();
        panel.setLayout(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Modify Product");

        nameButton = new JButton("Change name");
        nameButton.addActionListener(this);
        nameButton.setBounds(125, 40, 150, 30);
        priceButton = new JButton("Change price");
        priceButton.addActionListener(this);
        priceButton.setBounds(125, 90, 150, 30);
        vatButton = new JButton("Change VAT");
        vatButton.addActionListener(this);
        vatButton.setBounds(125, 140, 150, 30);
        quantityButton = new JButton("Change quantity");
        quantityButton.addActionListener(this);
        quantityButton.setBounds(125, 190, 150, 30);
        nameLabel = new JLabel("Name of the product:", SwingConstants.CENTER);
        nameLabel.setBounds(105, 10, 180, 20);
        nameText = new JTextField();
        nameText.setBounds(110, 26, 180, 25);
        priceLabel = new JLabel("Price of the product", SwingConstants.CENTER);
        priceLabel.setBounds(105, 61, 180, 20);
        priceTextf = new JTextField();
        priceTextf.setBounds(110, 77, 180, 25);
        vatLabel = new JLabel("VAT of the product", SwingConstants.CENTER);
        vatLabel.setBounds(105, 112, 180, 20);
        vatTextf = new JTextField();
        vatTextf.setBounds(110, 128, 180, 25);

        quantityLabel = new JLabel("Quantity of the product", SwingConstants.CENTER);
        quantityLabel.setBounds(105, 163, 180, 20);

        quantityTextf = new JTextField();
        quantityTextf.setBounds(110, 179, 180, 25);

        errorLabel = new JLabel();
        errorLabel.setBounds(50, 250, 300, 20);

        changeButton = new JButton("Change");
        changeButton.setBounds(120, 220, 160, 25);
        changeButton.addActionListener(this);

        addButtons();

        goBack = new JButton("Go Back");
        goBack.addActionListener(this);
        goBack.setBounds(150, 335, 100, 20);
        panel.add(goBack);

        goToChange = new JButton("Change Modify Type");
        goToChange.addActionListener(this);
        goToChange.setBounds(110, 275, 180, 25);


        this.add(panel);
        setBounds(400, 400, 400, 400);
        setLocationRelativeTo(null); // Center the frame on the screen
        setResizable(false);

    }

    private void addButtons() {
        panel.add(nameButton);
        panel.add(priceButton);
        panel.add(vatButton);
        panel.add(quantityButton);
    }

    private void addTextfields() {
        panel.add(nameLabel);
        panel.add(nameText);
        panel.add(priceLabel);
        panel.add(priceTextf);
        panel.add(vatLabel);
        panel.add(vatTextf);
        panel.add(changeButton);
        panel.updateUI();
    }

    private void removeTextFields() {
        panel.remove(nameText);
        panel.remove(priceTextf);
        panel.remove(vatTextf);
        panel.remove(nameLabel);
        panel.remove(priceLabel);
        panel.remove(vatLabel);
        panel.remove(changeButton);
        panel.remove(errorLabel);
        panel.updateUI();
    }

    private void removeButtons() {
        panel.remove(nameButton);
        panel.remove(priceButton);
        panel.remove(vatButton);
        panel.remove(quantityButton);
        panel.updateUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == goBack) {
            DataController.close(this);
        } else if (e.getSource() == goToChange) {
            if (changeType == 3) {
                panel.remove(quantityLabel);
                panel.remove(quantityTextf);
                panel.updateUI();
            }
            removeTextFields();
            addButtons();
            panel.remove(goToChange);
        } else if (e.getSource() == nameButton) {
            removeButtons();
            addTextfields();
            panel.add(goToChange);
            changeType = 0;
        } else if (e.getSource() == priceButton) {
            removeButtons();
            addTextfields();
            panel.add(goToChange);
            changeType = 1;
        } else if (e.getSource() == vatButton) {
            removeButtons();
            addTextfields();
            panel.add(goToChange);
            changeType = 2;
        } else if (e.getSource() == quantityButton) {
            removeButtons();
            addTextfields();
            panel.add(quantityTextf);
            panel.add(quantityLabel);
            panel.add(goToChange);
            changeType = 3;
        } else if (e.getSource() == changeButton) {
            System.out.println("Change Button Press");
            printError("Change Button Presses");
            int vat, price, quantity;
            String name = nameText.getText();
            if (name.equals("")) {
                printError("Length of the name can not be zero");
                return;
            } else if (name.length() > 21) {
                printError("Length of the name can not exceed 20");
                return;
            }
            try {
                price = Integer.parseInt(priceTextf.getText());
                if (price < 0) {
                    printError("Price must be bigger than 0");
                    return;
                }
            } catch (NumberFormatException ex) {
                printError("Price must be a number");
                return;
            }
            try {
                vat = Integer.parseInt(vatTextf.getText());
                if (vat > 100 || vat < 0) {
                    printError("VAT must be between 0 and 100");
                    return;
                }
            } catch (NumberFormatException ex) {
                printError("Vat must be a number");
                return;
            }
            if (changeType == 3) {
                try {
                    quantity = Integer.parseInt(quantityTextf.getText());
                    if (quantity <= 0) {
                        printError("Quantity must be bigger than 0");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    printError("Quantity must be a number");
                    return;
                }
                if (SQL_IMPLEMENTATION.modifyProduct(new Product(name, price, vat, quantity), changeType) == 0) {
                    printError("Success");
                    DataController.modified = true;
                }
                printError("Failed. Check Values");
                return;
            }
            if (SQL_IMPLEMENTATION.modifyProduct(new Product(name, price, vat), changeType) == 0) {
                printError("Success");
                DataController.modified = true;
            } else
                printError("Failed. Check Values");
        }
    }

    void printError(String error) {
        errorLabel.setText(error);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(errorLabel);
        panel.updateUI();
    }
}
