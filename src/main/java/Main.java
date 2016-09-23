import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



public class Main {

    private static int pin = 9999;
    private static boolean login = false;
    private static boolean startup = true;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Account.firebaseInit();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users");
        //System.out.println(firebase.toString());

        while(startup) {
            System.out.println("Welcome to the Bank of Myelin! Do you have an account?");
            String response = scanner.nextLine();
            if(response.toLowerCase().equals("yes")) {
                System.out.println("Please insert your username.");
                String tempUsername = scanner.nextLine();

                System.out.println("Please insert your PIN.");
                int tempPIN = scanner.nextInt();

                login = true;
                startup = false;
            }
            else if(response.toLowerCase().equals("no")) {
                login = true;
                startup = false;

                System.out.println("Please create a username.");
                String newUsername = scanner.nextLine();

                System.out.println("Please enter your first name.");
                String newFirstName = scanner.nextLine();

                System.out.println("Please enter your last name.");
                String newLastName = scanner.nextLine();

                System.out.println("Please create a PIN (numbers only).");
                int newPIN = scanner.nextInt();

                DatabaseReference accountRef = reference.child("Accounts");

                accountRef.child(newUsername).setValue(new Account(newFirstName, newLastName, newPIN));
            }
        }

        while(login) {
            System.out.println("Would you like to withdraw, transfer, deposit myelin bucks or exit?");
            String responseS = scanner.next();

            switch(responseS.toLowerCase()) {
                case "withdraw":
                    System.out.println("How much money would you like to withdraw?");
                    int withdrawAmount = scanner.nextInt();
                    
                    System.out.println("Great! You have withdrawn " + withdrawAmount);
                    break;
                case "transfer":
                    System.out.println("Who would you like to transfer your myelin bucks to?");
                    String transferTarget = scanner.nextLine();

                    System.out.println("How many myelin bucks do you want to transfer to " + transferTarget + "?");
                    int withdrawAmount2 = scanner.nextInt();

                    System.out.println("You have successfully transferred " + withdrawAmount2 + "to " + transferTarget);
                    break;
            }

            login = false;
        }
    }
}
