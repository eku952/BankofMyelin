import com.google.firebase.database.*;
import com.google.firebase.internal.Objects;

import java.util.Scanner;



public class Main {

    //private static int pin = 9999;
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

                System.out.println("Please insert your pin.");
                int tempPin = scanner.nextInt();

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Account pulledAccount = dataSnapshot.getValue(Account.class);
                        //System.out.println(pulledAccount);
                        System.out.println(pulledAccount.getUsername());
                        System.out.println(pulledAccount.getPin());

                        if(tempUsername.equals(pulledAccount.getUsername()) && tempPin == pulledAccount.getPin()) {
                            System.out.println("Login completed");

                            startup = false;
                            login = true;
                        }
                        else {
                            System.out.println("Incorrect password");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("Failed to read: " + databaseError.getCode());
                    }
                });
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

                System.out.println("Please create a pin (numbers only).");
                int newPin = scanner.nextInt();

                DatabaseReference accountRef = reference.child("Accounts");

                accountRef.child(newUsername).setValue(new Account(newUsername, newFirstName, newLastName, newPin));
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
                case "deposit":
                    System.out.println("How much would you like to deposit?");
                    int depositAmount = scanner.nextInt();

                    System.out.println("Great! You wave deposited " + depositAmount);
                    break;
                case "exit":
                    System.out.println("Shutting down.");
                    login = false;
                    break;
            }
        }
    }
}
