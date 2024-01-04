import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Få info om vikt och längd från användare
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your weight in kilograms: ");
        double weight = scanner.nextDouble();
        System.out.print("Enter your height in centimeters: ");
        double heightInCentimeters = scanner.nextDouble();
        // Vi konverterar längden till meter
        double heightInMeters = heightInCentimeters / 100.0;
        // Vi använder oss av BMICalculator klassen för att räkna ut bmi utifrån användarens inpit
        double bmi = BMICalculator.calculateBMI(weight, heightInMeters);
        // Vi skriver ut BMIn
        System.out.println("BMI: " + bmi);

        scanner.close();
    }

    static class BMICalculator {
        public static double calculateBMI(double weight, double height) {
            // Uträkning av BMI logik bakom
            return weight / (height * height);
        }
    }
}