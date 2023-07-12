import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

class SQL_IMPLEMENTATION {
    public static int isQuit(String inp){
        if (inp.equals("\"quit\"")){
            System.out.println("Quitting");
            return -1;
        }
        return 0;
    }
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
                Product product = new Product(result.getString("name"), Integer.parseInt(result.getString("price")), Integer.parseInt(result.getString("vat")));
                // Print the values
                /*product.printName();
                product.printPrice();
                product.printVat();*/
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
            Scanner scanner = new Scanner(System.in);

            // Getting Values
            /*System.out.print("Enter the Name of the product: ");
            String name = "\""+scanner.next() +"\"";
            if (isQuit(name) == -1) return;
            System.out.print("Enter the Price of the product: ");
            int price = scanner.nextInt();
            System.out.print("Enter the VAT of the product: ");
            int vat = scanner.nextInt();
            double vatAmount = price*(vat/100.0);
            System.out.println("VAT amount is "+vatAmount);*/

            // Create a statement
            Statement statement = con.createStatement();
            String sql = "INSERT INTO Product(Name, Price, Vat) VALUES(\""+product.name+"\", "+product.price+", "+product.vat+")";
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
    public static void deleteProduct(){
        try {
            // Loading driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Registering driver
            Connection con = DriverManager.getConnection(url, user, password);
            Scanner scanner = new Scanner(System.in);

            System.out.print("Choose deletion type (1-Name, 2-Name and Price, 3-Name, Price and Vat, 4-Price and Vat): ");
            int dType = scanner.nextInt();

            // Create a statement
            Statement statement = con.createStatement();

            System.out.print("Enter the Name of the product: ");
            // get product
            String name = "\""+scanner.next()+"\"";
            if (isQuit(name) == -1) return;
            int price;
            String sql = "DELETE FROM Product WHERE name = "+name;
            switch (dType) {
                case 2 -> {
                    System.out.print("Enter the Price of the product: ");
                    price = scanner.nextInt();
                    sql = sql.concat(" and price = " + price);
                }
                case 3 -> {
                    System.out.print("Enter the Price of the product: ");
                    price = scanner.nextInt();
                    System.out.print("Enter the VAT of the product: ");
                    int vat = scanner.nextInt();
                    sql = sql.concat(" and price = " + price + " and vat = " + vat);
                }
                case 4 -> {
                    System.out.print("Enter the Price of the product: ");
                    price = scanner.nextInt();
                    System.out.print("Enter the VAT of the product: ");
                    int vat = scanner.nextInt();
                    sql = "DELETE FROM Product WHERE price = " + price + " and vat = " + vat;
                }
            }

            // Execute the query
            int result = statement.executeUpdate(sql);
            con.close();
            if(result==0)
                System.out.println("Deletion failed.");
            else
                System.out.println(name.replace("\"", "")+ " is successfully deleted.");
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

            int price;
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
            Scanner scanner = new Scanner(System.in);

            Statement statement = con.createStatement();
            // 0 - name 1 - price 2 - vat
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
                    sql = "UPDATE Product SET VAT = "+product.vat+" WHERE name = \""+product.name+"\" and  = price "+product.price+";";
                }
            }
            // System.out.println(sql);
            int result = statement.executeUpdate(sql);
            con.close();
            if(result==0) {
                System.out.println("Deletion failed.");
                return -1;
            }
            System.out.println(((uType==1) ? "Name" : (uType==2) ? "Price" : "VAT" )+ " is successfully changed to "+ ((uType==1) ? product.name : (uType==2) ? product.price : product.vat) +".");
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
    public static int checkAdmin(String username, String adminPassword, String database){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, user, password);

            Statement statement = con.createStatement();
//            String sql = "SELECT COUNT(*) FROM adminAccounts WHERE username = \""+ username+"\" AND password = \""+adminPassword+"\"";
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
}
