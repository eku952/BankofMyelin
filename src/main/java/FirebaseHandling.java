import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FirebaseHandling {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    //  static final FirebaseDatabase staticDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Users");
    public static Firebase firebase = null;
    static Scanner scanner = new Scanner(System.in);

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
                .getReference("Users/Accounts/Test/username");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object document = dataSnapshot.getValue();
                //System.out.println(document);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    /*public static void pullTransferTarget(String transferTarget) {
        final FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference ref = database2.getReference("Users/Accounts/" + transferTarget);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Account pulledTransferAccount = dataSnapshot.getValue(Account.class);

                try {
                    Thread.sleep(2000);
                    //System.out.println("sleeping");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    Account.transferToLocalVariables(pulledTransferAccount.getUsername(), pulledTransferAccount.getFirstName(), pulledTransferAccount.getLastName(), pulledTransferAccount.getPin(), pulledTransferAccount.getBalance());
                } catch (Exception e) {
                    System.out.println("That username does not match any usernames in the database.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed:" + databaseError.getCode());
            }
        });
    } */
}
