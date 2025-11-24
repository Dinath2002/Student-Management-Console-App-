import java.util.LinkedHashMap;
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

        System.out.println("\n📋 Student List (" + students.size() + "):");
        for (Student student : students.values()) {
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

    private String normalizeId(String id) {
        return id == null ? "" : id.trim().toLowerCase();
    }
}
