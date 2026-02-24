import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUIStudentGradeTracker extends JFrame {
    private ArrayList<Student> students = new ArrayList<>();
    private JTextArea displayArea;
    private JTextField nameField, gradeField;

    public GUIStudentGradeTracker() {
        // BIGGER WINDOW - NO CUT OFF!
        setTitle("Student Grade Tracker - CodeAlpha Task 1");
        setSize(800, 700);  // Increased size
        setMinimumSize(new Dimension(700, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with better spacing
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel title = new JLabel("🎓 Student Grade Tracker", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(30, 144, 255));
        mainPanel.add(title, BorderLayout.NORTH);

        // Input panel - SPACED OUT
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("📝 Add Student / Grade"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Student Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(15);
        inputPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Grade:"), gbc);
        gbc.gridx = 1;
        gradeField = new JTextField(15);
        inputPanel.add(gradeField, gbc);

        // Buttons - FULL WIDTH
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton addStudentBtn = new JButton("➕ Add Student");
        JButton addGradeBtn = new JButton("📊 Add Grade");
        JButton showSummaryBtn = new JButton("📈 Show Summary");

        addStudentBtn.setPreferredSize(new Dimension(140, 40));
        addGradeBtn.setPreferredSize(new Dimension(140, 40));
        showSummaryBtn.setPreferredSize(new Dimension(140, 40));

        buttonPanel.add(addStudentBtn);
        buttonPanel.add(addGradeBtn);
        buttonPanel.add(showSummaryBtn);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        inputPanel.add(buttonPanel, gbc);

        // Display area - BIGGER
        displayArea = new JTextArea(18, 60);
        displayArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        displayArea.setEditable(false);
        displayArea.setBorder(BorderFactory.createLoweredBevelBorder());
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("📋 Summary Report"));

        // Layout
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel);

        // Button actions
        addStudentBtn.addActionListener(e -> addStudent());
        addGradeBtn.addActionListener(e -> addGrade());
        showSummaryBtn.addActionListener(e -> showSummary());
    }

    private void addStudent() {
        String name = nameField.getText().trim();
        if (!name.isEmpty()) {
            students.add(new Student(name));
            displayArea.append("✅ Student '" + name + "' added successfully!\n");
            nameField.setText("");
            gradeField.setText("");
            displayArea.setCaretPosition(displayArea.getDocument().getLength());
        } else {
            JOptionPane.showMessageDialog(this, "Please enter student name!");
        }
    }

    private void addGrade() {
        String name = nameField.getText().trim();
        String gradeStr = gradeField.getText().trim();

        if (name.isEmpty() || gradeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both name and grade!");
            return;
        }

        try {
            double grade = Double.parseDouble(gradeStr);
            Student student = findStudent(name);
            if (student != null) {
                student.addGrade(grade);
                displayArea.append("✅ Grade " + grade + " added to '" + name + "'\n");
            } else {
                displayArea.append("❌ Student '" + name + "' not found! Add student first.\n");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid grade! Enter numbers only.");
        }
        nameField.setText("");
        gradeField.setText("");
        displayArea.setCaretPosition(displayArea.getDocument().getLength());
    }

    private Student findStudent(String name) {
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) return s;
        }
        return null;
    }

    private void showSummary() {
        displayArea.setText("");
        if (students.isEmpty()) {
            displayArea.append("📭 No students added yet! Add some students first.\n");
            return;
        }

        double overallAvg = 0, highest = Double.MIN_VALUE, lowest = Double.MAX_VALUE;
        int totalGrades = 0;

        displayArea.append("📊 === SUMMARY REPORT ===\n\n");
        for (Student s : students) {
            displayArea.append("👤 " + s.getName() + ": " + s.getGrades() + "\n");
            overallAvg += s.getAverage() * s.getGrades().size();
            totalGrades += s.getGrades().size();

            for (double g : s.getGrades()) {
                if (g > highest) highest = g;
                if (g < lowest) lowest = g;
            }
        }

        overallAvg = totalGrades > 0 ? overallAvg / totalGrades : 0;
        displayArea.append("\n📈 STATISTICS:\n");
        displayArea.append("• Overall Average: " + String.format("%.2f", overallAvg) + "\n");
        displayArea.append("• Highest Score:   " + String.format("%.2f", highest) + "\n");
        displayArea.append("• Lowest Score:    " + String.format("%.2f", lowest) + "\n");
        displayArea.append("\n✨ Powered by CodeAlpha Internship Task 1\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GUIStudentGradeTracker().setVisible(true);  // ← CLEAN & SIMPLE
        });
    }
}
