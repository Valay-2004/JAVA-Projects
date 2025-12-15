import exceptions.InvalidGradeException;

import java.util.ArrayList;
import java.util.List;

public class Student{

    // Initializing what we need for the given scope
    private String name;
    private String id;
    private List<Double> grades;

    // create a constructor
    public Student(String name, String id){
        this.name = name;
        this.id = id;
        this.grades = new ArrayList<>();
    }

    // method for adding the grades to the list
    public void addGrade(double grade) throws InvalidGradeException{
        if(grade < 0 || grade > 100){
            throw new InvalidGradeException("Grade must be between 0 and 100");
        }
        grades.add(grade);
    }

    // method for getting the average grades
    public double getAverageGrade(){
        if(grades.isEmpty()) return 0.0;
        double sum = 0;
        for(double grade : grades){
            sum += grade;
        }
        return sum / grades.size();
    }

    // Getters and toString method
    public String getName() { return name;}
    public String getId() { return id; }
    public List<Double> getGrades() { return new ArrayList<>(grades); }

    @Override
    public String toString(){
        return String.format("Student: %s (ID - %s) - Average: %.2f", name, id, getAverageGrade());
    }
}