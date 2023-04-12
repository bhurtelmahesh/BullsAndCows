package bullscows;
import java.util.Scanner;
import java.util.Random;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static int requiredLength;
    public static String code;
    public static String secret;
    public static int bulls = 0;
    public static int cows = 0;
    public static int turn = 1;
    public static Random rand = new Random();
    public static long pseudoRandomNumber = (long)(rand.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
    public static StringBuilder randomCode = new StringBuilder();
    public static char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    public static int inAlpha; // first symbols of alpha according to possible symbols
    public static int myRand = (int) Math.floor(Math.random() * 10); // random number between 0 and 9
    public static char[] newChar;
    public static int symbolCount;
    public static StringBuilder stars = new StringBuilder("*");

    public static void main(String[] args) {
        generate();
        switch (requiredLength) {
            case 3 -> stars.append("**");
            case 4 -> stars.append("***");
            case 5 -> stars.append("****");
            case 6 -> stars.append("*****");
            case 7 -> stars.append("******");
            case 8 -> stars.append("*******");
            case 9 -> stars.append("********");
            case 10 -> stars.append("*********");
            default -> stars.append("**********");
        }
        switch (symbolCount) {
            case 11 -> System.out.println("The secret is prepared: " + stars + " (0-9, a)");
            case 12 -> System.out.println("The secret is prepared: " + stars + " (0-9, a-b)");
            case 13 -> System.out.println("The secret is prepared: " + stars + " (0-9, a-c)");
            case 14 -> System.out.println("The secret is prepared: " + stars + " (0-9, a-d)");
            case 15 -> System.out.println("The secret is prepared: " + stars + " (0-9, a-e)");
            case 16 -> System.out.println("The secret is prepared: " + stars + " (0-9, a-f)");
            case 17 -> System.out.println("The secret is prepared: " + stars + " (0-9, a-g)");
            case 18 -> System.out.println("The secret is prepared: " + stars + " (0-9, a-h)");
            case 19 -> System.out.println("The secret is prepared: " + stars + " (0-9, a-i)");
            case 20 -> System.out.println("The secret is prepared: " + stars + " (0-9, a-j)");
            default -> System.out.println("The secret is prepared: " + stars + " (0-9, a-z)");
        }

        System.out.println("Okay, let's start a game!");
        System.out.println("Turn " + turn + ":");
        scanner.nextLine();
        code = scanner.nextLine();
        checkAll();
        scanner.close();
    }

    public static void generate() {
        System.out.println("Please enter the secret code's length:");
        String a = scanner.nextLine();
        try {
            requiredLength = Integer.parseInt(a);
        } catch (Exception e) {
            System.out.println("Error: " +"'" + a + "' isn't a valid number" );
            System.exit(0);
        }
        if(requiredLength == 0) {
            System.out.println("Error: the secret code's length must be greater than 0.");
            System.exit(0);
        }

        System.out.println("Input thr number of possible symbols in the code:");
        symbolCount = scanner.nextInt();
        if (symbolCount > 36) {
            System.out.println("Error: the possible range is 36 (0-9, a-z)");
            System.exit(0);
        }
        if (requiredLength > symbolCount) {
            System.out.println("Error: it's not possible to generate a code with a length of " + requiredLength + " with " + symbolCount + "unique symbols.");
            System.exit(0);
        }
        if (requiredLength > 10 ){
            System.out.println("Error: can't generate a secret number with a length of 11 because there aren't enough unique digits.");
        } else {

            if (symbolCount <= 10){
                randomCode.append(pseudoRandomNumber);
            } else {
                inAlpha = symbolCount-10; // first symbols of alpha according to possible symbols
                // random number between 0 and 9
                newChar = new char[inAlpha]; // new char array of extra length
                for (int i = 0; i < inAlpha; i++) {
                    newChar[i] = alphabet[i];   // copy the first symbols of alpha to new char array
                    randomCode.append(pseudoRandomNumber);
                    randomCode.append(newChar[i]); // append the new char array to randomCode
                    randomCode.append(randomCode.toString().charAt(myRand)); // append the random number to randomCode
                }
            }

            if(randomCode.length() < requiredLength){
                pseudoRandomNumber = (long)(rand.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
                randomCode.append(pseudoRandomNumber);
            }
            randomCode.reverse();
            randomCode.delete(requiredLength,randomCode.length());


            for (int i = 0; i < randomCode.length(); i++) {
                for (int j = 0 ; j < randomCode.length(); j++) {
                    while(i != j && randomCode.charAt(i) == randomCode.charAt(j)) {
                        Random random = new Random();
                        int n = random.nextInt(10);
                        randomCode.setCharAt(j, String.valueOf(n).charAt(0));
                    }
                }
            }

            secret = randomCode.toString();
        }
    }


    public static void checkAll(){
        checkBulls();
        checkCows();

        if(bulls == 0 && cows == 0) {
            System.out.println("Grade: None.");
            turn++;
            System.out.println("Turn " + turn + ":");
            reset();
            code = scanner.nextLine();
            checkAll();
        } else if (bulls == secret.length()) {
            System.out.println("Grade: " + bulls + " bulls");
            System.out.println("Congratulations! You guessed the secret code.");
            System.exit(0);
        } else if (cows > 1  & bulls > 1) {
            System.out.println("Grade: "+ bulls + " bulls " +  cows + " cows");
            turn++;
            System.out.println("Turn " + turn + ":");
            reset();
            code = scanner.nextLine();
            checkAll();
        } else if (cows == 1 && bulls == 1 ) {
            System.out.println("Grade: " + bulls + " bull and " + cows + " cow");
            turn++;
            System.out.println("Turn " + turn + ":");
            reset();
            code = scanner.nextLine();
            checkAll();
        }else if (bulls == 0 && cows > 1){
            System.out.println("Grade: " + cows +" cows");
            turn++;
            System.out.println("Turn " + turn + ":");
            reset();
            code = scanner.nextLine();
            checkAll();

        }else if (bulls == 1){
            System.out.println("Grade: " + bulls +" bull");
            turn++;
            System.out.println("Turn " + turn + ":");
            reset();
            code = scanner.nextLine();
            checkAll();

        }else if (cows == 1){
            System.out.println("Grade: " + cows +" cow");
            turn++;
            System.out.println("Turn " + turn + ":");
            reset();
            code = scanner.nextLine();
            checkAll();

        } else {
            System.out.println("Grade: " + bulls +" bulls");
            turn++;
            System.out.println("Turn " + turn + ":");
            reset();
            code = scanner.nextLine();
            checkAll();

        }
    }

    public static boolean isBull(int i) {
        return code.charAt(i) == String.valueOf(secret).charAt(i);
    }

    public static void checkBulls() {
        for (int i = 0; i < secret.length(); i++) {
            if (isBull(i)) {
                bulls++;
            }
        }
    }

    public static void checkCows(){
        for (int i = 0; i < secret.length(); i++) {
            if(!isBull(i)) {
                for (int j = 0; j < secret.length(); j++) {
                    if (i != j && code.charAt(i) == String.valueOf(secret).charAt(j)) {
                        cows++;
                        break;
                    }
                }
            }
        }
    }

    public static void reset(){
        bulls = 0;
        cows = 0;
    }
}
