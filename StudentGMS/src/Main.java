import exceptions.*;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final GradeManager manager = new GradeManager();
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        //  This is my first project after long time
        // What we are going to make
        // A console-based application that manages student grades,
        // allowing teachers to add, view, and calculate averages
        // while handling various error scenarios.

        // Load existing data on startup
        try{
            manager.loadFromFile();
            System.out.println("Data loaded successfully!");
        } catch (Exception e){
            System.out.println("Could not load existing data: " + e.getMessage());
        }

        // show welcome message and menu
        showMenu();
    }

    private static void showMenu(){
        boolean running = true;
        while(running){
            System.out.println("\n--- Student Grade Management ---");
            System.out.println("1. Add Student");
            System.out.println("2. Add Grade to Student");
            System.out.println("3. View All Students");
            System.out.println("4. View Single Student");
            System.out.println("5. Save & Exit");
            System.out.print("Choose and Option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1 -> addStudent();
                case 2 -> addGradeToStudent();
                case 3 -> manager.displayAllStudents();
                case 4 -> viewSingleStudent();
                case 5 -> {
                    try{
                        manager.saveToFile();
                        System.out.println("Data saved successfully!");
                    } catch (IOException e) {
                        System.out.println("Could not save data: " + e.getMessage());
                    }
                    running = false;
                }
                default -> System.out.println("Invalid Option!");
            }
        }
    }

    private static void addStudent(){
        System.out.println("Enter student name: ");
        String name = scanner.nextLine();
        System.out.println("Enter student ID: ");
        String id = scanner.nextLine();

        manager.addStudent(name, id);
        System.out.println("Student added successfully!");

        try {
            manager.saveToFile();
        } catch (IOException e) {
            System.out.println("Could not save data: " + e.getMessage());
        }
    }

    private static void addGradeToStudent(){
        System.out.println("Enter student ID: ");
        String id = scanner.nextLine();
        System.out.println("Enter grade: ");
        double grade = Double.parseDouble(scanner.nextLine());
        scanner.nextLine();

        try {
            manager.addGradeToStudent(id, grade);
            System.out.println("Grade added successfully!");
            // save after modification
            try {
                manager.saveToFile();
            } catch (IOException e) {
                System.out.println("Could not save data: " + e.getMessage());
            }
        } catch (StudentNotFoundException | InvalidGradeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewSingleStudent(){
        System.out.println("Enter student ID: ");
        String id = scanner.nextLine();

        try {
            System.out.println(manager.getStudent(id));
        } catch (StudentNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}