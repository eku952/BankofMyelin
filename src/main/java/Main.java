import com.google.firebase.database.*;

import java.util.Scanner;



public class Main {

    //private static int pin = 9999;
    private static boolean login = false;
    private static boolean startup = true;
    private static Account mainAccount;
    private static Account transferredAccount;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FirebaseHandling.firebaseInit();
        //FirebaseHandling.checkVersion();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users");
        //System.out.println(firebase.toString());
        System.out.println("Welcome to the Bank of Myelin! Do you have an account?");
        String response = scanner.nextLine();

        if(response.toLowerCase().equals("yes")) {
            while(startup) {
                System.out.println("Please insert your username.");
                String tempUsername = scanner.next();

                System.out.println("Please insert your pin.");
                int tempPin = scanner.nextInt();

                System.out.println("Loading...");
                DatabaseReference pullRef = reference.child("Accounts/" + tempUsername);
                pullRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Account pulledAccount = dataSnapshot.getValue(Account.class);
                        //pulledAccount = new Account();

                        //System.out.println(pulledAccount.getUsername() + " Puled down");
                        //System.out.println(pulledAccount.getPin());

                        try {
                            Thread.sleep(2000);
                            //System.out.println("sleeping");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        try {
                            Account.transferToLocalVariables(pulledAccount.getUsername(), pulledAccount.getFirstName(), pulledAccount.getLastName(), pulledAccount.getPin(), pulledAccount.getBalance());
                        } catch (Exception e) {
                            System.out.println("That username does not match any usernames in the database.");
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });

                try {
                    Thread.sleep(2000);
                    //System.out.println("sleeping");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                mainAccount = Account.transferToLocalAccount();

                if (tempUsername.equals(mainAccount.getUsername()) && tempPin == mainAccount.getPin()) {
                    startup = false;
                    login = true;

                    //System.out.println(mainAccount.getPin());
                    System.out.println("Login successful");
                    break;
                } else {
                    System.out.println("Login failed");
                }
            }
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

            System.out.println("You by default have 5 Myelin bucks in your account");
            int newBalance = 5;

            DatabaseReference accountRef = reference.child("Accounts");
            accountRef.child(newUsername).setValue(new Account(newUsername, newFirstName, newLastName, newPin, newBalance));
            mainAccount = new Account(newUsername, newFirstName, newLastName, newPin, newBalance);
        }

        while(login) {
            System.out.println("Would you like to transfer, deposit, check your balance (use command checkbalance), or exit?");
            String responseS = scanner.next();

            switch(responseS.toLowerCase()) {
                /*case "withdraw":
                    System.out.println("How much money would you like to withdraw?");
                    int withdrawAmount = scanner.nextInt();
                    
                    System.out.println("Great! You have withdrawn " + withdrawAmount);
                    break; */
                case "transfer":
                    System.out.println("Who would you like to transfer your myelin bucks to?");
                    String transferTarget = scanner.next();
                    //FirebaseHandling.pullTransferTarget(transferTarget);

                    System.out.println("Loading...");
                    DatabaseReference pullRef2 = reference.child("Accounts/" + transferTarget);
                    DatabaseReference pullRef3 = reference.child("Accounts/" + mainAccount.getUsername());
                    pullRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Account pulledAccount2 = dataSnapshot.getValue(Account.class);
                            //pulledAccount = new Account();

                            //System.out.println(pulledAccount.getUsername() + " Puled down");
                            //System.out.println(pulledAccount.getPin());

                            try {
                                Thread.sleep(2000);
                                //System.out.println("sleeping");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            try {
                                Account.transferToLocalVariables(pulledAccount2.getUsername(), pulledAccount2.getFirstName(), pulledAccount2.getLastName(), pulledAccount2.getPin(), pulledAccount2.getBalance());
                            } catch (Exception e) {
                                System.out.println("That username does not match any usernames in the database.");
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                    });

                    try {
                        Thread.sleep(2000);
                        //System.out.println("sleeping");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    transferredAccount = Account.transferToLocalAccount();

                    System.out.println("How many myelin bucks do you want to transfer to " + transferTarget + "?");
                    int withdrawAmount2 = scanner.nextInt();

                    if(mainAccount.getBalance() >= withdrawAmount2) {
                        transferredAccount.setBalance(transferredAccount.getBalance() + withdrawAmount2);
                        mainAccount.setBalance(mainAccount.getBalance() - withdrawAmount2);

                        DatabaseReference pushRef = pullRef2.child("balance");
                        DatabaseReference pushRef2 = pullRef3.child("balance");
                        pushRef.setValue(transferredAccount.getBalance());
                        pushRef2.setValue(mainAccount.getBalance());

                        System.out.println("You have successfully transferred " + withdrawAmount2 + " to " + transferTarget);
                        //System.out.println(transferredAccount.getBalance());
                        //System.out.println(mainAccount.getBalance());
                    }
                    else {
                        System.out.println("You cannot withdraw that many Myelin Bucks.");
                    }
                    break;
                case "deposit":
                    System.out.println("How much would you like to deposit?");
                    int depositAmount = scanner.nextInt();

                    System.out.println("Please enter the administrative password to deposit " + depositAmount + " into your account.");
                    int adminPass = scanner.nextInt();

                    if(adminPass == 9999) {
                        mainAccount.setBalance(mainAccount.getBalance() + depositAmount);
                        DatabaseReference pushRef4 = reference.child("Accounts/" + mainAccount.getUsername() + "/balance");

                        pushRef4.setValue(mainAccount.getBalance());
                        System.out.println("Great! You have deposited " + depositAmount);
                    } else {
                        System.out.println("That administrative password is incorrect");
                    }

                    break;
                case "checkbalance":
                    System.out.println("Your balance is: " + mainAccount.getBalance());
                    break;
                case "exit":
                    System.out.println("Shutting down.");
                    login = false;
                    System.exit(1);
                    break;
            }
        }
    }
}
