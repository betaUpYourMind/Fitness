import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BMI {


    public static void BMIcalc() {
        double weight = getValidNumericInput("Hur mycket väger du (i kg)?: ");
        double heightInCentimeters = getValidNumericInput("Hur lång är du (i cm)?: ");

        double heightInMeters = heightInCentimeters / 100.0;

        int bmi = (int) BMICalculator.calculateBMI(weight, heightInMeters);
        System.out.println('\n' + "BMI: " + bmi);

        char gender = getValidGenderInput();
        int age = getValidIntegerInput("Ange din ålder: ");

        int activityLevel = getValidActivityLevelInput();

        int suggestedCaloricIntake = suggestCaloricIntake(gender, age, weight, heightInMeters, activityLevel);
        System.out.println('\n' + "Baserat på din basalmetabolism, aktivitetsnivå och ålder är den rekommenderade kaloriintaget/dag: " + suggestedCaloricIntake + "kal" + '\n');

        int goal = getValidGoalInput();

        if (goal == 1) { //Behålla intag
            System.out.println("Ditt dagliga intag för att behålla din vikt är: " + suggestedCaloricIntake);
        } else if (goal == 2) { //Öka intag
            int caloriesForGain = suggestedCaloricIntake + 300;
            System.out.println("Ditt dagliga kaloriintag för att öka i vikt: " + caloriesForGain);
        } else if (goal == 3) { //Minska intag
            double intendedCaloricDeficitPerDay = getValidNumericInput("Ange hur stor kaloriunderskott du vill ha (i kalorier): ");
            double kilogramsToLose = getValidNumericInput("Ange hur många kilo du vill gå ner: ");

            int daysToReachGoal = calculateDaysToReachGoal(intendedCaloricDeficitPerDay, kilogramsToLose);

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, daysToReachGoal);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateToReachGoal = sdf.format(calendar.getTime());

            System.out.println("För att gå ner " + kilogramsToLose + " kilo med ett underskott på "
                    + intendedCaloricDeficitPerDay + " kalorier, kan du nå ditt mål om " + daysToReachGoal + " dagar, datum: " + dateToReachGoal);
        }
    }

    static class BMICalculator {
        public static double calculateBMI(double weight, double height) {
            return weight / (height * height);
        }
    }

    static char getValidGenderInput() {
        char gender = ' ';
        boolean validInput = false;
        while (!validInput) {
            System.out.print('\n' + "M  = Man" +'\n' + "K = Kvinna" + '\n' + "Ange ditt kön (M/K)/ : ");
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

    static int getValidGoalInput() {
        int goal = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print("1. Behålla" + '\n' + "2. Öka" + '\n' + "3. Minska" + '\n' + "Vad är ditt mål med din vikt? ");
            goal = getValidIntegerInput("");
            if (goal == 1 || goal == 2 || goal == 3) {
                validInput = true;
            } else {
                System.out.println("Ogiltigt val, vänligen välj bland tillgängliga val.");
            }
        }
        return goal;
    }

    static int getValidActivityLevelInput() {
        int activityLevel;
        boolean validInput = false;
        do {
            System.out.print('\n' + "1. Stillasittande" + '\n' + "2. Lätt" + '\n' + "3. Måttlig" + '\n' + "4. Tung" + '\n' + "Vad är din aktivitetsnivå? ");
            activityLevel = getValidIntegerInput("");
            if (activityLevel == 1 || activityLevel == 2 || activityLevel == 3 || activityLevel == 4) {
                validInput = true;
            } else {
                System.out.println("Ogiltigt val, vänligen välj bland tillgängliga val:");
            }
        } while (!validInput);
        return activityLevel;
    }

    static int suggestCaloricIntake(char gender, int age, double weight, double height, int activityLevel) {
        int suggestedCaloricIntake;
        double bmr; //Basal Metabolic Rate, antal kalorier/dag som kroppen behöver.
        if (gender == 'M') {
            bmr = 88.362 + (13.397 * weight) + (4.799 * height * 100) - (5.677 * age);
        } else {
            bmr = 447.593 + (9.247 * weight) + (3.098 * height * 100) - (4.330 * age);
        }

        double tdee; //Total Daily Energy Expenditure, antal kalorier per dag som kroppen förbrukar.

        switch (activityLevel) {
            case 1: //stillasittande
                tdee = bmr * 1.2;
                break;
            case 2: //lätt
                tdee = bmr * 1.375;
                break;
            case 3: //måttlig
                tdee = bmr * 1.55;
                break;
            case 4: //tung
                tdee = bmr * 1.725;
                break;
            default: //Ogiltig inmatning som exempelvis negativa tal eller strängar
                tdee = bmr;
                System.out.println("Ogiltig aktivitetsnivå. Utgår ifrån stillasittande.");
                break;
        }
        // Det rekommenderade kaloriintaget kommer aldrig understiga 1400 kalorier dagligen.
        suggestedCaloricIntake = Math.max((int) tdee, 1400);
        return suggestedCaloricIntake;
    }

    static int calculateDaysToReachGoal(double intendedCaloricDeficitPerDay, double kilogramsToLose) {
        return (int) Math.ceil((kilogramsToLose * 7700) / intendedCaloricDeficitPerDay);
    }
}
