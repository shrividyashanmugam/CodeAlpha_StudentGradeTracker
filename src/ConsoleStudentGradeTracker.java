import java.util.*;

public class ConsoleStudentGradeTracker {
    private static ArrayList<Student> students = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        showMenu();
    }

    static void showMenu() {
        while(true) {
            System.out.println("\n=== Student Grade Tracker ===");
            System.out.println("1. Add Student");
            System.out.println("2. Add Grade");
            System.out.println("3. Show Summary");
            System.out.println("4. Exit");
            System.out.print("Choose: ");

            int choice = sc.nextInt(); sc.nextLine();

            switch(choice) {
                case 1: addStudent(); break;
                case 2: addGrade(); break;
                case 3: showSummary(); break;
                case 4: return;
            }
        }
    }

    static void addStudent() {
        System.out.print("Enter student name: ");
        String name = sc.nextLine();
        students.add(new Student(name));
        System.out.println("Student added!");
    }

    static void addGrade() {
        System.out.print("Enter student name: ");
        String name = sc.nextLine();
        Student student = findStudent(name);
        if(student == null) {
            System.out.println("Student not found!");
            return;
        }
        System.out.print("Enter grade: ");
        double grade = sc.nextDouble();
        student.addGrade(grade);
        System.out.println("Grade added!");
    }

    static Student findStudent(String name) {
        for(Student s : students) {
            if(s.getName().equalsIgnoreCase(name)) return s;
        }
        return null;
    }

    static void showSummary() {
        if(students.isEmpty()) {
            System.out.println("No students!");
            return;
        }

        double overallAvg = 0, highest = Double.MIN_VALUE, lowest = Double.MAX_VALUE;
        int totalGrades = 0;

        System.out.println("\n=== SUMMARY REPORT ===");
        for(Student s : students) {
            System.out.println(s.getName() + ": " + s.getGrades());
            overallAvg += s.getAverage() * s.getGrades().size();
            totalGrades += s.getGrades().size();

            for(double g : s.getGrades()) {
                if(g > highest) highest = g;
                if(g < lowest) lowest = g;
            }
        }

        overallAvg = totalGrades > 0 ? overallAvg / totalGrades : 0;
        System.out.printf("Overall Average: %.2f\n", overallAvg);
        System.out.printf("Highest Score: %.2f\n", highest);
        System.out.printf("Lowest Score: %.2f\n", lowest);
    }
}
