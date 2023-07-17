import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Set;

public class CustomerScreen extends JFrame{
    private DefaultTableModel tableModel;
    private JTable table;
    private int itemCount;
    JScrollPane scrollPane;
    ArrayList<Product> products, selectedProducts;
    Cart cart;
    String username = "";
    boolean samePr(Product pr1, Product pr2){
        return pr1.name.equals(pr2.name) && pr1.price==pr2.price && pr1.vat==pr2.vat;
    }

    public CustomerScreen(String username) {
        this.username = username;
        setTitle("Market");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        selectedProducts = new ArrayList<>();

        // Create the upper part (JTable inside JScrollPane)
        scrollPane = new JScrollPane(createTable());
        scrollPane.setPreferredSize(new Dimension(400, 250));



        // Create the lower part (Buttons arranged in a 2x4 grid)
        //JPanel buttonPanel = createButtonPanel();

        // Add the upper and lower parts to the frame
        add(scrollPane);
        //add(buttonPanel, BorderLayout.CENTER);
        setBounds(0,0,400,400);
        setLocationRelativeTo(null); // Center the frame on the screen
        setResizable(false);
        cart = new Cart(this);

        table.addMouseListener(new MouseListener() {
            @Override public void mouseClicked(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {
                int row = table.getSelectedRow();
                int column = table.getSelectedColumn();
                if ((int)table.getValueAt(row, 3)-1 < 0)
                    return;
                boolean inCart = false;
                Product sPr = new Product(table.getValueAt(row, 0).toString(), (int)table.getValueAt(row, 1), (int)table.getValueAt(row, 2), 1);
                for (Product pr : selectedProducts) {
                    if (samePr(pr, sPr)) {
                        inCart = true;
                        pr.quantity++;
                        break;
                    }
                }
                if (!inCart){
                    selectedProducts.add(sPr);
                }
                table.setValueAt((int)table.getValueAt(row, 3)-1,row,3);
                //System.out.println(products.get(row).name+" "+products.get(row).price+" "+products.get(row).vat);
                inCart = false;
                for(int i = 0; i < cart.item; i++){
                    cart.tableModel.removeRow(0);
                }
                for (Product selectedProduct : selectedProducts) {
                    Object[] objs = {selectedProduct.name, selectedProduct.price, selectedProduct.vat, selectedProduct.quantity};
                    cart.tableModel.addRow(objs);
                }
                cart.item = selectedProducts.size();
                cart.table.updateUI();
            }@Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
        });

        cart.setVisible(true);
    }

    private JTable createTable() {
        products = SQL_IMPLEMENTATION.printProducts();

        String col[] = {"Name","Price","VAT", "Quantity"};

        tableModel = new DefaultTableModel(col, 0);

        itemCount = products.size();
        for(int i = 0; i < itemCount ; i++) {
            Object[] objs = {products.get(i).name,products.get(i).price, products.get(i).vat, products.get(i).quantity};
            this.products.add(products.get(i));
            tableModel.addRow(objs);
        }
        table = new JTable(tableModel);

        table.setDefaultEditor(Object.class, null);

        return table;
    }
}
