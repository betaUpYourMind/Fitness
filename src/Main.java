import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        String choice;
        while (true){
            System.out.print("*** VÄLKOMMEN TILL FITNESSAPP ***\n");
            System.out.print("1. BMI Calculator\n");
            System.out.print("2. Macro tracker\n");
            System.out.print("3. Avsluta programmet\n");
            System.out.print("Ange 1 eller 2 för att använda appens funktioner: ");

            choice = scanner.nextLine();


            if (!choice.isEmpty()) {
                if (Objects.equals(choice, "1")) {
                    BMI.BMIcalc();
                } else if (Objects.equals(choice, "2")) {
                    MatDatabasApp.macroTrack();
                } else if (Objects.equals(choice, "3")) {
                    break;
                } else {
                    System.out.println("Vänligen ange giltigt val.");
                }
            }

        }
        scanner.close();
    }
}
