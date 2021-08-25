import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

public class Duke {

    //To do list storage DS
    private static ArrayList<Task> list = new ArrayList<>();

    //Constants
    // Some String format
    private static String start = "____________________________________________________________\n";
    private static String end = "____________________________________________________________";
    private static String logo = " ____        _        \n"
                                + "|  _ \\ _   _| | _____ \n"
                                + "| | | | | | | |/ / _ \\\n"
                                + "| |_| | |_| |   <  __/\n"
                                + "|____/ \\__,_|_|\\_\\___|\n";


    public static void main(String[] args) {
        //Print welcome message to the user
        Duke.welcomeMessage();

        try {
            loadFileContents("data/duke.txt");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        Scanner s = new Scanner(System.in);
        Duke.getPrompt();
        String input = s.nextLine();

        while (!input.equals("bye")) {
            try {
                if (input.contains("list")) {
                    Duke.getList();
                } else if (input.contains("done")) {
                    int i = Integer.valueOf(input.substring(5));
                    Duke.markDone(i);
                } else if (input.contains("delete")) {
                    int i = Integer.valueOf(input.substring(7));
                    System.out.println(i);
                    Duke.delete(i);
                } else if (input.contains("todo")) {
                    Duke.toDo(input);
                } else if (input.contains("deadline")) {
                    Duke.deadline(input);
                } else if (input.contains("event")) {
                    Duke.event(input);
                } else {
                    throw new DukeException(Duke.start +
                            "☹ OOPS!!! I'm sorry, but I don't know what that means :-( \n"
                            + Duke.end);
                }
            } catch (DukeException error) {
                System.out.println(error.getMessage());
            }

            //Get next command for the loop
            System.out.println("What else can I do for you?");
            input = s.nextLine();
        }
        Duke.byeMessage();
        s.close();
    }

    public static void welcomeMessage() {
        String welcome = "Hello! I'm Duke. A friendly chatbot!! :)\n" +
                            "What can I do for you?\n";
        System.out.println(Duke.start + Duke.logo + "\n" +  welcome + Duke.end);
    }

    public static void byeMessage() {
        String end_message = "Bye. I hope to talk to you again soon! :)";
        System.out.println(Duke.start + end_message + Duke.end);
    }

    public static void getPrompt() {
        String prompt_message = "Add to-do list ({input})/ View list (list) / Complete task (done {input}) / End (bye) :";
        System.out.println(prompt_message);
    }

    public static void addTask(Task t) {
        Duke.list.add(t);
        System.out.println(Duke.start + "added: " + t.toString() + "\n" + Duke.end);
    }

    public static void getList() {
        System.out.println(Duke.end);
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < Duke.list.size(); i++) {
                    String res = (i + 1) + ". " + Duke.list.get(i).toString();
                    System.out.println(res);
                }
                System.out.println(Duke.end);

    }

    public static void markDone(int i) {
        System.out.println(Duke.start + "Nice! I've marked this task as done: ");
        Duke.list.get((int) i - 1).markAsDone();
        String res = Duke.list.get(i-1).toString();
        System.out.println(res + "\n" +  Duke.end);
    }

    public static void toDo(String input) throws DukeException {
        if (input.equals("todo")) {
            throw new DukeException(Duke.start
                                    + "☹ OOPS!!! The description of a todo cannot be empty.\n"
                                    + Duke.end);
        }
        String t = input.split("todo")[1];
        ToDo td = new ToDo(t);
        Duke.list.add(td);
        System.out.println(Duke.start + "Got it. I've added this task: \n " + td.toString() + "\n"
                            + "Now you have " + Duke.list.size()  + " tasks in the list."  + "\n" +Duke.end);
    }

    public static void deadline(String input) {
        String t = input.split("deadline")[1];
        Deadline dl = new Deadline(t);
        Duke.list.add(dl);
        System.out.println(Duke.start + "Got it. I've added this task: \n " + dl.toString() + "\n"
                + "Now you have " + Duke.list.size()  + " tasks in the list."  + "\n" +Duke.end);
    }

    public static void event(String input) {
        String t = input.split("event")[1];
        Event e = new Event(t);
        Duke.list.add(e);
        System.out.println(Duke.start + "Got it. I've added this task: \n " + e.toString() + "\n"
                + "Now you have " + Duke.list.size()  + " tasks in the list."  + "\n" +Duke.end);
    }

    public static void delete(int i) {
        System.out.println(Duke.start + "Noted. I've removed this task: ");
        String deleted = Duke.list.get(i-1).toString();
        Duke.list.remove(((int) i - 1));
        System.out.println(" " + deleted);
        System.out.println("Now you have " + Duke.list.size() + " tasks in the list." + "\n" +  Duke.end);
    }

    private static void loadFileContents(String filePath) throws FileNotFoundException {
        File f = new File(filePath);
        Scanner s = new Scanner(f);
        while (s.hasNext()) {
            // Read and add the task into the list
            String input = s.nextLine();
            String[] arrOfInputs = input.split("\\|");
            readInputs(arrOfInputs);
        }
    }

    private static void readInputs(String[] arrOfInputs) {
        //Check for T, D, E
        if (arrOfInputs[0].equals("T")) {
            String t = arrOfInputs[2];
            ToDo td = new ToDo(t);
            Duke.list.add(td);
        } else if (arrOfInputs[0].equals("D")) {
            String t = arrOfInputs[2] + " /by " + arrOfInputs[3];
            Deadline d = new Deadline(t);
            Duke.list.add(d);
        } else if (arrOfInputs[0].equals("E")) {
            String t = arrOfInputs[2] + " /at " + arrOfInputs[3];
            Event e = new Event(t);
            Duke.list.add(e);
        }

        int currListLength = Duke.list.size();
        //Check if its completed or not (0,1) and mark accordingly
        if (arrOfInputs[1].equals("1")) {
            Duke.list.get(currListLength - 1).markAsDone();
        }
    }
}

