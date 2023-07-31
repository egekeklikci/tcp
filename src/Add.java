import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Add extends JFrame implements ActionListener {
    public JPanel panel;
    private JButton goBack, addButton;
    private JTextField nameText, priceTextf, vatTextf, quantityTextf;
    private JLabel errorLabel;

    void printError(String error) {
        errorLabel.setText(error);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(errorLabel);
        panel.updateUI();
    }

    public Add() {
        panel = new JPanel();
        panel.setLayout(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Add Product");

        JLabel nameLabel = new JLabel("Name of the product:", SwingConstants.CENTER);
        nameLabel.setBounds(105, 10, 180, 20);
        panel.add(nameLabel);

        nameText = new JTextField();
        nameText.setBounds(110, 26, 180, 25);
        panel.add(nameText);

        JLabel priceLabel = new JLabel("Price of the product", SwingConstants.CENTER);
        priceLabel.setBounds(105, 61, 180, 20);
        panel.add(priceLabel);

        priceTextf = new JTextField();
        priceTextf.setBounds(110, 77, 180, 25);
        panel.add(priceTextf);

        JLabel vatLabel = new JLabel("VAT of the product", SwingConstants.CENTER);
        vatLabel.setBounds(105, 112, 180, 20);
        panel.add(vatLabel);

        vatTextf = new JTextField();
        vatTextf.setBounds(110, 128, 180, 25);
        panel.add(vatTextf);

        JLabel quantityLabel = new JLabel("Quantity of the product", SwingConstants.CENTER);
        quantityLabel.setBounds(105, 163, 180, 20);
        panel.add(quantityLabel);

        quantityTextf = new JTextField();
        quantityTextf.setBounds(110, 179, 180, 25);
        panel.add(quantityTextf);

        addButton = new JButton("Add Item");
        addButton.addActionListener(this);
        addButton.setBounds(150, 220, 100, 20);
        panel.add(addButton);

        goBack = new JButton("Go Back");
        goBack.addActionListener(this);
        goBack.setBounds(150, 325, 100, 20);
        panel.add(goBack);

        errorLabel = new JLabel();
        errorLabel.setBounds(50, 260, 300, 20);

        add(panel);
        setBounds(400, 400, 400, 400);
        setLocationRelativeTo(null); // Center the frame on the screen
        setResizable(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == goBack) {
            DataController.close(this);
        } else if (e.getSource() == addButton) {
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
            Product product = new Product(name, price, vat, quantity);
            SQL_IMPLEMENTATION.addProduct(product);
            printError("Successful");
            DataController.modified = true;
        }
    }
}
