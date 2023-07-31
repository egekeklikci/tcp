public class Product {
    String name = "";
    int price = 0, vat = 0, quantity = 0;

    public Product(String name, int price, int vat) {
        this.name = name;
        this.price = price;
        this.vat = vat;
    }

    public Product(String name, int price, int vat, int quantity) {
        this.name = name;
        this.price = price;
        this.vat = vat;
        this.quantity = quantity;
    }

    public void printName() {
        System.out.println(name);
    }

    public void printPrice() {
        System.out.println(price);
    }

    public void printVat() {
        System.out.println(vat);
    }
}
