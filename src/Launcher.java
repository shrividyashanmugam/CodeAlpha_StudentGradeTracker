public class Launcher {
    public static void main(String[] args) {
        System.out.println("=== CodeAlpha Student Grade Tracker ===");
        System.out.println("1. Launch CONSOLE Version");
        System.out.println("2. Launch GUI Version");
        System.out.print("Choose (1 or 2): ");

        java.util.Scanner sc = new java.util.Scanner(System.in);
        int choice = sc.nextInt();

        if(choice == 2) {
            javax.swing.SwingUtilities.invokeLater(() -> new GUIStudentGradeTracker().setVisible(true));
        } else {
            ConsoleStudentGradeTracker.main(args);  // Your console version
        }
    }
}
