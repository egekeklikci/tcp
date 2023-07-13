import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.NoRouteToHostException;
import java.util.ArrayList;

public class Cart extends JFrame implements ActionListener {
    JButton checkOut; DefaultTableModel tableModel; JTable table; int item; JScrollPane scrollPane; ArrayList<Product> products; CustomerScreen cs; JLabel moneyLabel;
    boolean purchComp = false;
    boolean samePr(Product pr1, Product pr2){
        return pr1.name.equals(pr2.name) && pr1.price==pr2.price && pr1.vat==pr2.vat;
    }

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
        setBounds(0,0,400,400);
        setLocationRelativeTo(null); // Center the frame on the screen
        setResizable(false);

        item = cs.selectedProducts.size();
    }
    private JTable createTable() {
        products = cs.selectedProducts;

        String col[] = {"Name","Price","VAT", "Quantity"};

        tableModel = new DefaultTableModel(col, 0);

        for(int i = 0; i < products.size() ; i++) {
            Object[] objs = {products.get(i).name,products.get(i).price, products.get(i).vat, products.get(i).quantity};
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
        moneyLabel = new JLabel("Money", SwingConstants.CENTER);
        buttonPanel.add(moneyLabel);

        checkOut = new JButton("check out");
        checkOut.addActionListener(this);
        buttonPanel.add(checkOut);

        return buttonPanel;
    }

    static void close(JFrame frame){
        frame.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==checkOut){
            boolean enough = true;
            for (Product pr : products){
                System.out.println(pr.name+" "+pr.quantity);
                int prStock = SQL_IMPLEMENTATION.checkProduct(pr);
                if (pr.quantity > prStock){
                    enough = false;
                }
            }
            if (enough){
                // can buy
                for (Product pr : products){
                    int prStock = SQL_IMPLEMENTATION.checkProduct(pr);
                    pr.quantity = prStock-pr.quantity;
                    SQL_IMPLEMENTATION.modifyProduct(pr,3);
                }
                for(int i = 0; i < products.size(); i++){
                    System.out.println("AAA");
                    tableModel.removeRow(0);
                }
                item = 0;
                this.setVisible(false);
                this.setVisible(true);
                products.clear();
                if(!purchComp)
                    moneyLabel.setText(moneyLabel.getText()+"\nPurchase Completed");
                purchComp = true;
            }
            else
                System.out.println("NOT ENOUGH STOCK");
        }
    }
}
