import java.util.*;
import exceptions.*;

public class GradeManager {
    private Map<String, Student> students;

    public GradeManager() {
        students = new HashMap<>();
        // Add sample student for testing
        addStudent("John Doe", "S001");
    }

    public void addStudent(String name, String id) {
        // Create new student and add to map
        // Use student ID as the key
    }

    public void addGradeToStudent(String studentId, double grade)
            throws StudentNotFoundException, InvalidGradeException {
        // 1. Find student by ID
        // 2. If student exists, add grade to their record
        // 3. If student doesn't exist, throw StudentNotFoundException
    }

    public Student getStudent(String id) throws StudentNotFoundException {
        // 1. Look up student in the map
        // 2. Return student if found
        // 3. Throw exception if not found
    }

    public void displayAllStudents() {
        // Loop through all students and print their info
    }
}