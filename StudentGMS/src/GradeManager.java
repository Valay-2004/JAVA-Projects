import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import exceptions.*;

public class GradeManager {
    private String dataFile = "data/students.txt";
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

    public void loadFromFile() throws IOException{
        Path path = Paths.get(dataFile);
        if(!Files.exists(path)){
            System.out.println("File does not exists in given path");
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))){
            String line;
            List<List<String>> records = new ArrayList<>();
            // Read lines until readLine() return null (EOS)
            while((line = reader.readLine()) != null){
                String[] values = line.split(",");
                // add the resulting array of values to a list of records
                records.add(Arrays.asList(values));
            }
            // Process or print the records
            for(List<String> record : records){
                System.out.println(record);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}