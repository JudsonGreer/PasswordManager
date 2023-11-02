package assn07;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String,String> passwordManager = new PasswordManager<>();

        // your code below
        System.out.println("Enter Master Password");
        String pass = scanner.nextLine();
        while (passwordManager.checkMasterPassword(pass) != true) {
            System.out.println("Enter Master Password");
            pass = scanner.nextLine();
        }
        while (true) {
            String command = scanner.nextLine();
            //System.out.println("command: " + command);
            if (command.equals("New password")) {
                String newWeb = scanner.nextLine();
                String newPas = scanner.nextLine();
                //System.out.println("newWeb: " + newWeb);
                //System.out.println("newPas: " + newPas);
                passwordManager.put(newWeb, newPas);
                System.out.println("New password added");
            } else if (command.equals("Get password")) {
                String getWeb = scanner.nextLine();
                //System.out.println(getWeb);
                if (passwordManager.get(getWeb) != null) {
                    System.out.println(passwordManager.get(getWeb));
                } else {
                    System.out.println("Account does not exist");
                }

            } else if (command.equals("Delete account")) {
                String delWeb = scanner.nextLine();
                if (passwordManager.remove(delWeb) != null) {
                    System.out.println("Account deleted");
                } else {
                    System.out.println("Account does not exist");
                }

            } else if (command.equals("Check duplicate password")) {
                String passDup = scanner.nextLine();
                List<String> dupAccs = passwordManager.checkDuplicate(passDup);
                if (!dupAccs.isEmpty()) {
                    System.out.println("Websites using that password:");
                    //passwordManager.checkDuplicate(passDup);
                    dupAccs.forEach(System.out::println);
                } else {
                    System.out.println("No account uses that password");
                }
            } else if (command.equals("Get accounts")) {
                System.out.println("Your accounts:");
                passwordManager.keySet().forEach(System.out::println);
            } else if (command.equals("Generate random password")) {
                int len = scanner.nextInt();
                //System.out.println(passwordManager.generateRandomPassword(len));
                passwordManager.generateRandomPassword(len);
            } else if (command.equals("Exit")) {
                System.exit(0);
            } else {
                System.out.println("Command not found");
            }
        }
    }
}
