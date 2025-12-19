import java.io.*;
import java.util.*;
import exceptions.*;

public class GradeManager {
    private final String dataFile = "data/students.txt";
    private final Map<String, Student> students;

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
            System.out.println("Name: " + s.getName() + ", ID: " + s.getId() + ", Grades: " + s.getGrades() + ", Average grade: " + s.getAverageGrade());

        }
    }

    public void saveToFile() throws IOException{
        File directory = new File("data");

        // Check if the directory already exists
        if(!directory.exists()){
            directory.mkdirs();
        }
        try (PrintWriter writer = new PrintWriter((new FileWriter(dataFile, false)))) {
            // Write the student's data as a single line
            for (Student student : students.values()) {
                writer.println(student.toCsvString());
            }
            System.out.println("Student was successfully added to the record file");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public void loadFromFile() throws IOException {
        File file = new File(dataFile);
        if(!file.exists()){
            System.out.println("No saved data found");
            return; // Exit early if no file exists
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))){
            String line;
            while((line = reader.readLine()) != null){
                try{
                    String[] parts = line.split(",", -1); // -1 keeps empty trailing fields

                    // Extract student data
                    String id = parts[0];
                    String name = parts[1];

                    // Create student object
                    Student student = new Student(name, id);

                    // Add grades (starting from index 2)
                    for(int i = 2; i < parts.length; i++){
                        if(!parts[i].trim().isEmpty()){
                            double grade = Double.parseDouble(parts[i].trim());
                            student.addGrade(grade);
                        }
                    }
                    // Add student to our collection
                    students.put(id, student);
                } catch (NumberFormatException e){
                    System.out.println("Skipping invalid grade in line: " + line);
                } catch (InvalidGradeException e){
                    System.out.println("Skipping invalid grade value in line: " + line + " - " + e.getMessage());
                }
            }
        }
    }
}