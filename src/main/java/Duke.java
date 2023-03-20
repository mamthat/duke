import java.util.*;

public class Duke {
    private static ArrayList<Task> taskList = new ArrayList<>();

    public static void main(String[] args) {

        printIntroductionGreeting();
        Scanner input = new Scanner(System.in);
        while(true) {
            System.out.print("Enter input : ");
            String userInput = input.nextLine().trim();

            switch (userInput) {
                case "list":
                    printTasksList();
                    break;
                case "bye":
                    printEndGreeting();
                    return;
                default:
                    if(isTask(userInput)) {  
                        if (isTaskExists(userInput) == false) {
                            Task task = new Task(userInput);
                            taskList.add(task);
                            System.out.println("Item added");
                        } else {
                            System.out.println("Item already exists. Please add another item.");
                        }
                    }

                        if(userInput.contains("mark") || userInput.contains("unmark")) {
                            markOrUnmarkTask(userInput);
                        }
                    
                        if(userInput.contains("todo")) {
                            setToDoTask(userInput);
                        }

                        if(userInput.contains("deadline")) {
                            setDeadlineTask(userInput);
                        }
                    
                        if(userInput.contains("event")) {
                            setEventTask(userInput);
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

    public static boolean isNumeric(String taskNo) {
        if (taskNo == " ") {
            return false;
        }
        try {
            Integer.parseInt(taskNo);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static Boolean isTask(String input) {
        if(input.contains("list") || input.contains("mark") 
        || input.contains("unmark") || input.contains("todo")
        || input.contains("deadline") || input.contains("event") 
        || input.equals(" ")) {
            return false;
        }
        return true;
    }

    public static boolean isTaskExists(String task) {
        for(int i=0; i<taskList.size(); i++) {
            if(taskList.get(i).getTaskName().equalsIgnoreCase(task)) {
                return true;
            }
        }
        return false;
    }

    private static void printTasksList() {
        String borderLine = "\t____________________________________________________________";
        System.out.println(borderLine);
        for(int i=0; i<taskList.size(); i++) {
            if(isTask(taskList.get(i).getTaskName())) {
                System.out.println("\t " + (i+1) + ". " +taskList.get(i).toString()+ "\t");
            }
        }
        System.out.println(borderLine);
    }

    private static void printMarkOrUnmarkTask(Boolean status) {
        if(status == true) {
            System.out.println("\tNice! I've marked this task as done:");
        } else {
            System.out.println("\tOK, I've marked this task as not done yet:");
        }
    }

    private static void markOrUnmarkTask(String userInput) {
        try{
            if(userInput.split(" ", 2).length > 1 && isNumeric(userInput.split(" ", 2)[1]) == false) {
                throw new DukeException();
            }

            boolean status = false;
            if(userInput.split(" ", 2)[0].equals("mark")) {
                status = true;
            }
            else {
                status = false;
            }

            int taskNo = Integer.parseInt(userInput.split(" ", 2)[1]);
            String borderLine = "\t____________________________________________________________";
            System.out.println(borderLine);
            for(int i=0; i<taskList.size(); i++) {
                if(i+1 == taskNo) {
                    if(taskList.get(i).getStatus() != status) {
                        taskList.get(i).setStatus(status);
                        printMarkOrUnmarkTask(status);
                        System.out.println("\t " + taskList.get(i).toString()+ "\t");
                    } else {
                        System.out.println("\tTask status is already set");
                    }
                }
            }
            System.out.println(borderLine);
        } catch(ArrayIndexOutOfBoundsException _exception) {
            System.out.println("☹ OOPS!!! The description of a todo cannot be empty.");
        } catch(DukeException exception) {
            System.out.println("☹ OOPS!!! The description of a todo cannot be empty.");
        } 
    }

    private static void setToDoTask(String userInput) {
        try{
            String[] taskArray = userInput.split(" ", 2);
            String taskName = taskArray[1];

            if(taskName == "") 
                throw new DukeException();

            String borderLine = "\t____________________________________________________________";
            System.out.println(borderLine); 

            for(int i=0; i<taskList.size(); i++) {
                if(taskList.get(i).taskName.equalsIgnoreCase(taskName)) {
                    Task toDoTask = new Todo(taskName, true);
                    toDoTask.setStatus(taskList.get(i).getStatus()); 
                    taskList.set(i, toDoTask);
                    printTaskUpdates(toDoTask.toString());    
                }
            }

            System.out.println(borderLine);
        } catch(ArrayIndexOutOfBoundsException _exception) {
            System.out.println("☹ OOPS!!! The description of a todo cannot be empty.");
        } catch(DukeException exception) {
            System.out.println("☹ OOPS!!! The description of a todo cannot be empty.");
        } 
    }

    private static void setDeadlineTask(String userInput) {
        try{
            String[] taskArray = userInput.split(" ", 2);
            String taskInputDetails = taskArray[1];

            if(taskInputDetails == "") 
                throw new DukeException();

            String borderLine = "\t____________________________________________________________";
            System.out.println(borderLine); 

            String[] taskDetails = taskInputDetails.split("/", 2);
            String taskName = taskDetails[0].trim();
            String day = taskDetails[1].split(" ")[1].trim();

            for(int i=0; i<taskList.size(); i++) {
                if(taskList.get(i).taskName.equalsIgnoreCase(taskName)) {
                    Task deadlineTask = new Deadline(taskName, day);
                    deadlineTask.setStatus(taskList.get(i).getStatus()); 
                    taskList.set(i, deadlineTask);
                    printTaskUpdates(deadlineTask.toString());
                } 
            }

        System.out.println(borderLine);
        } catch(ArrayIndexOutOfBoundsException _exception) {
            System.out.println("☹ OOPS!!! The description of a deadline cannot be empty.");
        } catch(DukeException exception) {
            System.out.println("☹ OOPS!!! The description of a deadline cannot be empty.");
        } 
    }

    private static void setEventTask(String userInput) {
        try{
            String[] taskArray = userInput.split(" ", 2);
            String taskInputDetails = taskArray[1];

            if(taskInputDetails == "") 
                throw new DukeException();

            String borderLine = "\t____________________________________________________________";
            System.out.println(borderLine); 

            String[] taskDetails = taskInputDetails.split("/", 3);
            String taskName = taskDetails[0].trim();
            String from = taskDetails[1].trim();
            String to = taskDetails[2].trim();

            for(int i=0; i<taskList.size(); i++) {
                if(taskList.get(i).taskName.equalsIgnoreCase(taskName)) {
                    Task eventTask = new Event(taskName, from, to);
                    eventTask.setStatus(taskList.get(i).getStatus()); 
                    taskList.set(i, eventTask);
                    printTaskUpdates(eventTask.toString());
                }
            }

        System.out.println(borderLine);
        } catch(ArrayIndexOutOfBoundsException _exception) {
            System.out.println("☹ OOPS!!! The description of an event cannot be empty.");
        } catch(DukeException exception) {
            System.out.println("☹ OOPS!!! The description of an event cannot be empty.");
        } 
    }


    private static void printTaskUpdates(String taskDetails) {
        System.out.println("\tGot it. I've added this task:" + "\n\t " + taskDetails + "\n\tNow you have " + taskList.size() + " in the list.");
    }
}