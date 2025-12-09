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
        Student s = new Student(name, id);
        students.put(id, s);
    }

    public void addGradeToStudent(String studentId, double grade)
            throws StudentNotFoundException, InvalidGradeException {
        // 1. Find student by ID
        Student s = students.get(studentId);
        // 2. If student doesn't exist, throw StudentNotFoundException
        if(s == null) throw new StudentNotFoundException("Student with ID " + studentId + " not found");
        // 3. If student exists, add grade to their record
        if(grade < 0 || grade > 100) throw new InvalidGradeException("Grade range should be between 0 to 100");
        s.addGrade(grade);
    }

    public Student getStudent(String id) throws StudentNotFoundException {
        // 1. Look up student in the map
        Student s = students.get(id);
        // 2. Throw exception if not found
        if(s==null) throw new StudentNotFoundException("Student not found!");
        // 3. Return student if found
        return s;
    }

    public void displayAllStudents() {
        // Loop through all students and print their info
        // adding this just to commit
        for(Student s : students.values()){
            System.out.println("Name: " + s.getName() + ", ID: " + s.getId() + ", Grades: " + s.getGrades() + ", Average grade: " + s.getAverageGrades());

        }
    }
}