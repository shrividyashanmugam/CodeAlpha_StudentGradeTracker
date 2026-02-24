import java.util.ArrayList;

public class Student {
    private String name;
    private ArrayList<Double> grades;

    public Student(String name) {
        this.name = name;
        this.grades = new ArrayList<>();
    }

    public void addGrade(double grade) {
        grades.add(grade);
    }

    public double getAverage() {
        double sum = 0;
        for(double g : grades) sum += g;
        return grades.isEmpty() ? 0 : sum / grades.size();
    }

    // getters
    public String getName() { return name; }
    public ArrayList<Double> getGrades() { return grades; }
}
