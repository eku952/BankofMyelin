import java.util.Scanner;



public class Main {

    private static int pin = 9999;
    private static boolean login = false;
    private static boolean startup = true;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Account.firebaseInit();

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
                Account.createNewUser();
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
