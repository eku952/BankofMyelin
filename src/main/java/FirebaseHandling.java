import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FirebaseHandling {

    private static final double currentVersion = 1.0;
    public double version;

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public FirebaseHandling() {

    }

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    //  static final FirebaseDatabase staticDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Users");
    public static Firebase firebase = null;
    static Scanner scanner = new Scanner(System.in);

    public static void firebaseInit() {
        firebase = new Firebase("https://bank-of-myelin.firebaseio.com/");


        // Initialize Firebase
        // [START initialize]
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setServiceAccount(FirebaseHandling.class.getClassLoader().getResourceAsStream("service-account.json"))
                .setDatabaseUrl("https://bank-of-myelin.firebaseio.com/")
                .build();
        FirebaseApp.initializeApp(options);
        // [END initialize]

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

    /*public static void checkVersion() {
        final FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference ref = database2.getReference("Version/versioncontrol");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseHandling pulledVersion = dataSnapshot.getValue(FirebaseHandling.class);

                if(pulledVersion.getVersion() != currentVersion) {
                    System.out.println("WARNING! Your current version is out of date! Updating your client is recommended");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed:" + databaseError.getCode());
            }
        });
    } */

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
