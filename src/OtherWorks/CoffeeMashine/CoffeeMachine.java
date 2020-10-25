package OtherWorks.CoffeeMashine;

import java.util.*;
import java.util.concurrent.TimeUnit;

class CoffeeMachineTest {
    public static void main(String[] args) throws InterruptedException {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        coffeeMachine.start();
    }
}

/*
 TODO:
  - There is a bug if you entering order with spaces it crashes
  - There is a bug that even if the additive ends up you still can add it
  though it isn't being displayed at list
 */

public class CoffeeMachine {
    private static final Scanner sc = new Scanner(System.in);
    // may make getting data from console or file
    // store information about remaining ingredients
    private int[] ingredientsStorage = new int[]{5, 5};
    private int[] additionsStorage = new int[]{5, 5, 3, 3, 2, 2, 1};

    public void start() throws InterruptedException {
        int code;
        while (true) {
            // checking if ingredients ran out
            if (ingredientsStorage[CoffeeMachine.Ingredients.WATER.ordinal()] == 0 ||
                    ingredientsStorage[CoffeeMachine.Ingredients.COFFEE_BEANS.ordinal()] == 0) {
                System.out.println("Sorry, ingredients ran out");
                shutDown();
                return;
            }
            // first choice: continue or not
            System.out.println("To pick what you want enter the matching code");
            System.out.println("Exit -> 0 \nMake coffee -> 1 \nCheck storage -> 2");
            code = sc.nextInt();
            sc.nextLine(); // crutch

            switch (code) {
                case 0: // exiting
                    shutDown();
                case 1: // starting making coffee
                    makeCoffee();
                    return;
                case 2: // checking storage
                    checkStorage();
                    break;
                default: // unidentified input
                    System.out.println("Incorrect command, try again...");
            }
        }
    }

    //just making a base cup of coffee, without any additives
    private void makeCoffee() throws InterruptedException {
        System.out.println("Boiling water...");
        TimeUnit.SECONDS.sleep(1);
        ingredientsStorage[Ingredients.WATER.ordinal()]--;
        System.out.println("Making black coffee...");
        ingredientsStorage[Ingredients.COFFEE_BEANS.ordinal()]--;
        TimeUnit.SECONDS.sleep(1);
        addAdditives();
    }

    // adding additives to the base coffee
    private void addAdditives() throws InterruptedException {
        //line with digits representing order, entered by user
        String codeLine = getOrder();
        ArrayList<Additions> addedAdditives = new ArrayList<>();
        if (isOrderOK(codeLine)) {
            for (int i = 0; i < codeLine.length(); i++) {
                int whatToAddCode = codeLine.charAt(i) - '0';
                additionsStorage[whatToAddCode - 1]--;
                addedAdditives.add(Additions.values()[whatToAddCode - 1]);
            }
            dispenseCoffee(addedAdditives);
        } else {
            // if code entered by user is invalid, trying to perform operation again
            addAdditives();
        }
    }

    // there user gets info about what he can add to his order and then entering codeLine
    private String getOrder() {
        Additions[] additions = Additions.values();
        System.out.println("Choose what you want to add, you may choose several additives: " +
                "\nNothing -> 0");

        // displaying all available additives
        for (int i = 0; i < Additions.size(); i++) {
            if (additionsStorage[i] != 0)
                System.out.println(additions[i].toString().charAt(0) +
                        additions[i].toString().substring(1).toLowerCase() + " -> " + (i + 1));
        }
        return sc.nextLine();
    }

    // checks if codeLine satisfy format
    private boolean isOrderOK(String codeLine) throws InterruptedException {
        // if contains 0, then order is just a black coffee
        if (codeLine.contains("0")) {
            dispenseCoffee(new ArrayList<>());
            return false;
        }
        // deleting spaces for easier information processing
        codeLine = codeLine.replaceAll(" ", "");
        // checking if some additive repeats in user's order
        boolean[] checkList = new boolean[Additions.size()];
        for (int i = 0; i < codeLine.length(); i++) {
            int whatToAddCode = codeLine.charAt(i) - '0';
            if (whatToAddCode > Additions.size()) {
                System.out.println("\nThere is no such additive!\n");
                return false;
            }
            if (checkList[whatToAddCode - 1]) {
                System.out.println("\nYou can't choose more than one of each additive!\n");
                // if something wrong trying to add additives again
                return false;
            }
            checkList[whatToAddCode - 1] = true;
        }
        // if everything OK continue completing order
        return true;
    }

    // displaying info about ready order
    private void dispenseCoffee(ArrayList<Additions> additions) throws InterruptedException {
        System.out.println("Adding additives...");
        TimeUnit.SECONDS.sleep(1);
        System.out.print("Your coffee");
        for (int i = 0; i < additions.size(); i++) {
            if (i == 0) System.out.print(" with ");
            else if (i == additions.size() - 1) System.out.print(" and ");
            else System.out.print(", ");
            System.out.print(additions.get(i).toString().toLowerCase());

        }
        System.out.println(" is ready!\n" +
                "-----------------------------------------------------------------");
        // going to next order
        start();
    }

    // simple displaying data from arrays
    private void checkStorage() {
        Ingredients[] ingredients = Ingredients.values();
        Additions[] additions = Additions.values();
        int counter;
        String expression;
        System.out.println("Remaining ingredients: ");
        for (int i = 0; i < Ingredients.size(); i++) {
            counter = ingredientsStorage[i];
            expression = counter == 1 ? " portion of " : " portions of ";
            System.out.println(counter + expression + ingredients[i].toString().charAt(0) +
                    ingredients[i].toString().substring(1).toLowerCase().replaceAll("_", " "));
        }
        System.out.println("\nRemaining additives: ");
        for (int i = 0; i < Additions.size(); i++) {
            counter = additionsStorage[i];
            expression = counter == 1 ? " portion of " : " portions of ";
            System.out.println(counter + expression + additions[i].toString().charAt(0) +
                    additions[i].toString().substring(1).toLowerCase().replaceAll("_", " "));
        }
        System.out.println("-----------------------------------------------------------------");
    }

    // ends work
    private void shutDown() {
        System.out.println("Shutting down system...");
        System.exit(0);
    }

    private enum Ingredients {
        WATER,
        COFFEE_BEANS;

        static int size() {
            return Ingredients.values().length;
        }
    }

    private enum Additions {
        SUGAR,
        MILK,
        CHOCOLATE,
        CARAMEL,
        MINT,
        BANANA,
        NUTS;

        static int size() {
            return Additions.values().length;
        }
    }
}




