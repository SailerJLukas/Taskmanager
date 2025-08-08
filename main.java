package pl.coderslab;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class main {

    static final String FILE_NAME = "tasks.csv";
    static List<String[]> tasks = new ArrayList<>();

    public static void main(String[] args) {
        loadTasks();

        Scanner scanner = new Scanner(System.in);
        String option;

        do {
            printOptions();
            option = scanner.nextLine();

            switch (option.toLowerCase()) {
                case "add":
                    addTask(scanner);
                    break;
                case "remove":
                    removeTask(scanner);
                    break;
                case "list":
                    listTasks();
                    break;
                case "exit":
                    saveTasks();
                    System.out.println("Bye!");
                    break;
                default:
                    System.out.println("Unknown option.");
            }
//New stuff for testing
        } while (!option.equalsIgnoreCase("exit"));
    }

    static void printOptions() {
        System.out.println("Choose an option:");
        System.out.println("add");
        System.out.println("remove");
        System.out.println("list");
        System.out.println("exit");
    }

    static void loadTasks() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));
            for (String line : lines) {
                tasks.add(line.split(","));
            }
        } catch (IOException e) {
            System.out.println("Could not read file: " + FILE_NAME);
        }
    }

    static void listTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(i + ": " + String.join(" | ", tasks.get(i)));
        }
    }

    static void addTask(Scanner scanner) {
        System.out.println("Enter task description:");
        String description = scanner.nextLine();
        System.out.println("Enter due date:");
        String dueDate = scanner.nextLine();
        System.out.println("Is the task important? true/false:");
        String important = scanner.nextLine();
        tasks.add(new String[]{description, dueDate, important});
    }

    static void removeTask(Scanner scanner) {
        System.out.println("Enter the number of the task to remove:");
        try {
            int index = Integer.parseInt(scanner.nextLine());
            if (index >= 0 && index < tasks.size()) {
                tasks.remove(index);
                System.out.println("Task removed.");
            } else {
                System.out.println("Invalid task number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    static void saveTasks() {
        List<String> lines = new ArrayList<>();
        for (String[] task : tasks) {
            lines.add(String.join(",", task));
        }
        try {
            Files.write(Paths.get(FILE_NAME), lines);
        } catch (IOException e) {
            System.out.println("Could not save file: " + FILE_NAME);
        }
    }
}
