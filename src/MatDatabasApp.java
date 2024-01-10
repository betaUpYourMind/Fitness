import java.util.HashMap;
import java.util.Map;


public class MatDatabasApp {
    public static void macroTrack() {
        // Vi skapar en matdatabas
        MatDatabas matDatabas = new MatDatabas();
        matDatabas.addFood("Kyckling (grillad)", 165);
        matDatabas.addFood("Nötfärs (10% fett)", 250);
        matDatabas.addFood("Lax (bakad)", 206);
        matDatabas.addFood("Ris (kokt)", 130);
        matDatabas.addFood("Pasta (kokt)", 130);
        matDatabas.addFood("Ägg (kokt)", 155);
        matDatabas.addFood("Mjölk (hel)", 61);
        matDatabas.addFood("Broccoli (kokt)", 55);
        matDatabas.addFood("Potatis (kokt)", 77);
        matDatabas.addFood("Banan", 89);
        matDatabas.addFood("Avokado", 160);
        matDatabas.addFood("Havregryn (kokt)", 68);
        matDatabas.addFood("Mandel (rostad)", 579);
        matDatabas.addFood("Mörk choklad (70% kakao)", 604);
        matDatabas.addFood("Granatäpple", 83);
        matDatabas.addFood("Blåbär", 57);
        matDatabas.addFood("Havrebröd", 68);
        matDatabas.addFood("Röd paprika", 31);
        matDatabas.addFood("Olivolja", 884);
        matDatabas.addFood("Fetaost", 264);
        matDatabas.addFood("Yoghurt (grekisk)", 59);
        matDatabas.addFood("Gröna ärtor (kokta)", 81);
        matDatabas.addFood("Kalkonbröst (kokt)", 135);
        matDatabas.addFood("Quinoa (kokt)", 120);
        matDatabas.addFood("Cashewnötter (rostade)", 553);

        //Spara totala kalorier här
        double totalCalories = 0.0;
        //  Vi låter användaren välja mat

        while (true) {
            System.out.println("Lägga till mat?(Ja/Nej)");
            String läggatill = Main.scanner.nextLine().toLowerCase();
            if (läggatill.equals("nej"))
                break;

            System.out.println("Matlistan:");
            matDatabas.displayFoodList();
            System.out.print("Välj en produkt: ");
            String selectedFood = Main.scanner.nextLine().toLowerCase(); // Convert to lowercase


            // Vi låter användaren ange vikt i gram
            System.out.print("Hur många gram åt du?: ");
            double amountInGrams = scanner.nextDouble();

            scanner.nextLine();

            // Utifrån användarens val i selectedFoods skickar vi värdet till calculateCalories metoden.
            //Användares val i gram finns i amountInGrams, värde skickas till calculateCalories metoden.
            //Detta ges till variabeln calories som sedan presenteras
            double calories = matDatabas.calculateCalories(selectedFood, amountInGrams);
            System.out.println("Calories: " + calories);

            totalCalories += calories;
        }
        System.out.println("Totala kalorier: " + totalCalories);


        Main.scanner.nextLine();

    }

    static class MatDatabas {
        // En databas för att vi ska kunna lagra mat och deras kaloriinnehåll
        private Map<String, Double> foodMap;

        public MatDatabas() {
            this.foodMap = new HashMap<>();
        }

        public void addFood(String foodName, double caloriesPer100Grams) {
            foodMap.put(getFirstWord(foodName).toLowerCase(), caloriesPer100Grams);
        }

        public void displayFoodList() {
            for (String food : foodMap.keySet()) {
                System.out.println(capitalFirstLetter(food));
            }
        }
        public double calculateCalories(String foodName, double amountInGrams) {
            // Kontrollera om maten finns i databasen
            if (foodMap.containsKey(foodName.toLowerCase())) {
                double caloriesPer100Grams = foodMap.get(foodName.toLowerCase());
                return (caloriesPer100Grams / 100.0) * amountInGrams;
            } else {
                System.out.println("Maten hittades inte i databasen.");
                return 0;
            }
        }
        private String getFirstWord(String input) {
            return input.split("\\s+")[0];
        }
        private String capitalFirstLetter(String input) {
            return input.substring(0, 1).toUpperCase() + input.substring(1);
        }
    }
}
