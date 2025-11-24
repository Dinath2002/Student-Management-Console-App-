import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1 -> addStudentFlow(manager);
                case 2 -> manager.displayAllStudents();
                case 3 -> manager.displayStudents(manager.getStudentsSortedByName(), "📑 Students Sorted by Name");
                case 4 -> manager.displayStudents(manager.getStudentsSortedByAge(), "⏳ Students Sorted by Age");
                case 5 -> searchStudentByIdFlow(manager);
                case 6 -> searchStudentsByNameFlow(manager);
                case 7 -> updateStudentFlow(manager);
                case 8 -> removeStudentFlow(manager);
                case 9 -> showStats(manager);
                case 10 -> {
                    System.out.println("👋 Exiting program...");
                    running = false;
                }
                default -> System.out.println("⚠️ Invalid choice! Please try again.\n");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("===== Student Management System =====");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. View Sorted by Name");
        System.out.println("4. View Sorted by Age");
        System.out.println("5. Search Student by ID");
        System.out.println("6. Search Students by Name");
        System.out.println("7. Update Student");
        System.out.println("8. Remove Student");
        System.out.println("9. Show Stats");
        System.out.println("10. Exit");
    }

    private static void addStudentFlow(StudentManager manager) {
        String id = readNonEmpty("Enter Student ID: ");
        String name = readNonEmpty("Enter Student Name: ");
        int age = readIntInRange("Enter Student Age: ", 1, 120);

        Student newStudent = new Student(name, age, id);
        if (manager.addStudent(newStudent)) {
            System.out.println("✅ Student added successfully!\n");
        } else {
            System.out.println("⚠️ A student with that ID already exists. Try another ID.\n");
        }
    }

    private static void searchStudentByIdFlow(StudentManager manager) {
        String id = readNonEmpty("Enter Student ID to search: ");
        Student student = manager.findStudent(id);
        if (student == null) {
            System.out.println("⚠️ No student found with ID: " + id + "\n");
            return;
        }

        System.out.println("\nFound student:");
        student.displayInfo();
        System.out.println();
    }

    private static void searchStudentsByNameFlow(StudentManager manager) {
        String namePart = readNonEmpty("Enter part of the name to search: ");
        var matches = manager.findByName(namePart);
        if (matches.isEmpty()) {
            System.out.println("⚠️ No matches found for: " + namePart + "\n");
            return;
        }
        manager.displayStudents(matches, "🔎 Matches for \"" + namePart + "\"");
    }

    private static void updateStudentFlow(StudentManager manager) {
        String id = readNonEmpty("Enter Student ID to update: ");
        Student student = manager.findStudent(id);
        if (student == null) {
            System.out.println("⚠️ No student found with ID: " + id + "\n");
            return;
        }

        System.out.println("Current record -> " + student);
        System.out.println("Press Enter to keep the current value.");

        System.out.print("New name (current \"" + student.getName() + "\"): ");
        String newNameInput = scanner.nextLine().trim();
        String newName = newNameInput.isEmpty() ? null : newNameInput;

        Integer newAge = readOptionalAge("New age (current " + student.getAge() + ")");

        manager.updateStudent(id, newName, newAge);
        System.out.println("✅ Student updated.\n");
    }

    private static void removeStudentFlow(StudentManager manager) {
        String id = readNonEmpty("Enter Student ID to remove: ");
        Student student = manager.findStudent(id);
        if (student == null) {
            System.out.println("⚠️ No student found with ID: " + id + "\n");
            return;
        }

        System.out.println("Removing: " + student);
        if (confirmAction("Are you sure you want to remove this student? (y/n): ")) {
            if (manager.removeStudent(id)) {
                System.out.println("🗑️ Student removed successfully!\n");
            } else {
                System.out.println("⚠️ Failed to remove student. Please try again.\n");
            }
        } else {
            System.out.println("Removal canceled.\n");
        }
    }

    private static void showStats(StudentManager manager) {
        var stats = manager.calculateStats();
        if (stats.count() == 0) {
            System.out.println("No students to summarize.\n");
            return;
        }
        System.out.printf("%n📈 Stats:%n - Count: %d%n - Youngest age: %d%n - Oldest age: %d%n - Average age: %.1f%n%n",
                stats.count(), stats.minAge(), stats.maxAge(), stats.averageAge());
    }

    private static boolean confirmAction(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) {
                return true;
            }
            if (input.equals("n") || input.equals("no")) {
                return false;
            }
            System.out.println("Please enter 'y' or 'n'.");
        }
    }

    private static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Input cannot be empty. Try again.");
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static int readIntInRange(String prompt, int min, int max) {
        while (true) {
            int value = readInt(prompt);
            if (value < min || value > max) {
                System.out.println("Please enter a number between " + min + " and " + max + ".");
                continue;
            }
            return value;
        }
    }

    private static Integer readOptionalAge(String prompt) {
        while (true) {
            System.out.print(prompt + " (leave blank to keep): ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                return null;
            }
            try {
                int age = Integer.parseInt(input);
                if (age < 1 || age > 120) {
                    System.out.println("Age must be between 1 and 120.");
                    continue;
                }
                return age;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number or leave blank to keep the current age.");
            }
        }
    }
}
