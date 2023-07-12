import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DataController extends JFrame implements ActionListener {
    private JButton refreshButton, addButton, removeButton, modifyButton;
    private DefaultTableModel tableModel;
    private JTable table;
    private int itemCount;
    static Add add;
    static Remove remove;
    static Modify modify;
    JScrollPane scrollPane;
    static boolean modified = false;

    public DataController() {
        setTitle("Market");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the upper part (JTable inside JScrollPane)
        scrollPane = new JScrollPane(createTable());
        scrollPane.setPreferredSize(new Dimension(400, 250));

        // Create the lower part (Buttons arranged in a 2x4 grid)
        JPanel buttonPanel = createButtonPanel();

        // Add the upper and lower parts to the frame
        add(scrollPane, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        setBounds(0,0,400,400);
        setLocationRelativeTo(null); // Center the frame on the screen
        setResizable(false);
    }

    private JTable createTable() {
        ArrayList<Product> products = SQL_IMPLEMENTATION.printProducts();

        String col[] = {"Name","Price","VAT"};

        tableModel = new DefaultTableModel(col, 0);

        itemCount = products.size();
        for(int i = 0; i < itemCount ; i++) {
            Object[] objs = {products.get(i).name,products.get(i).price, products.get(i).vat};
            tableModel.addRow(objs);
        }
        table = new JTable(tableModel);

        table.setDefaultEditor(Object.class, null);

        return table;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(2, 4));

        // Create 8 buttons and add them to the panel
        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(this);
        buttonPanel.add(refreshButton);

        addButton = new JButton("Add");
        addButton.addActionListener(this);
        buttonPanel.add(addButton);

        removeButton = new JButton("Remove");
        removeButton.addActionListener(this);
        buttonPanel.add(removeButton);

        modifyButton = new JButton("Modify");
        modifyButton.addActionListener(this);
        buttonPanel.add(modifyButton);

        return buttonPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DataController app = new DataController();
            app.setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == refreshButton){
            ArrayList<Product> products = SQL_IMPLEMENTATION.printProducts();
            int size = products.size();
            if (modified){
                for(int i = 0; i < itemCount; i++){
                    tableModel.removeRow(0);
                }
                for(int i = 0; i < size ; i++) {
                    Object[] objs = {products.get(i).name,products.get(i).price, products.get(i).vat};
                    tableModel.addRow(objs);
                }
                itemCount = size;
                modified = false;
                return;
            }
            if (size==itemCount) {
                return;
            };
            if (size > itemCount) {
                for (int i = itemCount; i < size; i++) {
                    Object[] objs = {products.get(i).name, products.get(i).price, products.get(i).vat};
                    tableModel.addRow(objs);
                }
                tableModel.fireTableDataChanged();
                itemCount = size;
                return;
            }
            int i = 0;
            while (i < size){
                if (!tableModel.getValueAt(i, 0).equals(products.get(i).name) || (int) tableModel.getValueAt(i, 1)!=products.get(i).price || ((int)tableModel.getValueAt(i, 2))!=products.get(i).vat) {
                    tableModel.removeRow(i);
                    itemCount--;
                }
                else
                    i++;
            }
            i = size;
            while (i < itemCount){
                tableModel.removeRow(i);
                itemCount--;
            }
            itemCount = size;
            tableModel.fireTableDataChanged();
        }
        if (add != null) {
            if (add.isDisplayable()) return;
        }
        if (remove!=null){
            if (remove.isDisplayable()) return;
        }
        if (modify != null){
            if (modify.isDisplayable()) return;
        }
        if (e.getSource() == addButton){
            add = new Add();
            add.setVisible(true);
            System.out.println("add");
            return;
        }
        if (e.getSource() == removeButton){
            remove = new Remove();
            remove.setVisible(true);
            System.out.println("Remove");
            return;
        }
        if (e.getSource() == modifyButton){
            modify = new Modify();
            modify.setVisible(true);
            System.out.println("Modify Select Screen");
            return;
        }
    }
    static void closeAdd(){
        add.dispose();
    }
    static void closeRemove(){
        remove.dispose();
    }
    static void close(JFrame frame){
        frame.dispose();
    }

}
