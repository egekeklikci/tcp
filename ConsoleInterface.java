import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleInterface {
    public static void main(String[] args) {
        /*
            1: Insert
            2: Print
            3: Quit
         */
        Scanner scanner=new Scanner(System.in);
        while(true) {
            System.out.print("Enter a number (1-Add 2-Print 3-Delete 4-Modify 5-Quit): ");
            try {
                int process = scanner.nextInt();
                switch(process){
                    case 1:
                        System.out.println("Add");
                        // SQL_IMPLEMENTATION.addProduct();
                        break;
                    case 2:
                        System.out.println("Can not print right now");
                        // SQL_IMPLEMENTATION.printProducts();
                        break;
                    case 3:
                        System.out.println("Delete");
                        // SQL_IMPLEMENTATION.deleteProduct();
                    case 4:
                        System.out.println("Modify");
                        // SQL_IMPLEMENTATION.modifyProduct();
                }
                if(process==5){
                    System.out.println("Quitting");
                    break;
                }

            } catch (InputMismatchException e) {
                System.out.println("Wrong input.");
            }
        }
    }
}
