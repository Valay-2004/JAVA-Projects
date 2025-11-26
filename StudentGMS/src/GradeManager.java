import java.util.*;
import java.io.*;
import exceptions.*;

public class GradeManager {
    private Map<String, Student> students;  // Stores students by ID
    private String dataFile = "data/students.txt";

    public GradeManager() {
        students = new HashMap<>();
        // Initialize with some sample data or load from file
    }

    // Method to add a new student
    public void addStudent(String name, String id) {
        // Create and store student
    }

    // Method to add grade to existing student
    public void addGradeToStudent(String studentId, double grade)
            throws StudentNotFoundException, InvalidGradeException {
        // Find student and add grade
    }

    // Method to get student by ID
    public Student getStudent(String id) throws StudentNotFoundException {
        // Return student or throw exception if not found
    }

    // File operations
    public void saveToFile() throws IOException {
        // Write all students to file
    }

    public void loadFromFile() throws IOException {
        // Read students from file
    }

    // Utility method to list all students
    public void displayAllStudents() {
        // Print all students and their averages
    }
}