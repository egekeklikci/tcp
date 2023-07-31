import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Cart extends JFrame implements ActionListener {
    JButton checkOut;
    DefaultTableModel tableModel;
    JTable table;
    int item, wallet;
    JScrollPane scrollPane;
    ArrayList<Product> products;
    CustomerScreen cs;
    JLabel moneyLabel;

    public Cart(CustomerScreen cs) {
        this.cs = cs;
        setTitle("Cart");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the upper part (JTable inside JScrollPane)
        scrollPane = new JScrollPane(createTable());
        scrollPane.setPreferredSize(new Dimension(400, 250));

        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.CENTER);

        // Add the upper and lower parts to the frame
        add(scrollPane, BorderLayout.NORTH);
        //add(buttonPanel, BorderLayout.CENTER);
        setBounds(0, 0, 400, 400);
        setLocationRelativeTo(null); // Center the frame on the screen
        setResizable(false);

        item = cs.selectedProducts.size();
    }

    private JTable createTable() {
        products = cs.selectedProducts;

        String col[] = {"Name", "Price", "VAT", "Quantity"};

        tableModel = new DefaultTableModel(col, 0);

        for (int i = 0; i < products.size(); i++) {
            Object[] objs = {products.get(i).name, products.get(i).price, products.get(i).vat, products.get(i).quantity};
            this.products.add(products.get(i));
            tableModel.addRow(objs);
        }
        table = new JTable(tableModel);

        table.setDefaultEditor(Object.class, null);

        return table;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

        // Create 2 buttons and add them to the panel
        wallet = SQL_IMPLEMENTATION.getWallet(cs.username);
        moneyLabel = new JLabel("Money: " + wallet, SwingConstants.CENTER);
        buttonPanel.add(moneyLabel);

        checkOut = new JButton("check out");
        checkOut.addActionListener(this);
        buttonPanel.add(checkOut);

        return buttonPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == checkOut) {
            int price = 0;
            boolean enoughStock = true;
            for (Product pr : products) {
                Product productInDB = SQL_IMPLEMENTATION.checkProduct(pr);
                assert productInDB != null;
                if (pr.quantity > productInDB.quantity) {
                    enoughStock = false;
                }
                price += pr.quantity * productInDB.price;
            }

            if (enoughStock && wallet >= price) {
                // can buy
                for (Product pr : products) {
                    Product productInDB = SQL_IMPLEMENTATION.checkProduct(pr);
                    assert productInDB != null;
                    pr.quantity = productInDB.quantity - pr.quantity;
                    SQL_IMPLEMENTATION.modifyProduct(pr, 3);
                }
                for (int i = 0; i < products.size(); i++) {
                    tableModel.removeRow(0);
                }
                item = 0;
                this.setVisible(false);
                this.setVisible(true);
                products.clear();
                SQL_IMPLEMENTATION.changeWallet(cs.username, (wallet - price), price);
                moneyLabel.setText("Money:" + SQL_IMPLEMENTATION.getWallet(cs.username) + " Purchase completed.");
                wallet -= price;
            } else
                moneyLabel.setText("Money:" + SQL_IMPLEMENTATION.getWallet(cs.username) + " Not enough stock or balance");
        }
    }
}
