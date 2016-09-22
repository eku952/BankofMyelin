import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Account {

    private String username;
    private int PIN;
    private int balance;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static Firebase firebase = null;
    static Scanner scanner = new Scanner(System.in);

    public Account(String username, int PIN) {
        this.username = username;
        this.PIN = PIN;
        this.balance = 5;
    }

    public static void firebaseInit() {
        firebase = new Firebase("https://bank-of-myelin.firebaseio.com/");


        // Initialize Firebase
        try {
            // [START initialize]
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setServiceAccount(new FileInputStream("service-account.json"))
                    .setDatabaseUrl("https://bank-of-myelin.firebaseio.com/")
                    .build();
            FirebaseApp.initializeApp(options);
            // [END initialize]
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: invalid service account credentials. See README.");
            System.out.println(e.getMessage());

            System.exit(1);
        }

        DatabaseReference ref = FirebaseDatabase
                .getInstance()
                .getReference("Customers");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object document = dataSnapshot.getValue();
                System.out.println(document);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static Account createNewUser() {
        System.out.println("Please create a username.");
        String newUsername = scanner.nextLine();

        System.out.println("Please create a PIN (numbers only).");
        int newPIN = scanner.nextInt();

        return new Account(newUsername, newPIN);
    }

}
