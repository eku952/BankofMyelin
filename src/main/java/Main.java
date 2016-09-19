import java.util.Scanner;
import com.firebase.client.Firebase;

public class Main {

    static int pin = 9999;
    static boolean login = false;
    static boolean startup = true;
    static Firebase firebase = null;

    public static void main(String[] args) {
        firebase = new Firebase("https://bank-of-myelin.firebaseio.com/");
        Scanner scanner = new Scanner(System.in);

        System.out.println(firebase.toString());

        while(startup) {
            System.out.println("Welcome to the Bank of Myelin!");
            login = true;
            startup = false;
        }

        while(login) {
            System.out.println("Will you be adding or removing users today?");
            String responseS = scanner.next();

            if (responseS.toLowerCase().equals("adding")) {
                System.out.println("What will be the user's name?");
                String tempUsername = scanner.next();

                System.out.println("What will be the user's current balance?");
                int tempBalance = scanner.nextInt();

                Account.createAccount(tempUsername, tempBalance);
            }

            login = false;
        }
    }
}
