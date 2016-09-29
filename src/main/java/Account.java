import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Account implements java.io.Serializable {

    public String username;
    public String firstName;
    public String lastName;
    public int pin;
    public int balance;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Users");
    public static Firebase firebase = null;
    static Scanner scanner = new Scanner(System.in);

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Account(String username, String firstName, String lastName, int pin, int balance) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pin = pin;
        this.balance = balance;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Account() {
    }

    public static Account transferToLocalAccount(Account incomingAccount) {
        String transferUsername = incomingAccount.getUsername();
        String transferFirstName = incomingAccount.getFirstName();
        String transferLastName = incomingAccount.getLastName();
        int transferPin = incomingAccount.getPin();
        int transferBalance = incomingAccount.getBalance();

        return new Account(transferUsername, transferFirstName, transferLastName, transferPin, transferBalance);
    }
}
