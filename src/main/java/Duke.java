import java.util.*;
import java.io.*;

public class Duke {
    private static ArrayList<Task> taskList = new ArrayList<>();
    private static String borderLine = "\t____________________________________________________________";

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
                        setToDoTask(userInput, false, null);
                    }

                    if(userInput.contains("deadline")) {
                        setDeadlineTask(userInput, false, null);
                    }
                    
                    if(userInput.contains("event")) {
                        setEventTask(userInput, false, null);
                    }     

                    if(userInput.contains("delete")) {
                        removeTask(userInput);
                    }  

                    if(userInput.contains("bulk add")) {
                        bulkAddTasksFromFile(userInput);
                    }  
                    
                    break;
            }
        }
    }

    public static void printIntroductionGreeting() {
        String borderLine = "____________________________________________________________";
        String greetings = "Hello! I'm Duke \n\tWhat can I do for you \n\t(Enter tasks individually or add tasks in ../data/duke.text and hit 'bulk add' to bulk add tasks ";
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
        || input.contains("delete") || input.contains("bulk add") 
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

    private static void setToDoTask(String userInput, boolean isBulkTask, Boolean markOrUnmarked) {
        try{
            String[] taskArray = userInput.split(" ", 2);
            String taskName = taskArray[1];

            if(taskName == "") 
                throw new DukeException();

            if(!isBulkTask) {
                System.out.println(borderLine); 
            }

            for(int i=0; i<taskList.size(); i++) {
                if(taskList.get(i).taskName.equalsIgnoreCase(taskName)) {
                    Task toDoTask = new Todo(taskName, true);
                    boolean status = taskList.get(i).getStatus();
                    if(markOrUnmarked != null) {
                        status = markOrUnmarked;
                    } 
                    toDoTask.setStatus(status);
                    taskList.set(i, toDoTask);
                    if(!isBulkTask) {
                        printTaskUpdates(toDoTask.toString(), TaskUpdateType.UPDATE);  
                    }  
                }
            }
            if(!isBulkTask) {
                System.out.println(borderLine);
            }
        } catch(ArrayIndexOutOfBoundsException _exception) {
            System.out.println("☹ OOPS!!! The description of a todo cannot be empty.");
        } catch(DukeException exception) {
            System.out.println("☹ OOPS!!! The description of a todo cannot be empty.");
        } 
    }

    private static void setDeadlineTask(String userInput, boolean isBulkTask, Boolean markOrUnmarked) {
        try{
            String[] taskArray = userInput.split(" ", 2);
            String taskInputDetails = taskArray[1];

            if(taskInputDetails == "") 
                throw new DukeException();

            if(!isBulkTask) {
                System.out.println(borderLine); 
            }

            String[] taskDetails = taskInputDetails.split("/", 2);
            String taskName = taskDetails[0].trim();
            String day = taskDetails[1].split(" ",2)[1].trim();


            for(int i=0; i<taskList.size(); i++) {
                if(taskList.get(i).taskName.equalsIgnoreCase(taskName)) {
                    Task deadlineTask = new Deadline(taskName, day);
                    boolean status = taskList.get(i).getStatus();
                    if(markOrUnmarked != null) {
                        status = markOrUnmarked;
                    }
                    deadlineTask.setStatus(status); 
                    taskList.set(i, deadlineTask);
                    if(!isBulkTask) {
                        printTaskUpdates(deadlineTask.toString(), TaskUpdateType.UPDATE);
                    }  
                } 
            }

            if(!isBulkTask) {
                System.out.println(borderLine);
            }

        System.out.println(borderLine);
        } catch(ArrayIndexOutOfBoundsException _exception) {
            System.out.println("☹ OOPS!!! The description of a deadline cannot be empty.");
        } catch(DukeException exception) {
            System.out.println("☹ OOPS!!! The description of a deadline cannot be empty.");
        } 
    }

    private static void setEventTask(String userInput, boolean isBulkTask, Boolean markOrUnmarked) {

        try{
            String[] taskArray = userInput.split(" ", 2);
            String taskInputDetails = taskArray[1];

            if(taskInputDetails == "") 
                throw new DukeException();

            String borderLine = "\t____________________________________________________________";
            if(!isBulkTask) {
                System.out.println(borderLine); 
            }

            String[] taskDetails = taskInputDetails.split("/", 3);
            String taskName = taskDetails[0].trim();
            String from = taskDetails[1].replace("from", "").trim();
            String to = taskDetails[2].replace("to", "").trim();

            for(int i=0; i<taskList.size(); i++) {
                if(taskList.get(i).taskName.equalsIgnoreCase(taskName)) {
                    Task eventTask = new Event(taskName, from, to);
                    boolean status = taskList.get(i).getStatus();
                    if(markOrUnmarked != null) {
                        status = markOrUnmarked;
                    }
                    eventTask.setStatus(status); 
                    taskList.set(i, eventTask);
                    if(!isBulkTask) {
                        printTaskUpdates(eventTask.toString(), TaskUpdateType.UPDATE);
                    }
                }
            }

        if(!isBulkTask) {
            System.out.println(borderLine);
        }

        } catch(ArrayIndexOutOfBoundsException _exception) {
            System.out.println("☹ OOPS!!! The description of an event cannot be empty.");
        } catch(DukeException exception) {
            System.out.println("☹ OOPS!!! The description of an event cannot be empty.");
        } 
    }

    private static void removeTask(String userInput) {
        try {
            String[] taskArray = userInput.split(" ", 2);
            String taskNumber = taskArray[1];

            if(taskNumber == "") 
                throw new DukeException();

            int taskNo = Integer.parseInt(userInput.split(" ", 2)[1]); 

            for(int i=0; i<taskList.size(); i++) {
                if(i+1 == taskNo) {
                    taskList.remove(i);
                    printTaskUpdates(taskList.get(i).toString(), TaskUpdateType.DELETE);
                }
            }

        } catch(ArrayIndexOutOfBoundsException _exception) {
            System.out.println("☹ OOPS!!! The task number cannot be empty.");
        } catch(DukeException exception) {
            System.out.println("☹ OOPS!!! The task number cannot be empty.");
        } 
    }

    private static void printTaskUpdates(String taskDetails, TaskUpdateType updateType) {
        String message = "";
        if(updateType == TaskUpdateType.UPDATE) {
            message = "Got it. I've added this task:";
        } else if(updateType == TaskUpdateType.DELETE) {
            message = "Noted. I've removed this task:";
        }

        System.out.println("\t" + message + "\n\t " + taskDetails + "\n\tNow you have " + taskList.size() + " in the list.");
    }

    private static void bulkAddTasksFromFile(String userInput) {
        try {
            Scanner scan = new Scanner(new FileReader("/Users/mtadiboina/Desktop/School/TIC2002/Project_Duke/tic2002-duke-mamtha/data/duke.txt")).useDelimiter(",\\n");
            String fileContent = scan.next();
            String[] tasks = fileContent.split("\\n");
            bulkAddTasks(tasks);
          } catch(ArrayIndexOutOfBoundsException _exception) {
            System.out.println("The filename cannot be empty.");
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading file. No file found in /Users/mtadiboina/Desktop/School/TIC2002/Project_Duke/tic2002-duke-mamtha/data/");
            e.printStackTrace();
          }
    }

    private static void bulkAddTasks(String[] tasks) {
        EnumMap<TaskOperationTypePrefix, TaskOperationTypeAction> operationMapping = new EnumMap<>(TaskOperationTypePrefix.class);
        operationMapping.put(TaskOperationTypePrefix.D, TaskOperationTypeAction.deadline);
        operationMapping.put(TaskOperationTypePrefix.E, TaskOperationTypeAction.event);
        operationMapping.put(TaskOperationTypePrefix.T, TaskOperationTypeAction.todo);

        for (String task : tasks) {
            String[] taskDetails = task.split("\\|");
            String taskOperation = taskDetails[0].trim();
            Boolean status = Integer.parseInt(taskDetails[1].trim()) == 0 ? false : true;
            String taskName = taskDetails[2].trim();

            TaskUpdateType taskUpdateType = getTaskUpdateType(taskName);
            
            if(taskUpdateType == TaskUpdateType.INSERT) {
                Task newTask = new Task(taskName);
                taskList.add(newTask);
            }

            if(taskOperation.equals(TaskOperationTypePrefix.D.toString())) {
                String taskNameWithDeadline = taskName + "/by"+ taskDetails[3];
                setDeadlineTask(operationMapping.get(TaskOperationTypePrefix.D).toString() + " " + taskNameWithDeadline, true, status);
            }

            if(taskOperation.equals(TaskOperationTypePrefix.T.toString())) {
                setToDoTask(operationMapping.get(TaskOperationTypePrefix.T).toString() + " " + taskName, true, status);
            }

            if(taskOperation.equals(TaskOperationTypePrefix.E.toString())) {
                String[] eventTask = taskDetails[3].split("-"); 
                String fromTime = eventTask[0]+"pm";
                String toTime = eventTask[1];
                String taskNameWithEvent = taskName + "/from" + fromTime + " /to " + toTime;
                setEventTask(operationMapping.get(TaskOperationTypePrefix.E).toString() + " " + taskNameWithEvent, true, status);
            }
        }
    }  

    public static TaskUpdateType getTaskUpdateType(String taskName) {
        boolean isTaskExistCheck = isTaskExists(taskName);
        if(isTaskExistCheck) {
            return TaskUpdateType.UPDATE;
        } else {
            return TaskUpdateType.INSERT;
        }
    }
 
    public static boolean hasSameStatus(String taskName, boolean status) {
        for(int i=0; i<taskList.size(); i++) {
            if(taskList.get(i).getTaskName().equalsIgnoreCase(taskName)) {
                if(taskList.get(i).getStatus() == status) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkCanAddOrUpdateTask(boolean taskExists, boolean sameStatus) {
        if(taskExists) {
            if(!sameStatus) {
                return true;
            } else {
                return false;
            }
        }

        if(!taskExists) {
            return true;
        }

        return false;
    }
    
    public static boolean hasSameStatusTest(String taskName, boolean status) {
        for(int i=0; i<taskList.size(); i++) {
            if(taskList.get(i).getTaskName().equalsIgnoreCase(taskName)) {
                if(taskList.get(i).getStatus() == status) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkCanAddTask(boolean taskExists, boolean sameStatus) {
        if(!taskExists || (taskExists && !sameStatus)) {
            return true;
        }

        return false;
    }

    
}