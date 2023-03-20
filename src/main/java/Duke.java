import java.util.*;

public class Duke {
    private static ArrayList<Task> itemList = new ArrayList<>();

    public static void main(String[] args) {

        printIntroductionGreeting();
        Scanner input = new Scanner(System.in);

        while(true) {
            System.out.print("Enter input : ");
            String userInput = input.nextLine().trim();

            switch (userInput) {
                case "list":
                    printItemList();
                    break;
                case "bye":
                    printEndGreeting();
                    return;
                default:
                    if(isItem(userInput)) {
                        if (isItemExists(userInput) == false) {
                            Task item = new Task(userInput);
                            itemList.add(item);
                            System.out.println("Item added");
                        } else {
                            System.out.println("Item already exists. Please add another item.");
                        }
                    }
                    String[] itemArray = userInput.split(" ", 2);
                    if(itemArray.length > 1 && isNumeric(itemArray[1]) == true) {
                        if(itemArray[0].equals("mark")) {
                            markOrUnmarkItem(Integer.parseInt(itemArray[1]), true);
                        }
                        if(itemArray[0].equals("unmark")) {
                            markOrUnmarkItem(Integer.parseInt(itemArray[1]), false);
                        }
                    }
                    break;
            }
        }
    }

    public static void printIntroductionGreeting() {
        String borderLine = "____________________________________________________________";
        String greetings = "Hello! I'm Duke \n\tWhat can I do for you";
        System.out.printf("\t%s\n\t%s\n\t%s\n",borderLine, greetings, borderLine);
    }
    public static void printEndGreeting() {
        String borderLine = "____________________________________________________________";
        String greetings = "Bye. Hope to see you again soon!";
        System.out.printf("\t%s\n\t%s\n\t%s\n",borderLine, greetings, borderLine);
    }

    public static Boolean isItem(String input) {
        if(input.contains("list") || input.contains("mark") || input.contains("unmark") || input.equals(" ")) {
            return false;
        }
        return true;
    }

    public static boolean isItemExists(String item) {
        for(int i=0; i<itemList.size(); i++) {
            if(itemList.get(i).getTaskName().equalsIgnoreCase(item)) {
                return true;
            }
        }
        return false;
    }

    private static String getStatusSymbol(boolean status) {
        if(status == true) {
            return "X";
        }
        return " ";
    }

    private static void printItemList() {
        String borderLine = "\t____________________________________________________________";
        System.out.println(borderLine);
        for(int i=0; i<itemList.size(); i++) {
            if(isItem(itemList.get(i).getTaskName())) {
                System.out.println("\t" + (i + 1) + ". [" + getStatusSymbol(itemList.get(i).getStatus()) + "] " + itemList.get(i).getTaskName() + "\t");
            }
        }
        System.out.println(borderLine);
    }

    private static void printMarkOrUnmarkItem(Boolean status) {
        if(status == true) {
            System.out.println("\tNice! I've marked this task as done:");
        } else {
            System.out.println("\tOK, I've marked this task as not done yet:");
        }
    }

    private static void markOrUnmarkItem(Integer itemNo, Boolean status) {
        String borderLine = "\t____________________________________________________________";
        System.out.println(borderLine);
        for(int i=0; i<itemList.size(); i++) {
            if(i+1 == itemNo) {
                itemList.get(i).setStatus(status);
                printMarkOrUnmarkItem(status);
                System.out.println("\t [" + getStatusSymbol(itemList.get(i).getStatus()) + "] " + itemList.get(i).getTaskName() + "\t");
            }
        }
        System.out.println(borderLine);
    }

    public static boolean isNumeric(String itemNo) {
        if (itemNo == " ") {
            return false;
        }
        try {
            Integer.parseInt(itemNo);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}