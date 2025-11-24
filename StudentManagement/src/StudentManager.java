import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StudentManager {
    private final Map<String, Student> students = new LinkedHashMap<>();

    public boolean addStudent(Student student) {
        String key = normalizeId(student.getId());
        if (students.containsKey(key)) {
            return false;
        }
        students.put(key, student);
        return true;
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.\n");
            return;
        }
        displayStudents(new ArrayList<>(students.values()), "📋 Student List");
    }

    public void displayStudents(List<Student> list, String title) {
        if (list.isEmpty()) {
            System.out.println("No students found.\n");
            return;
        }
        System.out.println("\n" + title + " (" + list.size() + "):");
        for (Student student : list) {
            System.out.println(" - " + student);
        }
        System.out.println();
    }

    public Student findStudent(String id) {
        return students.get(normalizeId(id));
    }

    public boolean updateStudent(String id, String newName, Integer newAge) {
        Student student = findStudent(id);
        if (student == null) {
            return false;
        }
        if (newName != null && !newName.isBlank()) {
            student.setName(newName.trim());
        }
        if (newAge != null) {
            student.setAge(newAge);
        }
        return true;
    }

    public boolean removeStudent(String id) {
        return students.remove(normalizeId(id)) != null;
    }

    public List<Student> findByName(String searchTerm) {
        String target = normalizeId(searchTerm);
        List<Student> matches = new ArrayList<>();
        for (Student student : students.values()) {
            if (student.getName().toLowerCase().contains(target)) {
                matches.add(student);
            }
        }
        return matches;
    }

    public List<Student> getStudentsSortedByName() {
        List<Student> list = new ArrayList<>(students.values());
        list.sort(Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Student::getId, String.CASE_INSENSITIVE_ORDER));
        return list;
    }

    public List<Student> getStudentsSortedByAge() {
        List<Student> list = new ArrayList<>(students.values());
        list.sort(Comparator.comparingInt(Student::getAge)
                .thenComparing(Student::getName, String.CASE_INSENSITIVE_ORDER));
        return list;
    }

    public Stats calculateStats() {
        if (students.isEmpty()) {
            return new Stats(0, 0, 0, 0);
        }
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int total = 0;
        for (Student student : students.values()) {
            int age = student.getAge();
            min = Math.min(min, age);
            max = Math.max(max, age);
            total += age;
        }
        double average = total / (double) students.size();
        return new Stats(students.size(), min, max, average);
    }

    private String normalizeId(String id) {
        return id == null ? "" : id.trim().toLowerCase();
    }

    public record Stats(int count, int minAge, int maxAge, double averageAge) { }
}
