import java.text.SimpleDateFormat;
import java.util.Calendar;
/**BMICalc klassen kan förse och räkna ut information relaterad till BMI, kalorier och mål för viktuppgång/viktnedgång*/
public class BMI {

    /**BMICalc är en metod för att avgöra kaloriintag och förse användaren med mål för viktuppgång/viktnedgång  */
    public static void BMIcalc() {
        //Vi tar användarens input på vikt och längd
        double weight = getValidNumericInput("Hur mycket väger du?: ");
        double heightInCentimeters = getValidNumericInput("Hur lång är du?(i cm): ");
        //Vi konverterar användarens input(i cm) till meter för att kunna räkna ut BMI.
        double heightInMeters = heightInCentimeters / 100.0;

        //Vi räknar ut anvädarens BMI och presenterar resultatet
        int bmi = (int) BMICalculator.calculateBMI(weight, heightInMeters);
        System.out.println("Din BMI är: " + bmi);

        //Vi hämtar användarens input för kön, ålder och aktivitetsnivå
        char gender = getValidGenderInput();
        int age = getValidIntegerInput("Ange din ålder: ");
        String activityLevel = getValidActivityLevelInput();

        //Vi räknar ut kaloriintag baserat på användarens input
        int suggestedCaloricIntake = suggestCaloricIntake(gender, age, weight, heightInMeters, activityLevel);
        System.out.println("Baserat på din basalmetabolism, aktivitetsnivå och ålder är den rekommenderade kaloriintaget/dag: " + suggestedCaloricIntake + "kal");

        //Vi tar användarens input baserat på deras mål och förser de med rekommenderad kaloriintag
        String goal = getValidGoalInput();
        if (goal.equalsIgnoreCase("Behålla")) {
            System.out.println("Ditt dagliga intag för att behålla din vikt är: " + suggestedCaloricIntake);
        } else if (goal.equalsIgnoreCase("Öka")) {
            int caloriesForGain = suggestedCaloricIntake + 300;
            System.out.println("Ditt dagliga kaloriintag för att öka i vikt: " + caloriesForGain);
        } else if (goal.equalsIgnoreCase("Minska")) {
            //Förser användaren med info för mål viktnedgång
            double intendedCaloricDeficitPerDay = getValidNumericInput("Ange hur stor kaloriunderskott du vill ha (i kalorier): ");
            double kilogramsToLose = getValidNumericInput("Ange hur många kilo du vill gå ner: ");

            //Räknar ut dagar tills målet nås
            int daysToReachGoal = calculateDaysToReachGoal(intendedCaloricDeficitPerDay, kilogramsToLose);

            //Vi visar datumet när deras viktnegång kan upppnås till
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, daysToReachGoal);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateToReachGoal = sdf.format(calendar.getTime());

            System.out.println("För att gå ner " + kilogramsToLose + " kilo med ett underskott på "
                    + intendedCaloricDeficitPerDay + " kalorier, kan du nå ditt mål om " + daysToReachGoal + " dagar, datum: " + dateToReachGoal);
        }
    }
    /**En nästlad klass för uträkning av BMI  */
    static class BMICalculator {
        public static double calculateBMI(double weight, double height) {
            return weight / (height * height);
        }
    }
    //Metod för att se till att användaren lägger in korrekt input av kön
    static char getValidGenderInput() {
        char gender = ' ';
        boolean validInput = false;
        while (!validInput) {
            System.out.print("Ange ditt kön(M/K): ");
            String input = Main.scanner.next().toUpperCase();
            if (input.equals("M") || input.equals("K")) {
                gender = input.charAt(0);
                validInput = true;
            } else {
                System.out.println("Ogiltig köninmatning. Ange endast 'M' eller 'K'.");
            }
        }
        return gender;
    }
    //Metod för att se till att användarens input är en giltig integer
    static int getValidIntegerInput(String message) {
        int value = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print(message);
                value = Integer.parseInt(Main.scanner.next());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Ogiltigt val, vänligen välj en giltig siffra.");
            }
        }

        Main.scanner.nextLine();

        return value;
    }
    //Metod för att se till att användarens input är en siffra
    static double getValidNumericInput(String message) {
        double value = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print(message);
                value = Double.parseDouble(Main.scanner.next());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Ogiltigt val, vänligen välj en giltig siffra.");
            }
        }
        return value;
    }
    //Metod för att se till att användarens input matchar de tillgängliga viktmålsalternativen
    static String getValidGoalInput() {
        String goal = "";
        boolean validInput = false;
        while (!validInput) {
            System.out.print("Vad är ditt mål med din vikt? (Behålla/Öka/Minska): ");
            goal = Main.scanner.next();
            if (goal.equalsIgnoreCase("behålla") || goal.equalsIgnoreCase("öka") || goal.equalsIgnoreCase("minska")) {
                validInput = true;
            } else {
                System.out.println("Ogiltigt val, vänligen välj bland tillgängliga val.");
            }
            Main.scanner.nextLine();
        }
        return goal;
    }
    //Metod för att se till att användarens input matchar de tillgängliga aktivitetsnivåerna
    static String getValidActivityLevelInput() {
        String activityLevel;
        boolean validInput = false;
        do {
            System.out.print("Vad är din aktivitetsnivå? (stillasittande/lätt/måttlig/tung): ");
            activityLevel = Main.scanner.next().toLowerCase().replace(" ", "");
            if (activityLevel.equals("stillasittande") || activityLevel.equals("lätt") || activityLevel.equals("måttlig") || activityLevel.equals("tung")) {
                validInput = true;
            } else {
                System.out.println("Ogiltigt val, vänligen välj bland tillgängliga val:");
            }
        } while (!validInput);
        return activityLevel;
    }
    //Metod för att föreslå kaloriintag baserat på anvädarens input
    static int suggestCaloricIntake(char gender, int age, double weight, double height, String activityLevel) {
        int suggestedCaloricIntake;
        double bmr;
        //Räkna ut basalmetabolism baserat på kön
        if (gender == 'M') {
            bmr = 88.362 + (13.397 * weight) + (4.799 * height * 100) - (5.677 * age);
        } else {
            bmr = 447.593 + (9.247 * weight) + (3.098 * height * 100) - (4.330 * age);
        }

        double tdee;

        switch (activityLevel) {
            case "stillasittande":
                tdee = bmr * 1.2;
                break;
            case "lätt":
                tdee = bmr * 1.375;
                break;
            case "måttlig":
                tdee = bmr * 1.55;
                break;
            case "tung":
                tdee = bmr * 1.725;
                break;
            default:
                tdee = bmr;
                System.out.println("Ogiltig aktivitetsnivå. Utgår ifrån stillasittande.");
                break;
        }
        //Ser till att den rekommenderade kaloriintaget är minst 1400 kalorier
        suggestedCaloricIntake = Math.max((int) tdee, 1400);
        return suggestedCaloricIntake;
    }
    //Metod för att räkna ut antal dagar tills målet nås
    static int calculateDaysToReachGoal(double intendedCaloricDeficitPerDay, double kilogramsToLose) {
        return (int) Math.ceil((kilogramsToLose * 7700) / intendedCaloricDeficitPerDay);
    }
}
