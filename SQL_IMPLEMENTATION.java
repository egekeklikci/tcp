import java.sql.*;
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
    public static void printProducts() {
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

            // Process the results
            while (result.next()) {
                // Print the values
                System.out.println(
                        "Name:  " + result.getString("name"));
                System.out.println(
                        "Price: " + result.getString("price"));
                System.out.println(
                        "VAT:   " + result.getString("vat"));
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void addProduct(){
        try {
            // Loading driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Registering driver
            Connection con = DriverManager.getConnection(url, user, password);
            Scanner scanner = new Scanner(System.in);

            // Getting Values
            System.out.print("Enter the Name of the product: ");
            String name = "\""+scanner.next() +"\"";
            if (isQuit(name) == -1) return;
            System.out.print("Enter the Price of the product: ");
            int price = scanner.nextInt();
            System.out.print("Enter the VAT of the product: ");
            int vat = scanner.nextInt();
            double vatAmount = price*(vat/100.0);
            System.out.println("VAT amount is "+vatAmount);

            // Create a statement
            Statement statement = con.createStatement();
            String sql = "INSERT INTO Product(Name, Price, Vat) VALUES("+name+", "+price+", "+vat+")";

            // Execute the query
            int result = statement.executeUpdate(sql);
            if(result==0)
                System.out.println("Addition failed.");
            else
                System.out.println(name.replace("\"", "")+ " is successfully added.");
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
    public static void modifyProduct(){
        try {
            // Loading driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Registering driver
            Connection con = DriverManager.getConnection(url, user, password);
            Scanner scanner = new Scanner(System.in);

            Statement statement = con.createStatement();

            System.out.print("Which value will be updated? (1-Name, 2-Price, 3-VAT) ");
            int uType = scanner.nextInt();
            String sql = null;
            System.out.print("Enter the Name of the product: ");
            String name = "\""+scanner.next() +"\"";
            if (isQuit(name) == -1) return;
            System.out.print("Enter the Price of the product: ");
            int price = scanner.nextInt();
            System.out.print("Enter the VAT of the product: ");
            int vat = scanner.nextInt();
            switch (uType){
                case 1 -> {
                    // name
                    sql = "UPDATE Product SET name = "+name+" WHERE price = "+price+" and vat = "+vat+";";
                }
                case 2 -> {
                    // price
                    sql = "UPDATE Product SET price = "+price+" WHERE name = "+name+" and vat = "+vat +";";
                }
                case 3 -> {
                    // VAT
                    sql = "UPDATE Product SET VAT = "+vat+" WHERE name = "+name+" and  = price "+price+";";
                }
            }
            System.out.println(sql);
            int result = statement.executeUpdate(sql);
            if(result==0)
                System.out.println("Deletion failed.");
            else
                System.out.println(((uType==1) ? "Name" : (uType==2) ? "Price" : "VAT" )+ " is successfully changed to "+ ((uType==1) ? name : (uType==2) ? price : vat) +".");



        }
        catch (SQLException e) {
            System.out.println(e);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
