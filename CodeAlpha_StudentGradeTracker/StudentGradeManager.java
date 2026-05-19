import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


class Student {
    private String name;
    private ArrayList<Integer> grades;

    public Student(String name) {
        this.name = name;
        this.grades = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Integer> getGrades() {
        return grades;
    }

    public void addGrade(int score) {
        grades.add(score);
    }

    public double getAverage() {
        if (grades.isEmpty()) return 0;
        int sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        return (double) sum / grades.size();
    }

    public int getHighest() {
        if (grades.isEmpty()) return 0;
        return Collections.max(grades);
    }

    public int getLowest() {
        if (grades.isEmpty()) return 0;
        return Collections.min(grades);
    }

    public void printReport() {
        System.out.printf("%-15s | Avg: %-6.2f | High: %d | Low: %d%n", 
            name, getAverage(), getHighest(), getLowest());
        System.out.println("Grades: " + grades);
    }
}


public class StudentGradeManager {
    private ArrayList<Student> studentList;
    private Scanner scanner;

    public StudentGradeManager() {
        studentList = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        StudentGradeManager system = new StudentGradeManager();
        system.runMenu();
    }


    private void addStudent() {
        System.out.print("\nEnter Student Name: ");
        String name = scanner.nextLine();
        
        Student newStudent = new Student(name);
        
        System.out.println("How many subjects/assignments for " + name + "?");
        int count = getIntegerInput();

        for (int i = 0; i < count; i++) {
            System.out.print("Enter score #" + (i + 1) + ": ");
            int score = getIntegerInput();
            while (score < 0 || score > 100) {
                System.out.print("Invalid! Enter score between 0-100: ");
                score = getIntegerInput();
            }
            newStudent.addGrade(score);
        }

        studentList.add(newStudent);
        System.out.println("Student added successfully!");
    }

    private void displaySummaryReport() {
        if (studentList.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.println("\n========== CLASS SUMMARY REPORT ==========");
        System.out.println("------------------------------------------------");
        System.out.printf("%-15s | %-10s | %-6s | %-5s%n", "NAME", "AVERAGE", "HIGH", "LOW");
        System.out.println("------------------------------------------------");

        int totalClassScores = 0;
        double totalClassSum = 0;

        for (Student s : studentList) {
            s.printReport();
            totalClassScores += s.getGrades().size();
            totalClassSum += s.getAverage();
        }

        System.out.println("\n========== CLASS STATISTICS ==========");
        System.out.println("Total Students: " + studentList.size());
        
        if (totalClassScores > 0) {
            double classAverage = totalClassSum / studentList.size();
            System.out.printf("Class Average: %.2f%n", classAverage);
            
            int globalHighest = 0;
            int globalLowest = 100;
            String topStudent = "";
            String lowStudent = "";

            for(Student s : studentList) {
                if(s.getHighest() > globalHighest) {
                    globalHighest = s.getHighest();
                    topStudent = s.getName();
                }
                if(s.getLowest() < globalLowest) {
                    globalLowest = s.getLowest();
                    lowStudent = s.getName();
                }
            }
            System.out.println("Highest Score in Class: " + globalHighest + " (by " + topStudent + ")");
            System.out.println("Lowest Score in Class: " + globalLowest + " (by " + lowStudent + ")");
        }
    }

    private void runMenu() {
        int choice;
        do {
            System.out.println("\n--- GRADE MANAGEMENT MENU ---");
            System.out.println("1. Add New Student & Grades");
            System.out.println("2. Display Summary Report");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            choice = getIntegerInput();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    displaySummaryReport();
                    break;
                case 3:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 3);
    }

    private int getIntegerInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("That's not a number. Please enter a valid integer.");
            scanner.next();
        }
        int number = scanner.nextInt();
        scanner.nextLine();
        return number;
    }
}