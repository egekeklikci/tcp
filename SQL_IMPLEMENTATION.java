import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

class SQL_IMPLEMENTATION {
    private static String url = "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7631636";
    private static String user = "sql7631636";
    private static String password = "SFf2BG6XRu";
    public static ArrayList<Product> printProducts() {
        try {
            // Loading driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Registering driver
            Connection con = DriverManager.getConnection(url, user, password);

            // Create a statement
            Statement statement = con.createStatement();
            String sql = "select * from Product";

            // Execute the query
            ResultSet result = statement.executeQuery(sql);
            ArrayList<Product> products = new ArrayList<>();

            // Process the results
            while (result.next()) {
                Product product = new Product(result.getString("name"), Integer.parseInt(result.getString("price")), Integer.parseInt(result.getString("vat")), Integer.parseInt(result.getString("quantity")));
                products.add(product);
            }
            con.close();
            return products;
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void addProduct(Product product){
        try {
            // Loading driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Registering driver
            Connection con = DriverManager.getConnection(url, user, password);
            // Create a statement
            Statement statement = con.createStatement();
            String sql = "INSERT INTO Product(Name, Price, Vat, Quantity) VALUES(\""+product.name+"\", "+product.price+", "+product.vat+", "+ product.quantity+")";
            // Execute the query
            int result = statement.executeUpdate(sql);
            con.close();
            if(result==0)
                System.out.println("Addition failed.");
            else
                System.out.println(product.name.replace("\"", "")+ " is successfully added.");
        }
         catch (SQLException e) {
            System.out.println(e);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public static int removeProduct(Product product) {
        try {
            // Loading driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Registering driver
            Connection con = DriverManager.getConnection(url, user, password);
            Statement statement = con.createStatement();
            String sql = "DELETE FROM Product WHERE name = \"" + product.name + "\" and price = " + product.price + " and vat = " + product.vat;

            // Execute the query
            int result = statement.executeUpdate(sql);
            con.close();
            if (result == 0) {
                System.out.println("Deletion failed.");
                return -1;
            } else {
                System.out.println(product.name + " is successfully deleted.");
                return 0;
            }
        } catch (SQLException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public static int modifyProduct(Product product, int uType){
        try {
            // Loading driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Registering driver
            Connection con = DriverManager.getConnection(url, user, password);
            Statement statement = con.createStatement();
            // 0 - name | 1 - price | 2 - vat | 3 - quantity
            String sql = null;
            switch (uType){
                case 0 -> {
                    // name
                    sql = "UPDATE Product SET name = \""+product.name+"\" WHERE price = "+product.price+" and vat = "+product.vat+";";
                }
                case 1 -> {
                    // price
                    sql = "UPDATE Product SET price = "+product.price+" WHERE name = \""+product.name+"\" and vat = "+product.vat +";";
                }
                case 2 -> {
                    // VAT
                    sql = "UPDATE Product SET VAT = "+product.vat+" WHERE name = \""+product.name+"\" and  price = "+product.price+";";
                }
                case 3 -> {
                    // quantity
                    sql = "UPDATE Product SET quantity = "+product.quantity+" WHERE name = \""+product.name+"\" and  price = "+product.price+" and vat = "+product.vat+";";
                }
            }
            // System.out.println(sql);
            int result = statement.executeUpdate(sql);
            con.close();
            if(result==0) {
                System.out.println("Modify failed.");
                return -1;
            }
            System.out.println(((uType==0) ? "Name" : (uType==1) ? "Price" : (uType==2) ? "VAT" : "Quantity" )+ " is successfully changed to "+ ((uType==0) ? product.name : (uType==1) ? product.price : (uType==2) ? product.vat : product.quantity) +".");
            return 0;
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public static int checkUser(String username, String adminPassword, String database){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            Statement statement = con.createStatement();
            String sql = "SELECT COUNT(*) FROM "+database+" WHERE username = \""+ username+"\" AND password = \""+adminPassword+"\"";
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            return resultSet.getInt(1);
        }
         catch (SQLException e) {
        System.out.println(e);
        }
        catch (ClassNotFoundException e) {
        e.printStackTrace();
        }
        return -1;
    }
    public static int checkUsername(String username, String database){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, user, password);

            Statement statement = con.createStatement();
            String sql = "SELECT COUNT(*) FROM "+database+" WHERE username = \""+ username+"\"";

            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            return resultSet.getInt(1);
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public static int addCustomer(String username, String userPassword){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, user, password);

            Statement statement = con.createStatement();
            String sql = "INSERT INTO customerAccounts(username, password) VALUES(\""+username+"\", \""+userPassword+"\")";
            System.out.println(sql);
            // Execute the query
            int result = statement.executeUpdate(sql);
            con.close();
            // 0 fail
            return result;
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public static Product checkProduct(Product product){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM Product WHERE name = \"" + product.name + "\" and price = " + product.price + " and vat = " + product.vat+";";
            ResultSet resultSet = statement.executeQuery(sql);
            Product prReturned = null;
            if(resultSet.next()){
                prReturned = new Product(resultSet.getString("name"), Integer.parseInt(resultSet.getString("price")), Integer.parseInt(resultSet.getString("vat")), Integer.parseInt(resultSet.getString("Quantity")));
            }
            return prReturned;
        }
        catch (SQLException e) {
            System.out.println(e);}
        catch (ClassNotFoundException e) {
            e.printStackTrace();}
        return null;
    }
    public static int getWallet(String userName){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM customerAccounts WHERE username = \"" + userName +"\";";
            ResultSet resultSet = statement.executeQuery(sql);
            String foundType = "";
            if(resultSet.next()){
                foundType = resultSet.getString("wallet");
            }
            con.close();
            return Integer.parseInt(foundType);
        }
        catch (SQLException e) {
            System.out.println(e);}
        catch (ClassNotFoundException e) {
            e.printStackTrace();}
        return -1;
    }
    public static void changeWallet(String userName, int newWallet, int moneySpent){
        try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, user, password);
        Statement statement = con.createStatement();
        String sql = "UPDATE customerAccounts set wallet = "+newWallet+" where username = \""+userName+"\";";
        int result = statement.executeUpdate(sql);
        if(result==0) {
                System.out.println("Modify failed. (changeWallet)");
                return;
        }
        String updateAdmin = "UPDATE customerAccounts set wallet = "+(getWallet("admin")+moneySpent)+" where username = \"admin\";";
        statement.executeUpdate(updateAdmin);
        con.close();
        }
        catch (SQLException e) {
            System.out.println(e);}
        catch (ClassNotFoundException e) {
            e.printStackTrace();}
    }
}